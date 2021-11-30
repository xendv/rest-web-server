package rest.web.server.dao;

import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import rest.web.server.entities.Product;

import java.sql.Connection;
import java.util.List;

import static db.jooq.generated.Tables.PRODUCTS;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public final class ProductDAO{
    private final String ID_SEQ_NAME = "products_id_seq";

    private DSLContext context;

    public void setConnection(@NotNull Connection connection) {
        this.context = DSL.using(connection, SQLDialect.POSTGRES);
    }

    @NotNull
    public List<Product> all() {
        return context.select().from(PRODUCTS).fetchInto(Product.class);
    }

    @NotNull
    public List<Product> getManufacturersProducts(@NotNull Integer manId) {
        return context.select().from(PRODUCTS).where(PRODUCTS.MANUFACTURER_ID.eq(manId)).fetchInto(Product.class);
    }

    public void save(@NotNull Product entity) {
        entity.setId(context.nextval(ID_SEQ_NAME).intValue());
        var rec = context.newRecord(PRODUCTS, entity);
        rec.store();
    }

    public int delete(@NotNull String productName) {
        var productsRecordResult = context.selectFrom(PRODUCTS)
                .where(PRODUCTS.NAME.eq(productName)).fetch();
        if (productsRecordResult.isEmpty()) return -1;
        else {
            productsRecordResult.forEach(productsRecord -> context.executeDelete(productsRecord));
            return 0;
        }
    }

    // for future use, may be modified
    public void update(@NotNull Product entity) {
        var rec = context.newRecord(PRODUCTS, entity);
        rec.refresh();
    }
}