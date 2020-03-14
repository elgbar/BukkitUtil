package no.kh498.util.jackson.deserializers.bean

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.ConfigurationSerialization
import java.io.IOException
import java.util.*

/**
 * @author Elg
 */
object ConfigurationSerializableDeserializer : StdDeserializer<ConfigurationSerializable>(ConfigurationSerializable::class.java) {

    @Throws(IOException::class)
    override fun deserialize(parser: JsonParser, context: DeserializationContext): ConfigurationSerializable {
        return deserializeConfSection(parser, context)
    }

    @Throws(IOException::class)
    override fun deserializeWithType(parser: JsonParser, context: DeserializationContext,
                                     typeDeserializer: TypeDeserializer): ConfigurationSerializable {
        return deserializeConfSection(parser, context)
    }

    private fun deserializeConfSection(parser: JsonParser, context: DeserializationContext): ConfigurationSerializable {
        val map: Map<String, Any>
        try {
            map = context.readValue(parser, Map::class.java) as Map<String, Any>
        } catch (e: ClassCastException) {
            throw IllegalStateException(
                    "Expected to find a Map<String,Object> but found" + context.readTree(parser).nodeType, e)
        }
        return recSer(map) as ConfigurationSerializable
    }

    private fun recSer(map: Map<String, Any>): Any {
        val serializedMap: MutableMap<String, Any?> = HashMap()
        for ((key, value) in map) {
            if (value is Map<*, *>) {
                @Suppress("UNCHECKED_CAST")
                serializedMap[key] = recSer(value as MutableMap<String, Any>)
            } else {
                serializedMap[key] = value
            }
        }
        return ConfigurationSerialization.deserializeObject(serializedMap)
    }
}
