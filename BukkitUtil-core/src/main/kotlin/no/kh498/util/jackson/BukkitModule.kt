package no.kh498.util.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.Version
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.BeanSerializer
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier
import com.fasterxml.jackson.databind.ser.Serializers
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.fasterxml.jackson.datatype.guava.GuavaModule
import no.kh498.util.ConfigUtil
import no.kh498.util.jackson.deserializers.BukkitDeserializers
import no.kh498.util.jackson.deserializers.BukkitKeyDeserializers
import no.kh498.util.jackson.mixIn.*
import no.kh498.util.jackson.serializers.bean.ItemMetaSerializer
import org.bukkit.*
import org.bukkit.block.banner.Pattern
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.craftbukkit.v1_x_Ry.JacksonMockServer
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.util.Vector


/**
 * @author Elg
 */
class BukkitModule(
        /**
         * If [String]s will be serialized with [no.kh498.util.jackson.deserializers.ColoredStringDeserializer] by default.
         *
         * @see no.kh498.util.jackson.deserializers.ColoredStringDeserializer
         */
        val colorizeStringsByDefault: Boolean = true) : SimpleModule(Version(1, 0, 0, null, "no.kh498.util", "BukkitUtil")) {

    private val createServer = Bukkit.getServer() == null

    init {
        setMixInAnnotation(ConfigurationSerializable::class.java, ConfigurationSerializableMixIn::class.java)
        setMixInAnnotation(Vector::class.java, VectorMixIn::class.java)
        setMixInAnnotation(Color::class.java, ColorMixIn::class.java)
        setMixInAnnotation(World::class.java, WorldMixIn::class.java)
        setMixInAnnotation(Location::class.java, LocationMixIn::class.java)
        setMixInAnnotation(FireworkEffect::class.java, FireworkEffectMixIn::class.java)
        setMixInAnnotation(Pattern::class.java, PatternMixIn::class.java)
        setMixInAnnotation(PotionEffect::class.java, PotionEffectMixIn::class.java)
        setMixInAnnotation(OfflinePlayer::class.java, OfflinePlayerMixIn::class.java)

        setMixInAnnotation(ItemStack::class.java, ItemStackMixIn::class.java)
        setMixInAnnotation(ItemMeta::class.java, ItemMetaMixIn::class.java)

        setMixInAnnotation(PotionEffectType::class.java, GetNameMixIn::class.java)
        setMixInAnnotation(Enchantment::class.java, GetNameMixIn::class.java)

    }

    override fun setupModule(context: SetupContext) {
        context.addDeserializers(BukkitDeserializers(this))
        context.addKeyDeserializers(BukkitKeyDeserializers)

        context.addSerializers(object : Serializers.Base() {
            override fun findSerializer(config: SerializationConfig, type: JavaType, beanDesc: BeanDescription): JsonSerializer<*>? {
                return when {
                    ConfigurationSection::class.java.isAssignableFrom(type.rawClass) -> {
                        object : StdSerializer<ConfigurationSection>(ConfigurationSection::class.java) {
                            override fun serialize(value: ConfigurationSection, gen: JsonGenerator, provider: SerializerProvider) {
                                gen.writeString(ConfigUtil.saveToString(value))
                            }
                        }
                    }
                    else -> null
                }
            }
        })

        setSerializerModifier(object : BeanSerializerModifier() {
            override fun modifySerializer(config: SerializationConfig, beanDesc: BeanDescription, serializer: JsonSerializer<*>): JsonSerializer<*> {
                if (ItemMeta::class.java.isAssignableFrom(beanDesc.beanClass)) {
                    @Suppress("UNCHECKED_CAST")
                    return ItemMetaSerializer(serializer as BeanSerializer)
                }
                return serializer
            }
        })

        if (createServer) {
            JacksonMockServer
        }

        super.setupModule(context)
    }

    override fun getDependencies(): List<Module> {
        val deps = ArrayList<Module>()
        deps.add(GuavaModule())
        return deps
    }
}
