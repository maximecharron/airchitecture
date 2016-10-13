package ca.ulaval.glo4003;

import ca.ulaval.glo4003.air.api.flight.FlightResource;
import ca.ulaval.glo4003.air.api.user.UserResource;
import ca.ulaval.glo4003.air.api.weightdetection.WeightDetectionResource;
import ca.ulaval.glo4003.air.domain.flight.Flight;
import ca.ulaval.glo4003.air.domain.flight.FlightAssembler;
import ca.ulaval.glo4003.air.domain.flight.FlightRepository;
import ca.ulaval.glo4003.air.domain.flight.FlightService;
import ca.ulaval.glo4003.air.domain.user.*;
import ca.ulaval.glo4003.air.domain.weightdetection.WeightDetectionAssembler;
import ca.ulaval.glo4003.air.domain.weightdetection.WeightDetectionService;
import ca.ulaval.glo4003.air.domain.weightdetection.WeightDetector;
import ca.ulaval.glo4003.air.api.config.CORSResponseFilter;
import ca.ulaval.glo4003.air.persistence.flight.FlightDevDataFactory;
import ca.ulaval.glo4003.air.persistence.flight.FlightRepositoryInMemory;
import ca.ulaval.glo4003.air.persistence.user.UserDevDataFactory;
import ca.ulaval.glo4003.air.persistence.user.UserRepositoryInMemory;
import ca.ulaval.glo4003.air.domain.user.hashingStrategies.HashingStrategyBCrypt;
import ca.ulaval.glo4003.air.domain.user.encoders.JWTTokenEncoder;
import ca.ulaval.glo4003.air.domain.weightdetection.detectors.DummyWeightDetector;
import com.auth0.jwt.JWTSigner;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AirChitectureMain {

    private static boolean isDev = true; // Would be a JVM argument or in a .property file

    public static void main(String[] args) throws Exception {
        // Setup resources (API)
        FlightResource flightResource = createFlightResource();
        UserResource userResource = createUserResource();
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
                resources.add(userResource);
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

    private static FlightResource createFlightResource() {
        FlightRepository flightRepository = new FlightRepositoryInMemory();

        if (isDev) {
            FlightDevDataFactory flightDevDataFactory = new FlightDevDataFactory();
            List<Flight> flights = flightDevDataFactory.createMockData();
            flights.forEach(flightRepository::save);
        }

        FlightAssembler flightAssembler = new FlightAssembler();
        FlightService flightService = new FlightService(flightRepository, flightAssembler);

        return new FlightResource(flightService);
    }

    private static WeightDetectionResource createWeightDetectionResource() {
        WeightDetector weightDetector = new DummyWeightDetector();
        WeightDetectionAssembler weightDetectionAssembler = new WeightDetectionAssembler();
        WeightDetectionService weightDetectionService = new WeightDetectionService(weightDetector, weightDetectionAssembler);

        return new WeightDetectionResource(weightDetectionService);
    }

    private static UserResource createUserResource() {
        UserRepository userRepository = new UserRepositoryInMemory();
        TokenEncoder tokenEncoder = new JWTTokenEncoder(new JWTSigner(JWTTokenEncoder.SECRET));
        HashingStrategy hashingStrategy = new HashingStrategyBCrypt();
        UserFactory userFactory = new UserFactory(tokenEncoder, hashingStrategy);

        if (isDev) {
            UserDevDataFactory userDevDataFactory = new UserDevDataFactory(userFactory);
            List<User> users = userDevDataFactory.createMockData();
            users.forEach(userRepository::update);
        }

        UserAssembler userAssembler = new UserAssembler();
        UserService userService = new UserService(userRepository, userAssembler, userFactory);

        return new UserResource(userService);
    }
}
