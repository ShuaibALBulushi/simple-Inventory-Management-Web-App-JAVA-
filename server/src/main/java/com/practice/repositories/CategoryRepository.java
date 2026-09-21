package com.practice.repositories;

import com.practice.models.Category;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import java.util.List;
import static org.jooq.impl.DSL.*;

public class CategoryRepository {
    
    private final DSLContext dsl;

    public CategoryRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    private static final Table<Record> CATEGORIES = table("categories");
    private static final Field<Integer> ID = field("id", Integer.class);
    private static final Field<String> NAME = field("name", String.class);
}
