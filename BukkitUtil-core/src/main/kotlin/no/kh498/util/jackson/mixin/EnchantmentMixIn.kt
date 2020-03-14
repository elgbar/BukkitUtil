package no.kh498.util.jackson.mixin

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import no.kh498.util.jackson.deserializers.key.EnchantmentKeyDeserializer
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.enchantments.EnchantmentTarget

/**
 * @author Elg
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = ConfigurationSerialization.SERIALIZED_TYPE_KEY)
@JsonDeserialize(keyUsing = EnchantmentKeyDeserializer::class)
abstract class EnchantmentMixIn {
    @get:JsonValue
    abstract val id: String?

    @get:JsonIgnore
    abstract val name: String?

    @get:JsonIgnore
    abstract val maxLevel: Int

    @get:JsonIgnore
    abstract val startLevel: Int

    @get:JsonIgnore
    abstract val itemTarget: EnchantmentTarget?
}
