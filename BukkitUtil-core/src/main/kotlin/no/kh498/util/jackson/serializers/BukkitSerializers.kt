package no.kh498.util.jackson.serializers

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.ser.Serializers
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.fasterxml.jackson.databind.type.MapType
import no.kh498.util.jackson.BukkitModule
import no.kh498.util.jackson.mixIn.ItemMetaMixIn
import no.kh498.util.jackson.serializers.bean.ConfigurationSectionSerializer
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.inventory.meta.ItemMeta


/**
 * @author Elg
 */
class BukkitSerializers(val bukkitModule: BukkitModule) : Serializers.Base() {
    override fun findSerializer(config: SerializationConfig, type: JavaType, beanDesc: BeanDescription): JsonSerializer<*>? {
        return when {
            ItemMeta::class.java.isAssignableFrom(type.rawClass) -> {

                class ItemMetaSerializer : StdSerializer<ItemMeta>(ItemMeta::class.java) {

                    val mapper: ObjectMapper = ObjectMapper().registerModule(BukkitModule(bukkitModule.colorizeStringsByDefault, true))
                    val mapType: MapType = mapper.typeFactory.constructMapType(MutableMap::class.java, String::class.java, Any::class.java)

                    override fun serialize(value: ItemMeta, gen: JsonGenerator, provider: SerializerProvider) {
                        val map = LinkedHashMap<String, Any?>()

                        map[ItemMetaMixIn.TYPE_FIELD] = ItemMetaMixIn.classMap[value::class.java]
                                ?: error("Unknown item meta ${value::class.java}. Perhaps you are using an unsupported version of BukkitUtil?")


                        //the spigot class is weird so we add it as an extra class
                        map.putAll(mapper.convertValue<MutableMap<String, Any?>>(value.spigot(), mapType))
                        map.putAll(mapper.convertValue<MutableMap<String, Any?>>(value, mapType))

                        gen.writeObject(map)
                    }
                }

                return if (bukkitModule.noCustomItemMetaSerialization) null
                else ItemMetaSerializer()
            }
            ConfigurationSection::class.java.isAssignableFrom(type.rawClass) -> {
                ConfigurationSectionSerializer
            }
            else -> null
        }
    }


}
