package com.practice;

import com.practice.config.DatabaseConfig;
import com.practice.config.WebConfig;
import io.javalin.Javalin;
import org.flywaydb.core.Flyway;

public class App {
    public static void main(String[] args) {
        
        // 1. Run database migrations on startup
        System.out.println("Running database migrations...");
        Flyway flyway = Flyway.configure()
                .dataSource(DatabaseConfig.getDataSource())
                .load();
        flyway.migrate();
        System.out.println("Database migrations completed successfully!");

        // 2. Initialize Javalin server using WebConfig
        Javalin app = WebConfig.createServer().start(8080);

        // 3. Register JVM shutdown hook for connection pool teardown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down application...");
            app.stop();
            DatabaseConfig.shutdown();
        }));

        // Test route
        app.get("/api/hello", ctx -> ctx.result("Server is running!"));

        System.out.println("Server started on http://localhost:8080");
    }
}