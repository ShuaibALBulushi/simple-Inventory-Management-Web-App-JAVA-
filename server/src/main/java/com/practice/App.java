package com.practice;

import io.javalin.Javalin;

public class App {
    public static void main(String[] args) {
        
        // Initialize Javalin server on port 8080
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(io.javalin.plugin.bundled.CorsPluginConfig.CorsRule::anyHost);
            });
        }).start(8080);

        //test route
        app.get("/api/hello", ctx -> ctx.result("Server is running!"));

        System.out.println("Server started on http://localhost:8080");
    }
}
