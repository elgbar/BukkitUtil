package no.kh498.util.jackson.deserializers

import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.deser.KeyDeserializers
import no.kh498.util.jackson.deserializers.key.EnchantmentKeyDeserializer
import org.bukkit.enchantments.Enchantment

/**
 * @author Elg
 */
object BukkitKeyDeserializers : KeyDeserializers {

    override fun findKeyDeserializer(type: JavaType, config: DeserializationConfig, beanDesc: BeanDescription): KeyDeserializer? {
        return if (Enchantment::class.java.isAssignableFrom(type.rawClass)) {
            EnchantmentKeyDeserializer
        } else {
            null
        }
    }
}
