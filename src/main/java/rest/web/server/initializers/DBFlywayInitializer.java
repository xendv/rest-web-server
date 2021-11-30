package rest.web.server.initializers;

import com.google.inject.Inject;
import org.flywaydb.core.Flyway;
import org.gradle.internal.impldep.com.esotericsoftware.minlog.Log;
import org.jetbrains.annotations.NotNull;

public final class DBFlywayInitializer {
    public static final @NotNull
    JDBCSettingsProvider JDBC_SETTINGS = JDBCSettingsProvider.DEFAULT;

    @Inject
    public static void initDBFlyway() {
        final Flyway flyway = Flyway
                .configure()
                .dataSource(
                        JDBC_SETTINGS.url(),
                        JDBC_SETTINGS.login(),
                        JDBC_SETTINGS.password()
                )
                .locations("database_init_scripts")
                .load();
        flyway.clean();
        flyway.migrate();
        Log.debug("Migrations applied successfully");
    }
}
