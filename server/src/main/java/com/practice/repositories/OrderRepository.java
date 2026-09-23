package com.practice.repositories;

import com.practice.models.Order;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import java.time.LocalDateTime;
import java.util.List;
import static org.jooq.impl.DSL.*;

public class OrderRepository {
    private final DSLContext dsl;

    public OrderRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    private static final Table<Record> ORDERS = table("orders");
    private static final Field<Integer> ID = field("id", Integer.class);
    private static final Field<LocalDateTime> ORDER_DATE = field("order_date", LocalDateTime.class);
    private static final Field<String> STATUS = field("status", String.class);

    // find all orders
    public List<Order> findAll() {
        return dsl.select(ID, ORDER_DATE, STATUS)
                  .from(ORDERS)
                  .fetch(r -> new Order(
                      r.get(ID),
                      r.get(ORDER_DATE),
                      r.get(STATUS)
                  ));
    }

    // add a new order
    public Integer addOrder(Order order) {
        return dsl.resultQuery(
        "INSERT INTO orders (status) OUTPUT inserted.id VALUES (?)",
        order.status()
        ).fetchOneInto(Integer.class);
    }

    // update an order
    public boolean updateOrder(Order order) {
        int updatedRows = dsl.update(ORDERS)
                             .set(STATUS, order.status())
                             .where(ID.eq(order.id()))
                             .execute();
        return updatedRows > 0;
    }

    // delete an order
    public boolean deleteOrder(int id) {
        int deleted = dsl.deleteFrom(ORDERS)
                         .where(ID.eq(id))
                         .execute();
        return deleted > 0;
    }
}
