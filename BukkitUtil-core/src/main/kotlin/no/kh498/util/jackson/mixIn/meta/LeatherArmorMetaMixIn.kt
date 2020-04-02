package no.kh498.util.jackson.mixIn.meta

import com.fasterxml.jackson.annotation.JsonProperty
import no.kh498.util.jackson.mixIn.ItemMetaMixIn
import org.bukkit.Color

/**
 * @author Elg
 */
abstract class LeatherArmorMetaMixIn : ItemMetaMixIn() {

    @JsonProperty(COLOR, defaultValue = "{\"==\":\"Color\",\"RED\":160,\"GREEN\":101,\"BLUE\":64}")
    abstract fun getColor(): Color

    companion object {
        const val COLOR = "color"
    }
}
