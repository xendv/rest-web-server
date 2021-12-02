package rest.web.server.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

//@SuppressWarnings("unused")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    @JsonProperty("id")
    Integer id;
    @JsonProperty("name")
    String name;
    @JsonProperty("manufacturerId")
    Integer manufacturerId;
    @JsonProperty("quantity")
    Integer quantity;

    @JsonCreator
    public Product(@JsonProperty(value = "id") Integer id,
                   @JsonProperty(value = "name", required = true) String name,
                   @JsonProperty(value = "manufacturerId") Integer manufacturerId,
                   @JsonProperty(value = "quantity") Integer quantity) {
        this.id = id;
        this.name = name;
        this.manufacturerId = manufacturerId;
        this.quantity = quantity;
    }

    @Override
    public @NotNull
    String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", manufacturerId=" + manufacturerId +
                ", quantity=" + quantity +
                '}';
    }
}