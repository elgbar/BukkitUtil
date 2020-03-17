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

                    override fun serialize(meta: ItemMeta, gen: JsonGenerator, provider: SerializerProvider) {
                        val map = LinkedHashMap<String, Any?>()

                        map[ItemMetaMixIn.TYPE_FIELD] = ItemMetaMixIn.classMap[meta::class.java]
                                ?: error("Unknown item meta ${meta::class.java}. Perhaps you are using an unsupported version of BukkitUtil?")


                        //the spigot class is weird so we add it as an extra class
                        map.putAll(mapper.convertValue<MutableMap<String, Any?>>(meta.spigot(), mapType))
                        map.putAll(mapper.convertValue<MutableMap<String, Any?>>(meta, mapType))

                        //filter out default values
                        gen.writeObject(map.filterValues { value ->
                            when (value) {
                                null -> false
                                is Collection<*> -> value.isNotEmpty()
                                is Map<*, *> -> value.isNotEmpty()
                                is String -> value.isNotEmpty()
                                is Int? -> value != 0
                                is Long? -> value != 0
                                is Float? -> value != 0f
                                is Boolean? -> value != false
                                is Double? -> value != 0.0
                                is Byte? -> value != 0
                                is Short? -> value != 0
                                else -> true
                            }
                        })
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
