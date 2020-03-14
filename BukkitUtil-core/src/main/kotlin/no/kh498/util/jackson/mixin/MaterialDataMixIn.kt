package no.kh498.util.jackson.mixin

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeInfo
import javafx.scene.paint.Material
import org.bukkit.configuration.serialization.ConfigurationSerialization

/**
 * @author Elg
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = ConfigurationSerialization.SERIALIZED_TYPE_KEY)
abstract class MaterialDataMixIn {
    @get:JsonIgnore
    abstract val itemTypeId: Int

    @get:JsonProperty("type")
    abstract val itemType: Material?
}
