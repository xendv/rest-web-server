package rest.web.server.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Manufacturer {
    @JsonProperty("manufacturerId")
    Integer id;

    @JsonCreator
    public Manufacturer(@JsonProperty(value = "manufacturerId", required = true) Integer id) {
        this.id = id;
    }

    @Override
    public @NotNull
    String toString() {
        return "Manufacturer{" +
                "id=" + id + "}";
    }
}
