package no.kh498.util.jackson.serializers.bean

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.yaml.snakeyaml.Yaml
import java.io.IOException

/**
 * @author Elg
 */
object ConfigurationSerializableSerializer : StdSerializer<ConfigurationSerializable>(ConfigurationSerializable::class.java) {

    @Throws(IOException::class)
    override fun serialize(ser: ConfigurationSerializable, gen: JsonGenerator, provider: SerializerProvider) {
        val yaml: Yaml
        try {
            val yamlField = YamlConfiguration::class.java.getDeclaredField("yaml")
            yamlField.isAccessible = true
            yaml = yamlField[YamlConfiguration()] as Yaml
        } catch (e: NoSuchFieldException) {
            throw IllegalStateException("Failed to get internal yaml field of YamlConfig", e)
        } catch (e: IllegalAccessException) {
            throw IllegalStateException("Failed to get internal yaml field of YamlConfig", e)
        } catch (e: ClassCastException) {
            throw IllegalStateException("Failed to get internal yaml field of YamlConfig", e)
        }
        ser.serialize()


//        YamlConfiguration.
//        gen.writeObject(map);
    }
}
