package no.kh498.util.jackson

import com.fasterxml.jackson.core.Version
import com.fasterxml.jackson.databind.module.SimpleModule
import no.kh498.util.jackson.deserializers.BukkitDeserializers
import no.kh498.util.jackson.serializers.BukkitSerializers
import no.kh498.util.jackson.deserializers.ColoredStringDeserializer
import no.kh498.util.jackson.mixIn.BlockVectorMixIn
import no.kh498.util.jackson.mixIn.ColorMixIn
import no.kh498.util.jackson.mixIn.ConfigurationSerializableMixIn
import no.kh498.util.jackson.mixIn.VectorMixIn
import org.bukkit.Color
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.util.BlockVector
import org.bukkit.util.Vector

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

        context.setMixInAnnotations(ConfigurationSerializable::class.java, ConfigurationSerializableMixIn::class.java)
        context.setMixInAnnotations(Vector::class.java, VectorMixIn::class.java)
        context.setMixInAnnotations(BlockVector::class.java, VectorMixIn::class.java)
        context.setMixInAnnotations(Color::class.java, ColorMixIn::class.java)
    }
}
