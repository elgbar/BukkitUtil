package no.kh498.util.jackson.deserializers.bean

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer
import no.kh498.util.ConfigUtil
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

        //as it was saved as a map we read it back as a map then let the internal bukkit yaml serialization
        // handle the converting of the map to config section to a string then to the wantend object
        //
        val map: Map<String, Any> = context.readValue(parser, Map::class.java) as Map<String, Any>
//        val content = ConfigUtil.saveToString(ConfigUtil.getSectionFromMap(map))

        val conf = YamlConfiguration().also { it.set(ROOT_PATH, map) }
        return conf.get(ROOT_PATH) as ConfigurationSerializable
    }
}
