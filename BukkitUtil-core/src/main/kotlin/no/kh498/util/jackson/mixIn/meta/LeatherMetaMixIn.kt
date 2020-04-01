package no.kh498.util.jackson.mixIn.meta

import com.fasterxml.jackson.annotation.JsonProperty
import no.kh498.util.jackson.mixIn.ItemMetaMixIn
import org.bukkit.Color

/**
 * @author Elg
 */
abstract class LeatherMetaMixIn : ItemMetaMixIn() {

    @JsonProperty("color")
    abstract fun getColor(): Color?
}
