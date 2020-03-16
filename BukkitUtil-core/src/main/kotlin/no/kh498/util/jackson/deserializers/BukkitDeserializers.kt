package no.kh498.util.jackson.deserializers

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.deser.Deserializers
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import no.kh498.util.jackson.BukkitModule
import no.kh498.util.jackson.deserializers.bean.ConfigurationSerializableDeserializer
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.DelegateDeserialization
import org.bukkit.enchantments.Enchantment
import org.bukkit.potion.PotionEffectType
import java.util.*

/**
 * @author Elg
 */
class BukkitDeserializers(private val bukkitModule: BukkitModule) : Deserializers.Base() {
    override fun findBeanDeserializer(type: JavaType, config: DeserializationConfig,
                                      beanDesc: BeanDescription): JsonDeserializer<*>? {
        return if (World::class.java.isAssignableFrom(type.rawClass)) {
            object : StdDeserializer<World>(World::class.java) {
                override fun deserialize(p: JsonParser, ctxt: DeserializationContext): World {
                    val uuid = ctxt.readValue(p, UUID::class.java)
                    return Bukkit.getWorld(uuid)
                }
            }
        } else if (PotionEffectType::class.java.isAssignableFrom(type.rawClass)) {
            object : StdDeserializer<PotionEffectType>(PotionEffectType::class.java) {
                override fun deserialize(p: JsonParser, ctxt: DeserializationContext): PotionEffectType {
                    val name = ctxt.readValue(p, String::class.java)
                    return PotionEffectType.getByName(name)
                }
            }
        } else if (Enchantment::class.java.isAssignableFrom(type.rawClass)) {
            object : StdDeserializer<Enchantment>(Enchantment::class.java) {
                override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Enchantment {
                    val name = ctxt.readValue(p, String::class.java)
                    return Enchantment.getByName(name)
                }
            }
        } else if (bukkitModule.colorizeStringsByDefault && String::class.java.isAssignableFrom(type.rawClass)) {
            ColoredStringDeserializer
        } else {
            null
        }
    }
}
