package no.kh498.util.jackson.serializers.bean

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.jsontype.TypeSerializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import no.kh498.util.ConfigUtil
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.YamlConfiguration
import java.io.IOException

/**
 * @author Elg
 */
object ConfigurationSectionSerializer : StdSerializer<ConfigurationSection>(ConfigurationSection::class.java) {

    override fun serialize(value: ConfigurationSection, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeString(ConfigUtil.saveToString(value))
    }
}
