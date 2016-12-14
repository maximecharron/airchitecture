package ca.ulaval.glo4003.air.infrastructure.context;

import ca.ulaval.glo4003.air.api.airplane.AirplaneResource;
import ca.ulaval.glo4003.air.api.flight.FlightResource;
import ca.ulaval.glo4003.air.api.geolocation.GeolocationResource;
import ca.ulaval.glo4003.air.api.transaction.CartItemResource;
import ca.ulaval.glo4003.air.api.transaction.TransactionResource;
import ca.ulaval.glo4003.air.api.user.AuthenticationResource;
import ca.ulaval.glo4003.air.api.user.UserResource;
import ca.ulaval.glo4003.air.api.weightdetection.WeightDetectionResource;
import ca.ulaval.glo4003.air.domain.DateTimeFactory;
import ca.ulaval.glo4003.air.domain.airplane.Airplane;
import ca.ulaval.glo4003.air.domain.flight.AvailableSeatsFactory;
import ca.ulaval.glo4003.air.domain.flight.Flight;
import ca.ulaval.glo4003.air.domain.flight.FlightSortingStrategy;
import ca.ulaval.glo4003.air.domain.flight.WeightFilterVerifier;
import ca.ulaval.glo4003.air.domain.geolocation.Geolocator;
import ca.ulaval.glo4003.air.domain.notification.EmailTransactionNotifier;
import ca.ulaval.glo4003.air.domain.notification.EmailTransactionNotifierConfiguration;
import ca.ulaval.glo4003.air.domain.notification.TransactionNotifier;
import ca.ulaval.glo4003.air.domain.transaction.TransactionRepository;
import ca.ulaval.glo4003.air.domain.user.User;
import ca.ulaval.glo4003.air.domain.user.UserFactory;
import ca.ulaval.glo4003.air.domain.user.UserRepository;
import ca.ulaval.glo4003.air.domain.user.hashing.HashingStrategy;
import ca.ulaval.glo4003.air.domain.weightdetection.WeightDetector;
import ca.ulaval.glo4003.air.infrastructure.airplane.AirplaneDevDataFactory;
import ca.ulaval.glo4003.air.infrastructure.airplane.AirplaneRepositoryInMemory;
import ca.ulaval.glo4003.air.infrastructure.flight.FlightDevDataFactory;
import ca.ulaval.glo4003.air.infrastructure.flight.FlightRepositoryInMemory;
import ca.ulaval.glo4003.air.infrastructure.flight.SeatsAndPriceSortingStrategy;
import ca.ulaval.glo4003.air.infrastructure.geolocation.DummyAirportGeolocator;
import ca.ulaval.glo4003.air.infrastructure.notification.ResourcesWithDefaultsEmailTransactionNotifierConfiguration;
import ca.ulaval.glo4003.air.infrastructure.notification.SmtpEmailSender;
import ca.ulaval.glo4003.air.infrastructure.transaction.TransactionRepositoryInMemory;
import ca.ulaval.glo4003.air.infrastructure.user.UserDevDataFactory;
import ca.ulaval.glo4003.air.infrastructure.user.UserRepositoryInMemory;
import ca.ulaval.glo4003.air.infrastructure.user.encoding.JWTTokenEncoder;
import ca.ulaval.glo4003.air.infrastructure.user.hashing.HashingStrategyBCrypt;
import ca.ulaval.glo4003.air.infrastructure.weightdetection.DummyWeightDetector;
import ca.ulaval.glo4003.air.service.airplane.AirplaneService;
import ca.ulaval.glo4003.air.service.flight.FlightService;
import ca.ulaval.glo4003.air.service.geolocation.GeolocationService;
import ca.ulaval.glo4003.air.service.transaction.TransactionService;
import ca.ulaval.glo4003.air.service.transaction.cart.CartItemService;
import ca.ulaval.glo4003.air.service.user.UserService;
import ca.ulaval.glo4003.air.service.weightdetection.WeightDetectionService;
import ca.ulaval.glo4003.air.transfer.airplane.AirplaneAssembler;
import ca.ulaval.glo4003.air.transfer.airplane.SeatMapAssembler;
import ca.ulaval.glo4003.air.transfer.flight.AvailableSeatsAssembler;
import ca.ulaval.glo4003.air.transfer.flight.FlightAssembler;
import ca.ulaval.glo4003.air.transfer.flight.SeatsPricingAssembler;
import ca.ulaval.glo4003.air.transfer.geolocation.NearestAirportAssembler;
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

        List<Airplane> airplanes = createAirplaneMockData();

        UserAssembler userAssembler = new UserAssembler();
        UserService userService = createUserService(userAssembler);
        UserResource userResource = new UserResource(userService);

        AvailableSeatsAssembler availableSeatsAssembler = new AvailableSeatsAssembler();
        SeatMapAssembler seatMapAssembler = new SeatMapAssembler();

        AirplaneService airplaneService = createAirplaneService(airplanes, userService, seatMapAssembler);
        AirplaneResource airplaneResource = new AirplaneResource(airplaneService);
        FlightService flightService = createFlightService(airplanes, availableSeatsAssembler, userService);
        FlightResource flightResource = new FlightResource(flightService);

        CartItemAssembler cartItemAssembler = new CartItemAssembler(seatMapAssembler);
        CartItemResource cartItemResource = createCartItemResource(flightService, cartItemAssembler);
        TransactionResource transactionResource = createTransactionResource(cartItemAssembler, userService);

        AuthenticationResource authenticationResource = new AuthenticationResource(userService);

        WeightDetectionResource weightDetectionResource = createWeightDetectionResource();
        GeolocationResource geolocationResource = createGeolocationResource();

        resources.add(airplaneResource);
        resources.add(flightResource);
        resources.add(authenticationResource);
        resources.add(userResource);
        resources.add(cartItemResource);
        resources.add(transactionResource);
        resources.add(weightDetectionResource);
        resources.add(geolocationResource);

        return resources;
    }

    private static CartItemResource createCartItemResource(FlightService flightService, CartItemAssembler cartItemAssembler) {
        CartItemService cartItemService = new CartItemService(flightService, cartItemAssembler);
        return new CartItemResource(cartItemService);
    }

    private static TransactionResource createTransactionResource(CartItemAssembler cartItemAssembler, UserService userService) {
        TransactionRepository transactionRepository = new TransactionRepositoryInMemory();
        SmtpEmailSender smtpEmailSender = new SmtpEmailSender();
        EmailTransactionNotifierConfiguration emailConfiguration = new ResourcesWithDefaultsEmailTransactionNotifierConfiguration();
        TransactionNotifier transactionNotifier = new EmailTransactionNotifier(smtpEmailSender, emailConfiguration);

        TransactionAssembler transactionAssembler = new TransactionAssembler(cartItemAssembler);
        TransactionService transactionService = new TransactionService(transactionRepository, transactionNotifier, transactionAssembler, userService);
        return new TransactionResource(transactionService);
    }

    private static List<Airplane> createAirplaneMockData() {
        AirplaneDevDataFactory airplaneDevDataFactory = new AirplaneDevDataFactory();
        return airplaneDevDataFactory.createMockData();
    }

    private static AirplaneService createAirplaneService(List<Airplane> airplanes, UserService userService, SeatMapAssembler seatMapAssembler) {
        AirplaneRepositoryInMemory flightRepository = new AirplaneRepositoryInMemory();
        AirplaneAssembler flightAssembler = new AirplaneAssembler(seatMapAssembler);

        airplanes.forEach(flightRepository::save);
        return new AirplaneService(flightRepository, flightAssembler, userService);
    }

    private static FlightService createFlightService(List<Airplane> airplanes, AvailableSeatsAssembler availableSeatsAssembler, UserService userService) {
        FlightRepositoryInMemory flightRepository = new FlightRepositoryInMemory();
        FlightAssembler flightAssembler = new FlightAssembler(availableSeatsAssembler, new SeatsPricingAssembler());

        FlightDevDataFactory flightDevDataFactory = new FlightDevDataFactory();
        List<Flight> flights = flightDevDataFactory.createMockData(airplanes, new AvailableSeatsFactory());
        flights.forEach(flightRepository::save);

        WeightFilterVerifier weightFilterVerifier = new WeightFilterVerifier();
        DateTimeFactory dateTimeFactory = new DateTimeFactory();
        FlightSortingStrategy flightSortingStrategy = new SeatsAndPriceSortingStrategy();
        return new FlightService(flightRepository, weightFilterVerifier, dateTimeFactory, flightSortingStrategy, flightAssembler, userService);
    }


    private static WeightDetectionResource createWeightDetectionResource() {
        WeightDetector weightDetector = new DummyWeightDetector();
        WeightDetectionAssembler weightDetectionAssembler = new WeightDetectionAssembler();
        WeightDetectionService weightDetectionService = new WeightDetectionService(weightDetector, weightDetectionAssembler);

        return new WeightDetectionResource(weightDetectionService);
    }

    private static GeolocationResource createGeolocationResource() {
        Geolocator geolocator = new DummyAirportGeolocator();
        NearestAirportAssembler nearestAirportAssembler = new NearestAirportAssembler();
        GeolocationService geolocationService = new GeolocationService(geolocator, nearestAirportAssembler);

        return new GeolocationResource(geolocationService);
    }

    private static UserService createUserService(UserAssembler userAssembler) {
        UserRepository userRepository = new UserRepositoryInMemory();
        JWTTokenEncoder tokenEncoder = new JWTTokenEncoder(new JWTSigner(JWTTokenEncoder.SECRET), new JWTVerifier(JWTTokenEncoder.SECRET));
        HashingStrategy hashingStrategy = new HashingStrategyBCrypt();
        UserFactory userFactory = new UserFactory(tokenEncoder, hashingStrategy);

        UserDevDataFactory userDevDataFactory = new UserDevDataFactory(userFactory);
        List<User> users = userDevDataFactory.createMockData();
        users.forEach(userRepository::update);

        return new UserService(userRepository, userFactory, tokenEncoder, userAssembler);
    }

}
