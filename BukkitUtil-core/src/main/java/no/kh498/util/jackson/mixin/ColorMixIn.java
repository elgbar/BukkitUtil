package no.kh498.util.jackson.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Elg
 */
public interface ColorMixIn {

    @JsonProperty("RED")
    int getRed();

    @JsonProperty("GREEN")
    int getGreen();

    @JsonProperty("BLUE")
    int getBlue();
}
