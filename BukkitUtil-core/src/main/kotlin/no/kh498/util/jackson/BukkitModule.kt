package no.kh498.util.jackson

import com.fasterxml.jackson.core.Version
import com.fasterxml.jackson.databind.BeanDescription
import com.fasterxml.jackson.databind.DeserializationConfig
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier
import com.fasterxml.jackson.databind.module.SimpleModule
import no.kh498.util.jackson.deserializers.BukkitDeserializers
import no.kh498.util.jackson.serializers.BukkitSerializers
import no.kh498.util.jackson.deserializers.ColoredStringDeserializer

/**
 * @author Elg
 */
class BukkitModule(
        /**
         * If [String]s will be serialized with [ColoredStringDeserializer] by default.
         *
         * @see ColoredStringDeserializer
         */
        var colorizeStringsByDefault: Boolean = false) : SimpleModule(Version(1, 0, 0, null, "no.kh498.util", "BukkitUtil")) {

    override fun setupModule(context: SetupContext) {
        super.setupModule(context)

        context.addSerializers(BukkitSerializers)
        context.addDeserializers(BukkitDeserializers(this))
    }


}
