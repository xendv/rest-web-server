package rest.web.server.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import rest.web.server.entities.Product;

import java.util.List;

// Not needed anywhere
public final class JSONSerializerDeserializer {
    static ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public static String serialize(Product product) {
        String result = null;
        try {
            result = objectMapper.writeValueAsString(product);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static String serializeList(List<Product> productList) {
        String result = null;
        try {
            result = objectMapper.writeValueAsString(productList);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static Product deserialize(String jsonString) {
        Product product = null;
        try {
            product = objectMapper.readValue(jsonString, Product.class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return product;
    }

    public static List<Product> deserializeList(String jsonArrayString) {
        List<Product> listProduct = null;
        try {
            listProduct = objectMapper.readValue(jsonArrayString, new TypeReference<List<Product>>() {
            });
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return listProduct;
    }
}
