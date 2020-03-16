package no.kh498.util.jackson

import com.fasterxml.jackson.core.Version
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.guava.GuavaModule
import no.kh498.util.jackson.deserializers.BukkitDeserializers
import no.kh498.util.jackson.deserializers.ColoredStringDeserializer
import no.kh498.util.jackson.mixIn.*
import no.kh498.util.jackson.serializers.BukkitSerializers
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.block.banner.Pattern
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.enchantments.Enchantment
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
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
        context.setMixInAnnotations(World::class.java, WorldMixIn::class.java)
        context.setMixInAnnotations(Location::class.java, LocationMixIn::class.java)
        context.setMixInAnnotations(FireworkEffect::class.java, FireworkEffectMixIn::class.java)
        context.setMixInAnnotations(Pattern::class.java, PatternMixIn::class.java)
        context.setMixInAnnotations(PotionEffect::class.java, PotionEffectMixIn::class.java)

        context.setMixInAnnotations(PotionEffectType::class.java, GetNameMixIn::class.java)
        context.setMixInAnnotations(Enchantment::class.java, GetNameMixIn::class.java)


    }

    override fun getDependencies(): List<GuavaModule> {
        return listOf(GuavaModule())
    }
}
