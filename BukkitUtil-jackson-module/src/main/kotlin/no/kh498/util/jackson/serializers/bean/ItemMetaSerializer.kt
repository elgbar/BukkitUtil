package no.kh498.util.jackson.serializers.bean

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.jsontype.TypeSerializer
import com.fasterxml.jackson.databind.ser.PropertyWriter
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.fasterxml.jackson.databind.type.MapType
import no.kh498.util.jackson.mixIn.ItemMetaMixIn
import org.bukkit.inventory.meta.ItemMeta

/**
 * @author Elg
 */
class ItemMetaSerializer(private val serializer: BeanSerializerBase) : StdSerializer<ItemMeta>(ItemMeta::class.java) {

    private val properties: MutableSet<PropertyWriter>
    private var useDefaultSer = HashSet<ItemMeta>()

    init {
        properties = HashSet()
        properties.addAll(serializer.properties().asSequence())
    }

    override fun serialize(meta: ItemMeta, gen: JsonGenerator, provider: SerializerProvider) {
        if (useDefaultSer.contains(meta)) {
            serializer.serialize(meta, gen, provider)
            useDefaultSer.remove(meta)
            return
        }
        //mark this meta as "under construction" to not get an infinite loop
        useDefaultSer.add(meta)

        val mapType: MapType = provider.typeFactory.constructMapType(MutableMap::class.java, String::class.java, Any::class.java)
        val mapper: ObjectMapper = gen.codec as ObjectMapper

        val map = LinkedHashMap<String, Any?>()

        map[ItemMetaMixIn.TYPE_FIELD] = ItemMetaMixIn.classMap[meta.javaClass]
        map.putAll(mapper.convertValue<MutableMap<String, Any?>>(meta, mapType))

        gen.writeObject(map)
    }

    override fun serializeWithType(value: ItemMeta, gen: JsonGenerator, serializers: SerializerProvider, typeSer: TypeSerializer) {
        serialize(value, gen, serializers)
    }

    override fun handledType(): Class<ItemMeta> {
        return ItemMeta::class.java
    }

    override fun properties(): Iterator<PropertyWriter> {
        return properties.iterator()
    }
}
