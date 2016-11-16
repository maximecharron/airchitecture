package ca.ulaval.glo4003.air.infrastructure;

import ca.ulaval.glo4003.air.api.config.CORSResponseFilter;
import ca.ulaval.glo4003.air.api.flight.FlightResource;
import ca.ulaval.glo4003.air.api.transaction.CartItemResource;
import ca.ulaval.glo4003.air.api.transaction.TransactionResource;
import ca.ulaval.glo4003.air.api.user.AuthenticationResource;
import ca.ulaval.glo4003.air.api.user.UserResource;
import ca.ulaval.glo4003.air.api.weightdetection.WeightDetectionResource;
import ca.ulaval.glo4003.air.domain.DateTimeFactory;
import ca.ulaval.glo4003.air.domain.flight.Flight;
import ca.ulaval.glo4003.air.service.flight.FlightService;
import ca.ulaval.glo4003.air.domain.flight.WeightFilterVerifier;
import ca.ulaval.glo4003.air.domain.notification.EmailTransactionNotifier;
import ca.ulaval.glo4003.air.domain.notification.EmailTransactionNotifierConfiguration;
import ca.ulaval.glo4003.air.domain.notification.TransactionNotifier;
import ca.ulaval.glo4003.air.domain.transaction.TransactionRepository;
import ca.ulaval.glo4003.air.service.transaction.TransactionService;
import ca.ulaval.glo4003.air.service.transaction.cart.CartItemService;
import ca.ulaval.glo4003.air.domain.user.User;
import ca.ulaval.glo4003.air.domain.user.UserFactory;
import ca.ulaval.glo4003.air.domain.user.UserRepository;
import ca.ulaval.glo4003.air.service.user.UserService;
import ca.ulaval.glo4003.air.domain.user.hashing.HashingStrategy;
import ca.ulaval.glo4003.air.service.weightdetection.WeightDetectionService;
import ca.ulaval.glo4003.air.domain.weightdetection.WeightDetector;
import ca.ulaval.glo4003.air.infrastructure.flight.FlightDevDataFactory;
import ca.ulaval.glo4003.air.infrastructure.flight.FlightRepositoryInMemory;
import ca.ulaval.glo4003.air.infrastructure.notification.ResourcesEmailTransactionNotifierConfiguration;
import ca.ulaval.glo4003.air.infrastructure.notification.SmtpEmailSender;
import ca.ulaval.glo4003.air.infrastructure.transaction.TransactionRepositoryInMemory;
import ca.ulaval.glo4003.air.infrastructure.user.UserDevDataFactory;
import ca.ulaval.glo4003.air.infrastructure.user.UserRepositoryInMemory;
import ca.ulaval.glo4003.air.infrastructure.user.encoding.JWTTokenEncoder;
import ca.ulaval.glo4003.air.infrastructure.user.hashing.HashingStrategyBCrypt;
import ca.ulaval.glo4003.air.infrastructure.weightdetection.DummyWeightDetector;
import ca.ulaval.glo4003.air.transfer.flight.FlightAssembler;
import ca.ulaval.glo4003.air.transfer.transaction.CartItemAssembler;
import ca.ulaval.glo4003.air.transfer.transaction.TransactionAssembler;
import ca.ulaval.glo4003.air.transfer.user.UserAssembler;
import ca.ulaval.glo4003.air.transfer.weightdetection.WeightDetectionAssembler;
import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.ws.rs.core.Application;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AirChitectureMain {

    private static boolean isDev = true; // Would be a JVM argument or in a .property file

    public static void main(String[] args) throws Exception {
        // Setup API resources
        FlightService flightService = createFlightService();
        FlightResource flightResource = createFlightResource(flightService);

        UserAssembler userAssembler = new UserAssembler();
        UserService userService = createUserService(userAssembler);
        UserResource userResource = createUserResource(userService);

        CartItemAssembler cartItemAssembler = createCartItemAssembler();
        CartItemResource cartItemResource = createCartItemResource(flightService, cartItemAssembler);
        TransactionResource transactionResource = createTransactionResource(cartItemAssembler);

        AuthenticationResource authenticationResource = createAuthenticationResource(userService, userAssembler);

        WeightDetectionResource weightDetectionResource = createWeightDetectionResource();

        // Setup API context (JERSEY + JETTY)
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/api");
        ResourceConfig resourceConfig = ResourceConfig.forApplication(new Application() {
            @Override
            public Set<Object> getSingletons() {
                HashSet<Object> resources = new HashSet<>();
                // Add resources to context
                resources.add(flightResource);
                resources.add(authenticationResource);
                resources.add(userResource);
                resources.add(cartItemResource);
                resources.add(transactionResource);
                resources.add(weightDetectionResource);
                return resources;
            }
        });
        resourceConfig.register(CORSResponseFilter.class);

        ServletContainer servletContainer = new ServletContainer(resourceConfig);
        ServletHolder servletHolder = new ServletHolder(servletContainer);
        context.addServlet(servletHolder, "/*");

        // Setup static file context (WEBAPP)
        WebAppContext webapp = new WebAppContext();
        webapp.setResourceBase("src/main/webapp");
        webapp.setContextPath("/");

        // Setup http server
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{context, webapp});

        String portParameter = args.length != 0 ? args[0] : "8081";
        int port = Integer.valueOf(portParameter);

        Server server = new Server(port);
        server.setHandler(contexts);

        try {
            server.start();
            server.join();
        } finally {
            server.destroy();
        }
    }

    private static CartItemAssembler createCartItemAssembler() {
        return new CartItemAssembler();
    }

    private static TransactionResource createTransactionResource(CartItemAssembler cartItemAssembler) throws IOException {
        TransactionRepository transactionRepository = new TransactionRepositoryInMemory();
        SmtpEmailSender smtpEmailSender = new SmtpEmailSender();
        EmailTransactionNotifierConfiguration emailConfiguration = new ResourcesEmailTransactionNotifierConfiguration();
        TransactionNotifier transactionNotifier = new EmailTransactionNotifier(smtpEmailSender, emailConfiguration);

        TransactionAssembler transactionAssembler = new TransactionAssembler(cartItemAssembler);
        TransactionService transactionService = new TransactionService(transactionRepository, transactionNotifier, transactionAssembler);
        return new TransactionResource(transactionService);
    }

    private static CartItemResource createCartItemResource(FlightService flightService, CartItemAssembler cartItemAssembler) {
        CartItemService cartItemService = new CartItemService(flightService, cartItemAssembler);
        return new CartItemResource(cartItemService);
    }

    private static FlightService createFlightService() {
        FlightRepositoryInMemory flightRepository = new FlightRepositoryInMemory();
        FlightAssembler flightAssembler = new FlightAssembler();

        if (isDev) {
            FlightDevDataFactory flightDevDataFactory = new FlightDevDataFactory();
            List<Flight> flights = flightDevDataFactory.createMockData();
            flights.forEach(flightRepository::save);
        }

        WeightFilterVerifier weightFilterVerifier = new WeightFilterVerifier();
        DateTimeFactory dateTimeFactory = new DateTimeFactory();
        return new FlightService(flightRepository, weightFilterVerifier, dateTimeFactory, flightAssembler);
    }

    private static FlightResource createFlightResource(FlightService flightService) {
        return new FlightResource(flightService);
    }

    private static WeightDetectionResource createWeightDetectionResource() {
        WeightDetector weightDetector = new DummyWeightDetector();
        WeightDetectionAssembler weightDetectionAssembler = new WeightDetectionAssembler();
        WeightDetectionService weightDetectionService = new WeightDetectionService(weightDetector, weightDetectionAssembler);

        return new WeightDetectionResource(weightDetectionService);
    }

    private static UserService createUserService(UserAssembler userAssembler) {
        UserRepository userRepository = new UserRepositoryInMemory();
        JWTTokenEncoder tokenEncoder = new JWTTokenEncoder(new JWTSigner(JWTTokenEncoder.SECRET), new JWTVerifier(JWTTokenEncoder.SECRET));
        HashingStrategy hashingStrategy = new HashingStrategyBCrypt();
        UserFactory userFactory = new UserFactory(tokenEncoder, hashingStrategy);

        if (isDev) {
            UserDevDataFactory userDevDataFactory = new UserDevDataFactory(userFactory);
            List<User> users = userDevDataFactory.createMockData();
            users.forEach(userRepository::update);
        }

        return new UserService(userRepository, userFactory, tokenEncoder, userAssembler);
    }

    private static AuthenticationResource createAuthenticationResource(UserService userService, UserAssembler userAssembler) {
        return new AuthenticationResource(userService);
    }

    private static UserResource createUserResource(UserService userService) {
        return new UserResource(userService);
    }
}
