package no.kh498.util.jackson.serializers.bean

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.TSFBuilder
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.jsontype.TypeSerializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLFactoryBuilder
import com.fasterxml.jackson.dataformat.yaml.YAMLParser
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.configuration.file.YamlConstructor
import org.bukkit.configuration.file.YamlRepresenter
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml
import java.io.IOException

/**
 * @author Elg
 */
object ConfigurationSerializableSerializer : StdSerializer<ConfigurationSerializable>(ConfigurationSerializable::class.java) {

    const val ROOT_PATH = "root"

    @Throws(IOException::class)
    override fun serialize(ser: ConfigurationSerializable, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeObject(ser.serialize())
    }

    override fun serializeWithType(value: ConfigurationSerializable?, gen: JsonGenerator?, serializers: SerializerProvider?, typeSer: TypeSerializer?) {
        super.serializeWithType(value, gen, serializers, typeSer)
    }
}
