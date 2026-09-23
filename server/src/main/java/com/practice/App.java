package com.practice;

import com.practice.config.DatabaseConfig;
import com.practice.config.WebConfig;
import com.practice.controllers.CategoryController;
import com.practice.controllers.InventoryController;
import com.practice.controllers.OrderController;
import com.practice.controllers.OrderItemController;
import com.practice.controllers.ProductController;
import com.practice.repositories.CategoryRepository;
import com.practice.repositories.InventoryRepository;
import com.practice.repositories.OrderItemRepository;
import com.practice.repositories.OrderRepository;
import com.practice.repositories.ProductRepository;

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

        InventoryRepository inventoryRepository = new InventoryRepository(dsl);
        InventoryController inventoryController = new InventoryController(inventoryRepository);

        OrderItemRepository orderItemRepository = new OrderItemRepository(dsl);
        OrderItemController orderItemController = new OrderItemController(orderItemRepository);

        ProductRepository productRepository = new ProductRepository(dsl);
        ProductController productController = new ProductController(productRepository);

        OrderRepository orderRepository = new OrderRepository(dsl);
        OrderController orderController = new OrderController(orderRepository);

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

        // Inventory routes
        app.get("/api/inventory", inventoryController::getAll);
        app.post("/api/inventory", inventoryController::create);
        app.put("/api/inventory/{id}", inventoryController::update);
        app.delete("/api/inventory/{id}", inventoryController::delete);

        // Order Item routes
        app.get("/api/order-items", orderItemController::getAll);
        app.post("/api/order-items", orderItemController::create);
        app.put("/api/order-items/{id}", orderItemController::update);
        app.delete("/api/order-items/{id}", orderItemController::delete);

        // Product routes
        app.get("/api/products", productController::getAll);
        app.post("/api/products", productController::create);
        app.put("/api/products/{id}", productController::update);
        app.delete("/api/products/{id}", productController::delete);

        // Order routes
        app.get("/api/orders", orderController::getAll);
        app.post("/api/orders", orderController::create);
        app.put("/api/orders/{id}", orderController::update);
        app.delete("/api/orders/{id}", orderController::delete);

        System.out.println("Server started on http://localhost:8080");
    }
}