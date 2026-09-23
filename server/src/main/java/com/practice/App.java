package com.practice;

import com.practice.config.DatabaseConfig;
import com.practice.config.WebConfig;
import com.practice.controllers.CategoryController;
import com.practice.repositories.CategoryRepository;
import io.javalin.Javalin;
import org.flywaydb.core.Flyway;
import org.jooq.DSLContext;

public class App {
    public static void main(String[] args) {
        
        // 1. Run database migrations on startup
        System.out.println("Running database migrations...");
        Flyway flyway = Flyway.configure()
                .dataSource(DatabaseConfig.getDataSource())
                .load();
        flyway.migrate();
        System.out.println("Database migrations completed successfully!");

        // 2. Initialize dependencies
        DSLContext dsl = DatabaseConfig.getDSLContext();
        CategoryRepository categoryRepository = new CategoryRepository(dsl);
        CategoryController categoryController = new CategoryController(categoryRepository);

        // 3. Initialize Javalin server using WebConfig
        Javalin app = WebConfig.createServer().start(8080);

        // 4. Register JVM shutdown hook for connection pool teardown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down application...");
            app.stop();
            DatabaseConfig.shutdown();
        }));

        // Test route
        app.get("/api/hello", ctx -> ctx.result("Server is running!"));

        // Category routes
        app.get("/api/categories", categoryController::getAll);
        app.post("/api/categories", categoryController::create);
        app.put("/api/categories/{id}", categoryController::update);
        app.delete("/api/categories/{id}", categoryController::delete);

        System.out.println("Server started on http://localhost:8080");
    }
}