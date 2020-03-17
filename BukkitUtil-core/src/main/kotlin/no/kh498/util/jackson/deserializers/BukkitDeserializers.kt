package no.kh498.util.jackson.deserializers

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.io.SegmentedStringWriter
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.deser.Deserializers
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider
import com.fasterxml.jackson.databind.ser.PropertyWriter
import com.fasterxml.jackson.databind.type.MapType
import no.kh498.util.jackson.BukkitModule
import no.kh498.util.jackson.mixIn.ItemMetaMixIn
import no.kh498.util.jackson.mixIn.ItemMetaMixIn.Companion.TYPE_FIELD
import no.kh498.util.jackson.mixIn.ItemMetaMixIn.Companion.classMap
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.World
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.potion.PotionEffectType
import java.util.*
import kotlin.collections.HashSet
import kotlin.collections.LinkedHashMap

/**
 * @author Elg
 */
class BukkitDeserializers(private val bukkitModule: BukkitModule) : Deserializers.Base() {
    override fun findBeanDeserializer(type: JavaType, config: DeserializationConfig,
                                      beanDesc: BeanDescription): JsonDeserializer<*>? {
        return when {
            World::class.java.isAssignableFrom(type.rawClass) -> {
                object : StdDeserializer<World>(World::class.java) {
                    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): World {
                        val uuid = ctxt.readValue(p, UUID::class.java)
                        return Bukkit.getWorld(uuid)
                    }
                }
            }

            OfflinePlayer::class.java.isAssignableFrom(type.rawClass) -> {
                object : StdDeserializer<OfflinePlayer>(OfflinePlayer::class.java) {
                    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): OfflinePlayer {
                        val uuid = ctxt.readValue(p, UUID::class.java)
                        return Bukkit.getOfflinePlayer(uuid)
                    }
                }
            }
            PotionEffectType::class.java.isAssignableFrom(type.rawClass) -> {
                object : StdDeserializer<PotionEffectType>(PotionEffectType::class.java) {
                    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): PotionEffectType {
                        val name = ctxt.readValue(p, String::class.java)
                        return PotionEffectType.getByName(name)
                    }
                }
            }
            Enchantment::class.java.isAssignableFrom(type.rawClass) -> {
                object : StdDeserializer<Enchantment>(Enchantment::class.java) {
                    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Enchantment {
                        val name = ctxt.readValue(p, String::class.java)
                        return Enchantment.getByName(name)
                    }
                }
            }
            OfflinePlayer::class.java.isAssignableFrom(type.rawClass) -> {
                object : StdDeserializer<OfflinePlayer>(OfflinePlayer::class.java) {
                    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): OfflinePlayer {
                        val uuid = ctxt.readValue(p, UUID::class.java)
                        return Bukkit.getOfflinePlayer(uuid)
                    }
                }
            }
            ItemMeta::class.java.isAssignableFrom(type.rawClass) -> {

                data class PropertyMetadata(
                        val name: String,
                        val type: JavaType,
                        val defaultValue: String,
                        val required: Boolean,
                        val description: String,
                        val virtual: Boolean
                ) {}

                class ItemMetaDeserializer : StdDeserializer<ItemMeta>(ItemMeta::class.java) {

                    val mapper: ObjectMapper = ObjectMapper().registerModule(BukkitModule(bukkitModule.colorizeStringsByDefault, true))

                    val mapType: MapType = mapper.typeFactory.constructMapType(MutableMap::class.java, String::class.java, Any::class.java)

                    /////////////////////////////////
                    // START stuff stolen from HOT //
                    /////////////////////////////////

                    fun createDSP(): DefaultSerializerProvider {
                        val jfac = JsonFactory.builder().build()
                        val gen: JsonGenerator = jfac.createGenerator(SegmentedStringWriter(jfac._getBufferRecycler()))
                        val cfg: SerializationConfig = mapper.serializationConfig
                        cfg.initialize(gen)

                        return DefaultSerializerProvider.Impl().createInstance(cfg, mapper.serializerFactory)
                    }

                    fun serializableProperties(type: Class<*>): HashMap<String, JavaType> {
                        val props = ser.findValueSerializer(type)
                        val map = HashMap<String, JavaType>()
                        props.properties().forEach { prop: PropertyWriter -> map[prop.name] = prop.type }
                        return map
                    }

                    val ser: DefaultSerializerProvider = createDSP()

                    ///////////////////////////////
                    // END stuff stolen from HOT //
                    ///////////////////////////////

                    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): ItemMeta {
                        val map = ctxt.readValue<MutableMap<String, Any?>>(p, mapType)!!
                        val typeName = map[TYPE_FIELD] ?: error("Failed to find the type name field")

                        //remove the type field so we do not need `@JsonIgnoreProperties(ignoreUnknown = true)` for ItemMetaMixIn
                        map.remove(TYPE_FIELD)

                        val metaClasses = classMap.filter { (_, name) -> name == typeName }.keys

                        require(metaClasses.size == 1) { "Found multiple potential classes! $metaClasses" }


                        val metaClass = metaClasses.first()
                        val typeInfo = serializableProperties(metaClass)

                        val convertedMap = LinkedHashMap<String, Any>()
                        for ((key, value) in map) {

                            if (value == null) continue //no null values allowed!
                            val subtype = typeInfo[key]!!

                            convertedMap[key] = when (key) {
                                //do not convert enchantment, it is done internally for whatever reason
                                ItemMetaMixIn.ENCHANTMENT -> value
                                //flags is expected to be a set
                                ItemMetaMixIn.FLAGS -> HashSet<String>(value as Collection<String>)
                                else -> (p.codec as ObjectMapper).convertValue(value, subtype)
                            }
                        }
                        val constructor = metaClass.getDeclaredConstructor(MutableMap::class.java)
                        constructor.isAccessible = true
                        return constructor.newInstance(convertedMap)
                    }
                }

                return if (bukkitModule.noCustomItemMetaSerialization) null
                else ItemMetaDeserializer()
            }

            bukkitModule.colorizeStringsByDefault && String::class.java.isAssignableFrom(type.rawClass) -> {
                ColoredStringDeserializer
            }
            else -> {
                null
            }
        }
    }

    companion object {
        const val VALUE_DELEGATOR_NAME = "value"
    }
}
