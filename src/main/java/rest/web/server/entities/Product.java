package rest.web.server.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    @JsonProperty("id")
    int id;
    @NotNull
    @JsonProperty("name")
    String name;
    @JsonProperty("manufacturerId")
    int manufacturerId;
    @JsonProperty("quantity")
    int quantity;

    public Product(@NotNull String name, int manufacturerId, int quantity){
        this.name = name;
        this.manufacturerId = manufacturerId;
        this.quantity = quantity;
    }

    public Product(int id, @NotNull String name, int manufacturerId, int quantity){
        this.id = id;
        this.name = name;
        this.manufacturerId = manufacturerId;
        this.quantity = quantity;
    }

    @JsonCreator
    public Product(@JsonProperty(value = "id", required = true) Integer id,
                   @NotNull @JsonProperty(value = "name", required = true) String name,
                   @JsonProperty(value = "manufacturerId", required = true) Integer manufacturerId,
                   @JsonProperty(value = "quantity", required = true) Integer quantity) {
        this.id = id;
        this.name = name;
        this.manufacturerId = manufacturerId;
        this.quantity = quantity;
    }

    @Override
    public @NotNull String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", manufacturerId=" + manufacturerId +
                ", quantity=" + quantity +
                '}';
    }
}