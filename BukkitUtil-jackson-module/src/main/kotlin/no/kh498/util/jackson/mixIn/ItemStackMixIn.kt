package no.kh498.util.jackson.mixIn

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.material.MaterialData

/**
 * @author Elg
 */
abstract class ItemStackMixIn {

    @JsonProperty(TYPE, index = 0)
    abstract fun getType(): Material

    @JsonProperty(AMOUNT, index = 1, defaultValue = "1")
    abstract fun getAmount(): Int

    @JsonProperty(DAMAGE, index = 2)
    abstract fun getDurability(): Short

    //For some bizarre reason we must have the property directly on the field
    // for the meta to changed
    @JsonProperty(META)
    private lateinit var meta: ItemMeta

    //So we must ignore this getter to not get duplicate meta with different keys
    @JsonIgnore
    abstract fun getItemMeta(): ItemMeta

    @JsonIgnore
    abstract fun getData(): MaterialData

    @JsonIgnore
    abstract fun getTypeId(): Int

    @JsonIgnore
    abstract fun getMaxStackSize(): String

    @JsonIgnore
    abstract fun getEnchantments(): MutableMap<Enchantment?, Int?>

    companion object {
        const val TYPE: String = "type"
        const val DAMAGE: String = "damage"
        const val AMOUNT: String = "amount"
        const val META: String = "meta"
    }
}
