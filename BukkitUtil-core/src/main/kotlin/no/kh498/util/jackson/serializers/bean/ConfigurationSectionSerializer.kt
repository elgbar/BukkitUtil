package no.kh498.util.jackson.serializers.bean

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.jsontype.TypeSerializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import org.bukkit.configuration.ConfigurationSection
import java.io.IOException

/**
 * @author Elg
 */
object ConfigurationSectionSerializer : StdSerializer<ConfigurationSection>(ConfigurationSection::class.java) {

    @Throws(IOException::class)
    override fun serialize(value: ConfigurationSection, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeObject(value.getValues(false))
    }

    @Throws(IOException::class)
    override fun serializeWithType(value: ConfigurationSection, gen: JsonGenerator, serializers: SerializerProvider,
                                   typeSer: TypeSerializer) {
        gen.writeObject(value.getValues(false))
    }
}
