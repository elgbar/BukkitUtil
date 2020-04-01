package no.kh498.util.jackson.mixIn

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.bukkit.configuration.serialization.ConfigurationSerialization

/**
 * @author Elg
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = ConfigurationSerialization.SERIALIZED_TYPE_KEY)
interface ConfigurationSerializableMixIn {


}
