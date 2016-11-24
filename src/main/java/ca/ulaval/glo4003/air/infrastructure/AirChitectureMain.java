package ca.ulaval.glo4003.air.infrastructure;

import ca.ulaval.glo4003.air.api.config.CORSResponseFilter;
import ca.ulaval.glo4003.air.infrastructure.context.AirChitectureApplicationContext;
import ca.ulaval.glo4003.air.infrastructure.context.DevelopmentContext;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.ws.rs.core.Application;
import java.util.Set;

public class AirChitectureMain {

    public static void main(String[] args) throws Exception {
        AirChitectureApplicationContext applicationContext = new DevelopmentContext();

        // Setup Api and Webapp HTTP servlet handlers
        ServletContextHandler ApiContextHandler = getApiContextHandler(applicationContext);
        WebAppContext WebAppContextHandler = getWebAppContextHandler();

        // Setup http server
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{ApiContextHandler, WebAppContextHandler});

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

    private static WebAppContext getWebAppContextHandler() {
        WebAppContext WebAppContextHandler = new WebAppContext();
        WebAppContextHandler.setResourceBase("src/main/webapp");
        WebAppContextHandler.setContextPath("/");
        return WebAppContextHandler;
    }

    private static ServletContextHandler getApiContextHandler(final AirChitectureApplicationContext applicationContext) {
        ServletContextHandler ApiContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        ApiContextHandler.setContextPath("/api");
        ResourceConfig resourceConfig = ResourceConfig.forApplication(new Application() {
            @Override
            public Set<Object> getSingletons() {
                return applicationContext.getApplicationContextResources();
            }
        });
        resourceConfig.register(CORSResponseFilter.class);

        ServletContainer servletContainer = new ServletContainer(resourceConfig);
        ServletHolder servletHolder = new ServletHolder(servletContainer);
        ApiContextHandler.addServlet(servletHolder, "/*");
        return ApiContextHandler;
    }

}
