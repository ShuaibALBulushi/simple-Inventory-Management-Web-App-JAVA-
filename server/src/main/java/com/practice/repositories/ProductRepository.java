package com.practice.repositories;

import com.practice.models.Product;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.jooq.impl.DSL.*;

public class ProductRepository {

    private final DSLContext dsl;

    public ProductRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    // Define table and fields dynamically matching your V1 SQL schema
    private static final Table<Record> PRODUCTS = table("products");
    private static final Field<Integer> ID = field("id", Integer.class);
    private static final Field<Integer> CATEGORY_ID = field("category_id", Integer.class);
    private static final Field<String> NAME = field("name", String.class);
    private static final Field<BigDecimal> PRICE = field("price", BigDecimal.class);
}
