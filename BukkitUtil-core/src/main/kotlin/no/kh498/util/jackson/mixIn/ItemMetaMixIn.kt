package no.kh498.util.jackson.mixIn

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeInfo
import no.kh498.util.VersionUtil
import org.bukkit.enchantments.Enchantment
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
    abstract val itemFlags: Set<String>

    @JsonProperty(DISPLAY_NAME)
    abstract fun getDisplayName(): String

    @JsonProperty(REPAIR_COST)
    abstract fun getRepairCost(): Int

    //not really here used as type annotation
    @JsonProperty(TYPE_FIELD, required = true)
    abstract fun getType(): String

    //from ItemMeta.Spigot (present in 1.12+)
    @JsonProperty(UNBREAKABLE)
    abstract fun isUnbreakable(): Boolean

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
