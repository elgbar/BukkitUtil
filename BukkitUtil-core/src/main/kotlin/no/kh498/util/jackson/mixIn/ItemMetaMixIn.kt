package no.kh498.util.jackson.mixIn

import com.fasterxml.jackson.annotation.*
import no.kh498.util.VersionUtil
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.meta.ItemMeta

/**
 * @author Elg
 */
//type info is handled by serializer and deserializers
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
abstract class ItemMetaMixIn
@JsonCreator
constructor(@JsonIgnore open val map: Map<String, Any?>? = null) {

    @get:JsonProperty(ENCHANTMENT)
    abstract var enchants: Map<Enchantment, Int>

    @get:JsonProperty(FLAGS)
    abstract var itemFlags: Set<ItemFlag>

    @JsonProperty(DISPLAY_NAME)
    abstract fun getDisplayName(): String

    @JsonProperty(REPAIR_COST)
    abstract fun getRepairCost(): Int

    //not really here used as type annotation
    @JsonPropertyDescription("Different subtypes of ItemMeta require different values to let bukkit know the type of meta to deserialize as. For each new version of the game specific meta class might be added or removed so only use items that are available to the minimum version you're targeting.\n" +
            "\n" +
            "MC 1.8+\n" +
            "CraftMetaBanner.class -> \"BANNER\"\n" +
            "CraftMetaBlockState.class -> \"TILE_ENTITY\n" +
            "CraftMetaBook.class -> \"BOOK\"\n" +
            "CraftMetaBookSigned.class -> \"BOOK_SIGNED\"\n" +
            "CraftMetaSkull.class -> \"SKULL\"\n" +
            "CraftMetaLeatherArmor.class -> \"LEATHER_ARMOR\"\n" +
            "CraftMetaMap.class -> \"MAP\"\n" +
            "CraftMetaPotion.class -> \"POTION\"\n" +
            "CraftMetaEnchantedBook.class -> \"ENCHANTED\"\n" +
            "CraftMetaFirework.class -> \"FIREWORK\"\n" +
            "CraftMetaCharge.class -> \"FIREWORK_EFFECT\"\n" +
            "CraftMetaItem.class -> \"UNSPECIFIC\"\n" +
            "\n" +
            "MC 1.12+\n" +
            "\n" +
            "CraftMetaSpawnEgg.class -> \"SPAWN_EGG\"\n" +
            "CraftMetaKnowledgeBook.class -> \"KNOWLEDGE_BOOK\"\n" +
            "\n" +
            "mc 1.15 (latest as of writing)\n" +
            "\n" +
            "CraftMetaArmorStand.class -> \"ARMOR_STAND\"\n" +
            "CraftMetaTropicalFishBucket.class -> \"TROPICAL_FISH_BUCKET\"\n" +
            "CraftMetaCrossbow.class -> \"CROSSBOW\"\n" +
            "CraftMetaSuspiciousStew.class -> \"SUSPICIOUS_STEW\"\n" +
            "")
    @JsonProperty(TYPE_FIELD, defaultValue = "\"UNSPECIFIC\"")
    abstract fun getMetaType(): String

    @JsonProperty(UNBREAKABLE)
    abstract fun isUnbreakable(): Boolean

    @JsonProperty(LORE)
    abstract fun getLore(): List<String>

    @JsonIgnore
    abstract fun getLocalizedName(): String

    companion object {
        val classMap: Map<Class<out ItemMeta>, String>
        const val TYPE_FIELD = "meta-type"
        const val ENCHANTMENT = "enchants"
        const val FLAGS = "ItemFlags"
        const val REPAIR_COST = "repair-cost"
        const val DISPLAY_NAME = "display-name"
        const val UNBREAKABLE = "Unbreakable"
        const val LORE = "lore"

        init {

            val serializableMetaClass: Class<*>
            val className = "inventory.CraftMetaItem\$SerializableMeta"
            serializableMetaClass = try {
                VersionUtil.getCBClass(className)
            } catch (e: ClassNotFoundException) {
                //FIXME this is too version dependent for my liking...
                Class.forName(VersionUtil.CB_PACKAGE + "." + VersionUtil.v1_12_R1 + "." + className)
            }

            val field = serializableMetaClass.getDeclaredField("classMap")
            field.isAccessible = true
            @Suppress("UNCHECKED_CAST")
            classMap = field.get(null) as Map<Class<out ItemMeta>, String>
        }
    }
}
