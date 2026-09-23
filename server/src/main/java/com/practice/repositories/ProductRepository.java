package com.practice.repositories;

import com.practice.models.Product;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import java.math.BigDecimal;
import java.util.List;
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


    // find all products
    public List<Product> findAll() {
    return dsl.select(ID, CATEGORY_ID, NAME, PRICE)
              .from(PRODUCTS)
              .fetch(r -> new Product(
                r.get(ID),
                r.get(CATEGORY_ID),
                r.get(NAME),
                r.get(PRICE)
              ));    
    }

    // add a new product
    public Integer addProduct(Product product){
        return dsl.resultQuery(
        "INSERT INTO products (category_id, name, price) OUTPUT inserted.id VALUES (?, ?, ?)",
        product.categoryId(), product.name(), product.price()
        ).fetchOneInto(Integer.class);
    }

    // update a product
    public boolean updateProduct(Product product){
        int updatedRows = dsl.update(PRODUCTS)
                             .set(CATEGORY_ID, product.categoryId())
                             .set(NAME, product.name())
                             .set(PRICE, product.price())
                             .where(ID.eq(product.id()))
                             .execute();
        return updatedRows > 0;

    }

    // delete a product
    public boolean deleteProduct(int id){
        int deleted = dsl.deleteFrom(PRODUCTS)
                         .where(ID.eq(id))
                         .execute();
        return deleted > 0;
    }
}
