package no.kh498.util.jackson.mixIn

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonValue
import org.bukkit.configuration.serialization.ConfigurationSerialization

/**
 * @author Elg
 */
abstract class GetNameMixIn {

    @JsonValue
    abstract fun getName(): String
}
