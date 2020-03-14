package no.kh498.util.jackson.deserializers.bean

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer
import no.kh498.util.jackson.serializers.bean.ConfigurationSerializableSerializer.ROOT_PATH
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.ConfigurationSerialization
import java.io.IOException
import java.util.*

/**
 * @author Elg
 */
object ConfigurationSerializableDeserializer : StdDeserializer<ConfigurationSerializable>(ConfigurationSerializable::class.java) {

    @Throws(IOException::class)
    override fun deserialize(parser: JsonParser, context: DeserializationContext): ConfigurationSerializable? {
        return deserializeConfSection(parser, context)
    }

    @Throws(IOException::class)
    override fun deserializeWithType(parser: JsonParser, context: DeserializationContext,
                                     typeDeserializer: TypeDeserializer): ConfigurationSerializable? {
        return deserializeConfSection(parser, context)
    }

    private fun deserializeConfSection(parser: JsonParser, context: DeserializationContext): ConfigurationSerializable? {
        val content = context.readValue(parser, String::class.java)
        val conf = YamlConfiguration().also { it.loadFromString(content) }
        return conf.get(ROOT_PATH) as ConfigurationSerializable
    }
}
