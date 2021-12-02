package rest.web.server;

import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import rest.web.server.handlers.SecurityHandlerBuilder;

import java.io.IOException;

@SuppressWarnings({"NotNullNullableValidation", "FieldCanBeLocal"})
public final class DefaultServer {

    private static final int port = 3466;
    private final Server server = new Server();

    public Server build() {
        return build(port);
    }

    public Server build(int port) {
        final ServerConnector serverConnector = new ServerConnector(server, new HttpConnectionFactory());
        serverConnector.setHost("localhost");
        serverConnector.setPort(port);
        server.setConnectors(new Connector[]{serverConnector});
        try {
            setUp();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return server;
    }

    private void setUp() throws IOException {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);

        context.addServlet(HttpServletDispatcher.class, "/");

        // RESTEasy setup
        context.addEventListener(new GuiceListener());
        server.setHandler(context);

        // security
        final String hashConfig = App.class.getResource("/hash_config").toExternalForm();
        final HashLoginService hashLoginService = new HashLoginService("login", hashConfig);
        final ConstraintSecurityHandler securityHandler = new SecurityHandlerBuilder().build(hashLoginService);

        server.addBean(hashLoginService);
        securityHandler.setHandler(context);
        server.setHandler(securityHandler);
    }
}
