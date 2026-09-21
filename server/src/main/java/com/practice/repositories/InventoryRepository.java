package com.practice.repositories;

import com.practice.models.Inventory;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import java.util.List;
import static org.jooq.impl.DSL.*;

public class InventoryRepository {

    private final DSLContext dsl;

    public InventoryRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    private static final Table<Record> INVENTORY = table("inventory");
    private static final Field<Integer> ID = field("id", Integer.class);
    private static final Field<Integer> PRODUCT_ID = field("product_id", Integer.class);
    private static final Field<Integer> QUANTITY = field("quantity", Integer.class);
}
