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
interface ItemStackMixIn {

    @JsonProperty(TYPE, index = 0)
    fun getType(): Material

    @JsonProperty(AMOUNT, index = 1, defaultValue = "1")
    fun getAmount(): Int

    @JsonProperty(DAMAGE, index = 2)
    fun getDurability(): Short

    @JsonProperty(META, index = 3)
    fun getItemMeta(): ItemMeta

    @JsonIgnore
    fun getData(): MaterialData

    @JsonIgnore
    fun getTypeId(): Int

    @JsonIgnore
    fun getMaxStackSize(): Int

    @JsonIgnore
    fun getEnchantments(): MutableMap<Enchantment?, Int?>

    companion object {
        const val TYPE: String = "type"
        const val DAMAGE: String = "damage"
        const val AMOUNT: String = "amount"
        const val META: String = "meta"
    }
}
