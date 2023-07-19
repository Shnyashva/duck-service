package autotests.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(fluent = true)
public class DuckProperties {

    @JsonProperty
    public String color;
    @JsonProperty
    public int height;
    @JsonProperty
    public String material;
    @JsonProperty
    public String sound;
    @JsonProperty
    public String wingsState;
}