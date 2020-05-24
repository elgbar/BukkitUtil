package no.kh498.util.jackson.mixIn

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonValue
import org.bukkit.configuration.serialization.ConfigurationSerialization

/**
 * @author Elg
 */
//Do not save type information! We get that stuff from the bukkit deserializers
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
abstract class GetNameMixIn {

    @JsonValue
    abstract fun getName(): String
}
