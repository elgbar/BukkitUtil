package no.kh498.util.jackson.mixIn

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.bukkit.configuration.serialization.ConfigurationSerialization

/**
 * @author Elg
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = ConfigurationSerialization.SERIALIZED_TYPE_KEY)
interface ConfigurationSerializableMixIn {}
