package no.kh498.util.jackson.mixin;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Map;

/**
 * @author Elg
 */
public interface ConfigurationSerializableMixIn {

    @JsonValue
    Map<String, Object> serialize();
}
