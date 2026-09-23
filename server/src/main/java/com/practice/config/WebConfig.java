package com.practice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import io.javalin.plugin.bundled.CorsPluginConfig;

import java.util.Map;

public class WebConfig {

    private WebConfig() {
        // Utility class; prevent instantiation
    }

    public static Javalin createServer() {
        // Configure Jackson ObjectMapper for Java 8 LocalDateTime support
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Javalin app = Javalin.create(config -> {
            // Pass the boolean parameter to match JavalinJackson constructor
            config.jsonMapper(new JavalinJackson(objectMapper, true));

            // Enable CORS for frontend integration
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(CorsPluginConfig.CorsRule::anyHost);
            });

            // Default response content type
            config.http.defaultContentType = "application/json";
        });

        // Register exception handling
        registerExceptionHandlers(app);

        return app;
    }

    private static void registerExceptionHandlers(Javalin app) {
        // Global handler for unexpected server errors (HTTP 500)
        app.exception(Exception.class, (e, ctx) -> {
            ctx.status(500).json(Map.of(
                "status", 500,
                "error", "Internal Server Error",
                "message", e.getMessage() != null ? e.getMessage() : "An unexpected error occurred."
            ));
        });
    }
}