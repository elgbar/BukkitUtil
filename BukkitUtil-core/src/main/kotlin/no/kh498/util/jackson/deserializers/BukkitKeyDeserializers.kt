package no.kh498.util.jackson.deserializers

import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.deser.KeyDeserializers
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.World
import org.bukkit.enchantments.Enchantment
import org.bukkit.potion.PotionEffectType
import java.lang.IllegalArgumentException
import java.util.*

/**
 * @author Elg
 */
object BukkitKeyDeserializers : KeyDeserializers {
    override fun findKeyDeserializer(type: JavaType, config: DeserializationConfig, beanDesc: BeanDescription?): KeyDeserializer? {
        return when {
            World::class.java.isAssignableFrom(type.rawClass) -> object : KeyDeserializer() {
                override fun deserializeKey(key: String, ctxt: DeserializationContext?): Any? {
                    try {
                        return Bukkit.getWorld(UUID.fromString(key))
                    } catch (e: IllegalArgumentException) {
                        throw IllegalArgumentException("Expected a uuid of a world", e)
                    }
                }
            }
            OfflinePlayer::class.java.isAssignableFrom(type.rawClass) -> object : KeyDeserializer() {
                override fun deserializeKey(key: String, ctxt: DeserializationContext?): Any? {
                    try {
                        return Bukkit.getOfflinePlayer(UUID.fromString(key))
                    } catch (e: IllegalArgumentException) {
                        throw IllegalArgumentException("Expected a uuid of an offline player", e)
                    }
                }
            }
            PotionEffectType::class.java.isAssignableFrom(type.rawClass) -> object : KeyDeserializer() {
                override fun deserializeKey(key: String, ctxt: DeserializationContext?): Any {
                    return PotionEffectType.getByName(key)
                }
            }
            Enchantment::class.java.isAssignableFrom(type.rawClass) -> object : KeyDeserializer() {
                override fun deserializeKey(key: String, ctxt: DeserializationContext?): Any {
                    return Enchantment.getByName(key)
                }
            }
            else -> null
        }
    }
}
