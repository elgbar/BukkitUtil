package no.kh498.util.jackson.mixIn

import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSetter
import org.bukkit.inventory.meta.ItemMeta.Spigot

/**
 * @author Elg
 */
abstract class ItemMetaSpigotMixIn : Spigot() {

    companion object {
        const val UNBREAKABLE = "Unbreakable"
    }

    @JsonGetter(UNBREAKABLE)
    override fun isUnbreakable(): Boolean {
        return super.isUnbreakable()
    }

}
