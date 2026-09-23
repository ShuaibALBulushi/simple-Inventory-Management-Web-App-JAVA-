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

    // find all inventory items
    public List<Inventory> findAll() {
        return dsl.select(ID, PRODUCT_ID, QUANTITY)
                  .from(INVENTORY)
                  .fetch(i -> new Inventory(
                      i.get(ID),
                      i.get(PRODUCT_ID),
                      i.get(QUANTITY)
                  ));
    }

    // add a new inventory item
    public Integer addInventory(Inventory inventory) {
        return dsl.resultQuery(
        "INSERT INTO inventory (product_id, quantity) OUTPUT inserted.id VALUES (?, ?)",
        inventory.productId(), inventory.quantity()
        ).fetchOneInto(Integer.class);
    }

    // update an inventory item
    public boolean updateInventory(Inventory inventory) {
        int updatedRows = dsl.update(INVENTORY)
                             .set(PRODUCT_ID, inventory.productId())
                             .set(QUANTITY, inventory.quantity())
                             .where(ID.eq(inventory.id()))
                             .execute();
        return updatedRows > 0;
    }

    // delete an inventory item
    public boolean deleteInventory(int id) {
        int deleted = dsl.deleteFrom(INVENTORY)
                         .where(ID.eq(id))
                         .execute();
        return deleted > 0;
    }
}
