package no.kh498.util.jackson.mixIn

import com.fasterxml.jackson.annotation.*
import no.kh498.util.VersionUtil
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.meta.ItemMeta

/**
 * @author Elg
 */
//type info is handled by serializer and deserializers
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NON_PRIVATE, creatorVisibility = JsonAutoDetect.Visibility.NON_PRIVATE)
abstract class ItemMetaMixIn
@JsonCreator
constructor(@JsonIgnore open val map: Map<String, Any?>? = null) {

    @get:JsonProperty("repair-cost")
    abstract var repairCost: Int

    @get:JsonProperty("enchants")
    abstract var enchants: Map<Enchantment, Int>

    @JsonProperty("display-name")
    abstract fun getDisplayName(): String


    companion object {
        val classMap: Map<Class<out ItemMeta>, String>
        const val TYPE_FIELD = "meta-type"

        init {
            val serializableMetaClass = VersionUtil.getCBClass("inventory.CraftMetaItem\$SerializableMeta")
            val field = serializableMetaClass.getDeclaredField("classMap")
            field.isAccessible = true
            @Suppress("UNCHECKED_CAST")
            classMap = field.get(null) as Map<Class<out ItemMeta>, String>
        }
    }
}
