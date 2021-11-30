package rest.web.server.content.managers;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import rest.web.server.dao.ProductDAO;
import rest.web.server.entities.Product;
import rest.web.server.initializers.JDBCSettingsProvider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ProductsDBManager {
    public static final @NotNull
    JDBCSettingsProvider JDBC_SETTINGS = JDBCSettingsProvider.DEFAULT;

    @Inject
    private ProductDAO productDAO;

    public List<Product> getAllProducts(){
        List<Product> productsList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_SETTINGS.url(), JDBC_SETTINGS.login(), JDBC_SETTINGS.password())) {
            productDAO.setConnection(connection);
            productsList = productDAO.all();
        }
        catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
        return productsList;
    }

    public List<Product> getManufacturersProducts(@NotNull Integer manId){
        List<Product> productsList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_SETTINGS.url(), JDBC_SETTINGS.login(), JDBC_SETTINGS.password())) {
            productDAO.setConnection(connection);
            productsList = productDAO.getManufacturersProducts(manId);
        }
        catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
        return productsList;
    }

    public void addNewProduct(Product product){
        try (Connection connection = DriverManager.getConnection(JDBC_SETTINGS.url(), JDBC_SETTINGS.login(), JDBC_SETTINGS.password())) {
            productDAO.setConnection(connection);
            productDAO.save(product);
        }
        catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
    }

    public int deleteProduct(@NotNull String productName){
        try (Connection connection = DriverManager.getConnection(JDBC_SETTINGS.url(), JDBC_SETTINGS.login(), JDBC_SETTINGS.password())) {
            productDAO.setConnection(connection);
            return productDAO.delete(productName);
        }
        catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return -1;
        }
    }
}
