package no.kh498.util.jackson.serializers

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.jsontype.TypeSerializer
import com.fasterxml.jackson.databind.ser.Serializers
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import no.kh498.util.jackson.mixIn.ItemStackMixIn
import no.kh498.util.jackson.serializers.bean.ConfigurationSectionSerializer
import org.bukkit.Bukkit
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.serialization.ConfigurationSerialization.SERIALIZED_TYPE_KEY
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

/**
 * @author Elg
 */
object BukkitSerializers : Serializers.Base() {
    override fun findSerializer(config: SerializationConfig, type: JavaType, beanDesc: BeanDescription): JsonSerializer<*>? {
        return when {
//            ItemStack::class.java.isAssignableFrom(type.rawClass) -> {
//                object : StdSerializer<ItemStack>(ItemStack::class.java) {
//                    override fun serialize(value: ItemStack, gen: JsonGenerator, provider: SerializerProvider) {
//
//                        //we have to serialize ItemStack with a custom serializer as it has some special rules that
//                        // is hard or impossible to specify with mix ins
//                        val map = LinkedHashMap<String, Any>()
//                        map[SERIALIZED_TYPE_KEY] = "ItemStack"
//                        map.putAll(value.serialize())
//                        gen.writeObject(map)
//                    }
//
//                    override fun serializeWithType(value: ItemStack, gen: JsonGenerator, serializers: SerializerProvider, typeSer: TypeSerializer?) {
//                        serialize(value, gen, serializers)
//                    }
//                }
//            }
            ConfigurationSection::class.java.isAssignableFrom(type.rawClass) -> {
                ConfigurationSectionSerializer
            }
            else -> null
        }
    }
}
