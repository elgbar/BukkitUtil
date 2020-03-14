package no.kh498.util.jackson

import com.fasterxml.jackson.core.Version
import com.fasterxml.jackson.databind.module.SimpleModule
import no.kh498.util.jackson.deserializers.BukkitDeserializers
import no.kh498.util.jackson.deserializers.BukkitKeyDeserializers
import no.kh498.util.jackson.mixin.ColorMixIn
import no.kh498.util.jackson.mixin.EnchantmentMixIn
import no.kh498.util.jackson.mixin.MaterialDataMixIn
import no.kh498.util.jackson.mixin.VectorMixIn
import no.kh498.util.jackson.serializers.BukkitSerializers
import org.bukkit.Color
import org.bukkit.enchantments.Enchantment
import org.bukkit.material.MaterialData
import org.bukkit.util.Vector

/**
 * @author Elg
 */
class BukkitModule : SimpleModule(Version(1, 0, 0, null, "no.kh498.util", "BukkitUtil")) {
    override fun setupModule(context: SetupContext) {
        super.setupModule(context)
        context.addSerializers(BukkitSerializers)
        context.addDeserializers(BukkitDeserializers)
        context.addKeyDeserializers(BukkitKeyDeserializers)

//        context.setMixInAnnotations(ConfigurationSerializable.class, ConfigurationSerializableMixIn.class);
        context.setMixInAnnotations(Enchantment::class.java, EnchantmentMixIn::class.java)
        context.setMixInAnnotations(MaterialData::class.java, MaterialDataMixIn::class.java)
        context.setMixInAnnotations(Vector::class.java, VectorMixIn::class.java)
        context.setMixInAnnotations(Color::class.java, ColorMixIn::class.java)
    }
}
