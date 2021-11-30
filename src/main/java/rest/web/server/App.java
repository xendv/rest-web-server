package rest.web.server;

import org.eclipse.jetty.server.Server;
import rest.web.server.initializers.DBFlywayInitializer;

public final class App {

    public static void main(String[] args) throws Exception {
        DBFlywayInitializer.initDBFlyway();
        final Server server = new DefaultServer().build();
        server.start();
    }
}
