package no.kh498.util.jackson.serializers.bean

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import no.kh498.util.ConfigUtil
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.yaml.snakeyaml.Yaml
import java.io.IOException

/**
 * @author Elg
 */
object ConfigurationSerializableSerializer : StdSerializer<ConfigurationSerializable>(ConfigurationSerializable::class.java) {

    const val ROOT_PATH = "root"

    @Throws(IOException::class)
    override fun serialize(ser: ConfigurationSerializable, gen: JsonGenerator, provider: SerializerProvider) {
        val yaml = YamlConfiguration()
        yaml.set(ROOT_PATH, ser)
        
        gen.writeString(yaml.saveToString())
    }
}
