package com.practice.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;

import javax.sql.DataSource;

public class DatabaseConfig {

    private static volatile HikariDataSource dataSource;
    
    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    private DatabaseConfig() {
        // Prevent instantiation
    }

    public static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (DatabaseConfig.class) {
                if (dataSource == null) {
                    HikariConfig config = new HikariConfig();

                    // Connection parameters MUST come from environment (No fallbacks)
                    config.setDriverClassName(getRequiredEnv("DB_DRIVER"));
                    config.setJdbcUrl(getRequiredEnv("DB_URL"));

                    // wrote this for practice purposes
                    config.setUsername(getOptionalEnv("DB_USER", ""));
                    config.setPassword(getOptionalEnv("DB_PASSWORD", ""));

                    // HikariCP Pool Configuration
                    config.setMaximumPoolSize(Integer.parseInt(getOptionalEnv("DB_POOL_MAX", "10")));
                    config.setMinimumIdle(Integer.parseInt(getOptionalEnv("DB_POOL_MIN", "2")));
                    config.setIdleTimeout(300000);       // 5 minutes
                    config.setConnectionTimeout(30000);  // 30 seconds
                    config.setMaxLifetime(1800000);      // 30 minutes

                    dataSource = new HikariDataSource(config);
                }
            }
        }
        return dataSource;
    }

    public static void shutdown() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    /**
     * Strict check: Throws IllegalStateException if key is missing or blank.
     */
    private static String getRequiredEnv(String key) {
        String value = getRawEnv(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing required environment variable in .env: " + key);
        }
        return value;
    }

    private static String getOptionalEnv(String key, String defaultValue) {
        String value = getRawEnv(key);
        return (value != null && !value.isBlank()) ? value : defaultValue;
    }

    private static String getRawEnv(String key) {
        String value = dotenv.get(key);
        if (value == null || value.isBlank()) {
            value = System.getenv(key);
        }
        return value;
    }
}