package no.kh498.util.jackson

import com.fasterxml.jackson.core.Version
import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.guava.GuavaModule
import com.fasterxml.jackson.module.mrbean.MrBeanModule
import no.kh498.util.jackson.serializers.BukkitSerializers
import no.kh498.util.jackson.deserializers.BukkitDeserializers
import no.kh498.util.jackson.deserializers.BukkitKeyDeserializers
import no.kh498.util.jackson.deserializers.ColoredStringDeserializer
import no.kh498.util.jackson.mixIn.*
import org.bukkit.*
import org.bukkit.block.banner.Pattern
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.*
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
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
        val colorizeStringsByDefault: Boolean = false,
        /**
         * For internal use, disables custom serialization of [ItemMeta] to allow it to be serialized to a map
         *
         * @see BukkitSerializers
         */
        val noCustomItemMetaSerialization: Boolean = false

) : SimpleModule(Version(1, 0, 0, null, "no.kh498.util", "BukkitUtil")) {

    override fun setupModule(context: SetupContext) {
        super.setupModule(context)

        context.addSerializers(BukkitSerializers(this))
        context.addDeserializers(BukkitDeserializers(this))
        context.addKeyDeserializers(BukkitKeyDeserializers)

        context.setMixInAnnotations(ConfigurationSerializable::class.java, ConfigurationSerializableMixIn::class.java)
        context.setMixInAnnotations(Vector::class.java, VectorMixIn::class.java)
        context.setMixInAnnotations(Color::class.java, ColorMixIn::class.java)
        context.setMixInAnnotations(World::class.java, WorldMixIn::class.java)
        context.setMixInAnnotations(Location::class.java, LocationMixIn::class.java)
        context.setMixInAnnotations(FireworkEffect::class.java, FireworkEffectMixIn::class.java)
        context.setMixInAnnotations(Pattern::class.java, PatternMixIn::class.java)
        context.setMixInAnnotations(PotionEffect::class.java, PotionEffectMixIn::class.java)
        context.setMixInAnnotations(OfflinePlayer::class.java, OfflinePlayerMixIn::class.java)

        context.setMixInAnnotations(ItemStack::class.java, ItemStackMixIn::class.java)
        context.setMixInAnnotations(ItemMeta::class.java, ItemMetaMixIn::class.java)

        context.setMixInAnnotations(PotionEffectType::class.java, GetNameMixIn::class.java)
        context.setMixInAnnotations(Enchantment::class.java, GetNameMixIn::class.java)
    }

    override fun getDependencies(): List<Module> {
        return listOf(GuavaModule(), MrBeanModule())
    }
}
