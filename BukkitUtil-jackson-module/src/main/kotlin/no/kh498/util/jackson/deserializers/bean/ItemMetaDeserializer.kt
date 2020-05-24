package no.kh498.util.jackson.deserializers.bean

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer
import com.fasterxml.jackson.databind.ser.PropertyWriter
import com.fasterxml.jackson.databind.type.MapType
import no.kh498.util.jackson.mixIn.ItemMetaMixIn.Companion.ENCHANTMENT
import no.kh498.util.jackson.mixIn.ItemMetaMixIn.Companion.FLAGS
import no.kh498.util.jackson.mixIn.ItemMetaMixIn.Companion.TYPE_FIELD
import no.kh498.util.jackson.mixIn.ItemMetaMixIn.Companion.classMap
import org.bukkit.inventory.meta.ItemMeta

/**
 * @author Elg
 */
object ItemMetaDeserializer : StdDeserializer<ItemMeta>(ItemMeta::class.java) {

    /////////////////////////////////
    // START stuff stolen from HOT //
    /////////////////////////////////

    private fun ObjectMapper.serializableProperties(clazz: Class<*>): HashMap<String, JavaType> {
        val props = serializerProviderInstance.findValueSerializer(clazz)
        val map = HashMap<String, JavaType>()
        props.properties().forEach { prop: PropertyWriter -> map[prop.name] = prop.type }
        return map
    }

    ///////////////////////////////
    // END stuff stolen from HOT //
    ///////////////////////////////

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): ItemMeta {
        val mapType: MapType = ctxt.typeFactory.constructMapType(MutableMap::class.java, String::class.java, Any::class.java)
        val map = ctxt.readValue<MutableMap<String, Any?>>(p, mapType) ?: error("failed to read value as a map")

        val typeName = map[TYPE_FIELD] ?: error("Failed to find the type name field")


        //remove the type field so we do not need `@JsonIgnoreProperties(ignoreUnknown = true)` for ItemMetaMixIn
        map.remove(TYPE_FIELD)

        //find the wanted CraftMeta class via reflection
        val metaClasses = classMap.filter { (_, name) -> name == typeName }.keys

        //sanity check
        require(metaClasses.size == 1) {
            "Found ${if (metaClasses.isEmpty()) "no" else "multiple"} potential classes! $metaClasses"
        }

        //use the type information to find what class the meta is expecting at different properties
        val metaClass = metaClasses.first()
        val typeMap = HashMap<String, JavaType>()
        typeMap.putAll((p.codec as ObjectMapper).serializableProperties(metaClass))
        typeMap.putAll((p.codec as ObjectMapper).serializableProperties(ItemMeta.Spigot::class.java))

        //manually serialize this class with the type information gained above
        val convertedMap = LinkedHashMap<String, Any>()
        for ((key, value) in map) {

            if (value == null) continue //no null values allowed!
            val subtype = typeMap[key] ?: error("Failed to find type of key '$key'")

            //some of the keys should be specially handled, it's kind of fragile doing it this way
            // but it has been so for a long time. The alternative is to write this bit for each
            // NMS that exists

            convertedMap[key] = when (key) {
                //do not convert enchantment, it is done internally for whatever reason
                ENCHANTMENT -> value
                //flags is expected to be a set of strings
                FLAGS -> HashSet<String>(value as Collection<String>)
                else -> (p.codec as ObjectMapper).convertValue(value, subtype)
            }
        }

        //now let bukkit handle the rest. There should be a constructor that will handle the
        val constructor = metaClass.getDeclaredConstructor(MutableMap::class.java)
        constructor.isAccessible = true
        return constructor.newInstance(convertedMap)
    }

    override fun deserializeWithType(p: JsonParser, ctxt: DeserializationContext, typeDeserializer: TypeDeserializer?): Any {
        return deserialize(p, ctxt)
    }
}
