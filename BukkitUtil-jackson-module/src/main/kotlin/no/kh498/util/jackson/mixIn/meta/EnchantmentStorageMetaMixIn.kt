package no.kh498.util.jackson.mixIn.meta

import com.fasterxml.jackson.annotation.JsonProperty
import no.kh498.util.jackson.mixIn.ItemMetaMixIn
import org.bukkit.enchantments.Enchantment

/**
 * @author Elg
 */
abstract class EnchantmentStorageMetaMixIn : ItemMetaMixIn() {
    companion object {
        const val STORED_ENCHANTMENTS = "stored-enchants"
    }

    @JsonProperty(STORED_ENCHANTMENTS)
    lateinit var enchantments: Map<Enchantment, Int>
}
