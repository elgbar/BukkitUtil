package no.kh498.util.jackson

import com.fasterxml.jackson.core.Version
import com.fasterxml.jackson.databind.module.SimpleModule
import no.kh498.util.jackson.deserializers.BukkitDeserializers
import no.kh498.util.jackson.serializers.BukkitSerializers

/**
 * @author Elg
 */
class BukkitModule : SimpleModule(Version(1, 0, 0, null, "no.kh498.util", "BukkitUtil")) {
    override fun setupModule(context: SetupContext) {
        super.setupModule(context)
        context.addSerializers(BukkitSerializers)
        context.addDeserializers(BukkitDeserializers)
    }
}
