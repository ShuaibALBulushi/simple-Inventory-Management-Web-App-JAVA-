package com.practice.repositories;

import com.practice.models.OrderItem;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import java.math.BigDecimal;
import java.util.List;
import static org.jooq.impl.DSL.*;

public class OrderItemRepository {
    
    private final DSLContext dsl;

    public OrderItemRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    private static final Table<Record> ORDER_ITEMS = table("order_items");
    private static final Field<Integer> ID = field("id", Integer.class);
    private static final Field<Integer> ORDER_ID = field("order_id", Integer.class);
    private static final Field<Integer> PRODUCT_ID = field("product_id", Integer.class);
    private static final Field<Integer> QUANTITY = field("quantity", Integer.class);
    private static final Field<BigDecimal> UNIT_PRICE = field("unit_price", BigDecimal.class);

    // find all order items
    public List<OrderItem> findAll() {
        return dsl.select(ID, ORDER_ID, PRODUCT_ID, QUANTITY, UNIT_PRICE)
                  .from(ORDER_ITEMS)
                  .fetch(r -> new OrderItem(
                      r.get(ID),
                      r.get(ORDER_ID),
                      r.get(PRODUCT_ID),
                      r.get(QUANTITY),
                      r.get(UNIT_PRICE)
                  ));
    }

    // add a new order item
    public Integer addOrderItem(OrderItem item) {
        return dsl.insertInto(ORDER_ITEMS)
                  .columns(ORDER_ID, PRODUCT_ID, QUANTITY, UNIT_PRICE)
                  .values(item.orderId(), item.productId(), item.quantity(), item.unitPrice())
                  .returningResult(ID)
                  .fetchOneInto(Integer.class);
    }

    // update an order item
    public boolean updateOrderItem(OrderItem item) {
        int updatedRows = dsl.update(ORDER_ITEMS)
                             .set(ORDER_ID, item.orderId())
                             .set(PRODUCT_ID, item.productId())
                             .set(QUANTITY, item.quantity())
                             .set(UNIT_PRICE, item.unitPrice())
                             .where(ID.eq(item.id()))
                             .execute();
        return updatedRows > 0;
    }

    // delete an order item
    public boolean deleteOrderItem(int id) {
        int deleted = dsl.deleteFrom(ORDER_ITEMS)
                         .where(ID.eq(id))
                         .execute();
        return deleted > 0;
    }
}
