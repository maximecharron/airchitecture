package ca.ulaval.glo4003;

import ca.ulaval.glo4003.ws.api.calllog.CallLogResource;
import ca.ulaval.glo4003.ws.api.calllog.CallLogResourceImpl;
import ca.ulaval.glo4003.ws.api.contact.ContactResource;
import ca.ulaval.glo4003.ws.api.contact.ContactResourceImpl;
import ca.ulaval.glo4003.ws.api.flight.FlightResource;
import ca.ulaval.glo4003.ws.api.flight.FlightResourceImpl;
import ca.ulaval.glo4003.ws.api.user.UserResource;
import ca.ulaval.glo4003.ws.api.user.UserResourceImpl;
import ca.ulaval.glo4003.ws.domain.calllog.CallLog;
import ca.ulaval.glo4003.ws.domain.calllog.CallLogAssembler;
import ca.ulaval.glo4003.ws.domain.calllog.CallLogRepository;
import ca.ulaval.glo4003.ws.domain.calllog.CallLogService;
import ca.ulaval.glo4003.ws.domain.contact.Contact;
import ca.ulaval.glo4003.ws.domain.contact.ContactAssembler;
import ca.ulaval.glo4003.ws.domain.contact.ContactRepository;
import ca.ulaval.glo4003.ws.domain.contact.ContactService;
import ca.ulaval.glo4003.ws.domain.flight.Flight;
import ca.ulaval.glo4003.ws.domain.flight.FlightAssembler;
import ca.ulaval.glo4003.ws.domain.flight.FlightRepository;
import ca.ulaval.glo4003.ws.domain.flight.FlightService;
import ca.ulaval.glo4003.ws.domain.user.User;
import ca.ulaval.glo4003.ws.domain.user.UserRepository;
import ca.ulaval.glo4003.ws.domain.user.UserService;
import ca.ulaval.glo4003.ws.http.CORSResponseFilter;
import ca.ulaval.glo4003.ws.infrastructure.calllog.CallLogDevDataFactory;
import ca.ulaval.glo4003.ws.infrastructure.calllog.CallLogRepositoryInMemory;
import ca.ulaval.glo4003.ws.infrastructure.contact.ContactDevDataFactory;
import ca.ulaval.glo4003.ws.infrastructure.contact.ContactRepositoryInMemory;
import ca.ulaval.glo4003.ws.infrastructure.flight.FlightDevDataFactory;
import ca.ulaval.glo4003.ws.infrastructure.flight.FlightRepositoryInMemory;
import ca.ulaval.glo4003.ws.infrastructure.user.UserDevDataFactory;
import ca.ulaval.glo4003.ws.infrastructure.user.UserRepositoryInMemory;
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
        ContactResource contactResource = createContactResource();
        CallLogResource callLogResource = createCallLogResource();
        FlightResource flightResource = createFlightResource();
        UserResource userResource = createUserResource();
        // Setup API context (JERSEY + JETTY)
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/api/");
        ResourceConfig resourceConfig = ResourceConfig.forApplication(new Application() {
            @Override
            public Set<Object> getSingletons() {
                HashSet<Object> resources = new HashSet<>();
                // Add resources to context
                resources.add(contactResource);
                resources.add(callLogResource);
                resources.add(flightResource);
                resources.add(userResource);
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

    private static ContactResource createContactResource() {
        // Setup resources' dependencies (DOMAIN + INFRASTRUCTURE)
        ContactRepository contactRepository = new ContactRepositoryInMemory();

        // For development ease
        if (isDev) {
            ContactDevDataFactory contactDevDataFactory = new ContactDevDataFactory();
            List<Contact> contacts = contactDevDataFactory.createMockData();
            contacts.forEach(contactRepository::save);
        }

        ContactAssembler contactAssembler = new ContactAssembler();
        ContactService contactService = new ContactService(contactRepository, contactAssembler);

        return new ContactResourceImpl(contactService);
    }

    private static CallLogResource createCallLogResource() {
        // Setup resources' dependencies (DOMAIN + INFRASTRUCTURE)
        CallLogRepository callLogRepository = new CallLogRepositoryInMemory();

        // For development ease
        if (isDev) {
            CallLogDevDataFactory callLogDevDataFactory = new CallLogDevDataFactory();
            List<CallLog> callLogs = callLogDevDataFactory.createMockData();
            callLogs.forEach(callLogRepository::save);
        }

        CallLogAssembler callLogAssembler = new CallLogAssembler();
        CallLogService callLogService = new CallLogService(callLogRepository, callLogAssembler);

        return new CallLogResourceImpl(callLogService);
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

        return new FlightResourceImpl(flightService);
    }

    private static UserResource createUserResource() {
        UserRepository userRepository = new UserRepositoryInMemory();

        if (isDev) {
            UserDevDataFactory userDevDataFactory = new UserDevDataFactory();
            List<User> users = userDevDataFactory.createMockData();
            users.forEach(userRepository::save);
        }

        UserService flightService = new UserService(userRepository);

        return new UserResourceImpl(flightService);
    }
}
