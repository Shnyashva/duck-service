package autotests.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(fluent = true)
public class Duck {

    @JsonProperty
    private String color;
    @JsonProperty
    private double height;
    @JsonProperty
    private int id;
    @JsonProperty
    private String material;
    @JsonProperty
    private String sound;
    @JsonProperty
    private String wingsState;
}