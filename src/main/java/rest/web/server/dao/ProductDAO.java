package rest.web.server.dao;

import org.jetbrains.annotations.NotNull;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import rest.web.server.entities.Product;

import java.sql.Connection;
import java.util.List;

import static db.jooq.generated.Tables.PRODUCTS;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public final class ProductDAO {
    public static final SQLDialect SQL_DIALECT = SQLDialect.POSTGRES;
    private final String ID_SEQ_NAME = "products_id_seq";

    @NotNull
    public List<Product> all(@NotNull Connection connection) {
        return DSL.using(connection, SQL_DIALECT).select().from(PRODUCTS).fetchInto(Product.class);
    }

    @NotNull
    public List<Product> getManufacturersProducts(@NotNull Connection connection, @NotNull Integer manId) {
        return DSL.using(connection, SQL_DIALECT).select().from(PRODUCTS)
                .where(PRODUCTS.MANUFACTURER_ID.eq(manId)).fetchInto(Product.class);
    }

    public void save(@NotNull Connection connection, @NotNull Product entity) {
        var context = DSL.using(connection, SQL_DIALECT);
        entity.setId(context.nextval(ID_SEQ_NAME).intValue());
        var rec = context.newRecord(PRODUCTS, entity);
        rec.store();
    }

    public int delete(@NotNull Connection connection, @NotNull String productName) {
        var context = DSL.using(connection, SQL_DIALECT);
        var productsRecordResult = context.selectFrom(PRODUCTS)
                .where(PRODUCTS.NAME.eq(productName)).fetch();
        if (productsRecordResult.isEmpty()) return -1;
        else {
            productsRecordResult.forEach(context::executeDelete);
            return 0;
        }
    }

    // for future use, may be modified
    public void update(@NotNull Connection connection, @NotNull Product entity) {
        var rec = DSL.using(connection, SQL_DIALECT).newRecord(PRODUCTS, entity);
        rec.refresh();
    }
}