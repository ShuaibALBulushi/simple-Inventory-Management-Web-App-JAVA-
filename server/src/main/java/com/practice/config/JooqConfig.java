package com.practice.config;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public class JooqConfig {

    private static volatile DSLContext dsl;

    private JooqConfig() {
        // Utility class; prevent instantiation
    }

    public static DSLContext getDsl() {
        if (dsl == null) {
            synchronized (JooqConfig.class) {
                if (dsl == null) {
                    dsl = DSL.using(DatabaseConfig.getDataSource(), SQLDialect.DEFAULT);
                }
            }
        }
        return dsl;
    }
}