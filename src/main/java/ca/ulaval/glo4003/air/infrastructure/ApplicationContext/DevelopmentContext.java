package ca.ulaval.glo4003.air.infrastructure.ApplicationContext;

import ca.ulaval.glo4003.air.api.flight.FlightResource;
import ca.ulaval.glo4003.air.api.transaction.CartItemResource;
import ca.ulaval.glo4003.air.api.transaction.TransactionResource;
import ca.ulaval.glo4003.air.api.user.AuthenticationResource;
import ca.ulaval.glo4003.air.api.user.UserResource;
import ca.ulaval.glo4003.air.api.weightdetection.WeightDetectionResource;
import ca.ulaval.glo4003.air.domain.DateTimeFactory;
import ca.ulaval.glo4003.air.domain.flight.Flight;
import ca.ulaval.glo4003.air.domain.flight.FlightService;
import ca.ulaval.glo4003.air.domain.flight.WeightFilterVerifier;
import ca.ulaval.glo4003.air.domain.notification.EmailTransactionNotifier;
import ca.ulaval.glo4003.air.domain.notification.EmailTransactionNotifierConfiguration;
import ca.ulaval.glo4003.air.domain.notification.TransactionNotifier;
import ca.ulaval.glo4003.air.domain.transaction.TransactionRepository;
import ca.ulaval.glo4003.air.domain.transaction.TransactionService;
import ca.ulaval.glo4003.air.domain.transaction.cart.CartItemService;
import ca.ulaval.glo4003.air.domain.user.User;
import ca.ulaval.glo4003.air.domain.user.UserFactory;
import ca.ulaval.glo4003.air.domain.user.UserRepository;
import ca.ulaval.glo4003.air.domain.user.UserService;
import ca.ulaval.glo4003.air.domain.user.hashing.HashingStrategy;
import ca.ulaval.glo4003.air.domain.weightdetection.WeightDetectionService;
import ca.ulaval.glo4003.air.domain.weightdetection.WeightDetector;
import ca.ulaval.glo4003.air.infrastructure.flight.FlightDevDataFactory;
import ca.ulaval.glo4003.air.infrastructure.flight.FlightRepositoryInMemory;
import ca.ulaval.glo4003.air.infrastructure.notification.ResourcesWithDefaultsEmailTransactionNotifierConfiguration;
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

import java.util.HashSet;
import java.util.List;

public class DevelopmentContext implements AirChitectureApplicationContext {

    public HashSet<Object> getApplicationContextResources() {
        HashSet<Object> resources = new HashSet<>();
        UserAssembler userAssembler = new UserAssembler();
        CartItemAssembler cartItemAssembler = new CartItemAssembler();

        FlightService flightService = createFlightService();
        UserService userService = createUserService();

        FlightResource flightResource = createFlightResource(flightService);
        AuthenticationResource authenticationResource = new AuthenticationResource(userService, userAssembler);
        UserResource userResource = new UserResource(userService, userAssembler);
        CartItemResource cartItemResource = createCartItemResource(flightService, cartItemAssembler);
        TransactionResource transactionResource = createTransactionResource(cartItemAssembler);
        WeightDetectionResource weightDetectionResource = createWeightDetectionResource();

        resources.add(flightResource);
        resources.add(authenticationResource);
        resources.add(userResource);
        resources.add(cartItemResource);
        resources.add(transactionResource);
        resources.add(weightDetectionResource);

        return resources;
    }

    private static TransactionResource createTransactionResource(CartItemAssembler cartItemAssembler) {
        TransactionRepository transactionRepository = new TransactionRepositoryInMemory();
        SmtpEmailSender smtpEmailSender = new SmtpEmailSender();
        EmailTransactionNotifierConfiguration emailConfiguration = new ResourcesWithDefaultsEmailTransactionNotifierConfiguration();
        TransactionNotifier transactionNotifier = new EmailTransactionNotifier(smtpEmailSender, emailConfiguration);

        TransactionAssembler transactionAssembler = new TransactionAssembler(cartItemAssembler);
        TransactionService transactionService = new TransactionService(transactionRepository, transactionNotifier);
        return new TransactionResource(transactionService, transactionAssembler);
    }

    private static CartItemResource createCartItemResource(FlightService flightService, CartItemAssembler cartItemAssembler) {
        CartItemService cartItemService = new CartItemService(flightService);
        return new CartItemResource(cartItemService, cartItemAssembler);
    }

    private static FlightService createFlightService() {
        FlightRepositoryInMemory flightRepository = new FlightRepositoryInMemory();

        FlightDevDataFactory flightDevDataFactory = new FlightDevDataFactory();
        List<Flight> flights = flightDevDataFactory.createMockData();
        flights.forEach(flightRepository::save);

        WeightFilterVerifier weightFilterVerifier = new WeightFilterVerifier();
        DateTimeFactory dateTimeFactory = new DateTimeFactory();
        return new FlightService(flightRepository, weightFilterVerifier, dateTimeFactory);
    }

    private static FlightResource createFlightResource(FlightService flightService) {
        FlightAssembler flightAssembler = new FlightAssembler();
        return new FlightResource(flightService, flightAssembler);
    }

    private static WeightDetectionResource createWeightDetectionResource() {
        WeightDetector weightDetector = new DummyWeightDetector();
        WeightDetectionAssembler weightDetectionAssembler = new WeightDetectionAssembler();
        WeightDetectionService weightDetectionService = new WeightDetectionService(weightDetector);

        return new WeightDetectionResource(weightDetectionService, weightDetectionAssembler);
    }

    private static UserService createUserService() {
        UserRepository userRepository = new UserRepositoryInMemory();
        JWTTokenEncoder tokenEncoder = new JWTTokenEncoder(new JWTSigner(JWTTokenEncoder.SECRET), new JWTVerifier(JWTTokenEncoder.SECRET));
        HashingStrategy hashingStrategy = new HashingStrategyBCrypt();
        UserFactory userFactory = new UserFactory(tokenEncoder, hashingStrategy);

        UserDevDataFactory userDevDataFactory = new UserDevDataFactory(userFactory);
        List<User> users = userDevDataFactory.createMockData();
        users.forEach(userRepository::update);

        return new UserService(userRepository, userFactory, tokenEncoder);
    }

}
