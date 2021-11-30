package rest.web.server.content.managers;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import rest.web.server.entities.Product;
import rest.web.server.json.JSONSerializerDeserializer;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("NotNullNullableValidation")
public final class ContentGenerator {

    @Inject
    private ProductsDBManager productsDBManager;

    public String getProductsJSON(){
        List<Product> pl = this.productsDBManager.getAllProducts();
        return JSONSerializerDeserializer.serializeList(pl);
    }

    public String getManufacturersProductsJSON(@NotNull Integer manId){
        List<Product> pl = this.productsDBManager.getManufacturersProducts(manId);
        return JSONSerializerDeserializer.serializeList(pl);
    }

    public String contentHtml() {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head><title>Content Generator</title></head>\n" +
                "<body>\n<h1>Products:</h1>" + getProductsInHtml() + "\n</body>" +
                "\n</html>";
    }

    private String getProductsInHtml(){
        StringBuilder result = new StringBuilder();
        List<Product> pl = this.productsDBManager.getAllProducts();
        List<String> products = pl.stream()
                .map(product -> {
                    String productStr = product.toString();
                    return "\n<p>" + productStr + "</p>";
                }).collect(Collectors.toList());
        for (String productString: products) {
            result.append(productString);
        }
        return result.toString();
    }
}
