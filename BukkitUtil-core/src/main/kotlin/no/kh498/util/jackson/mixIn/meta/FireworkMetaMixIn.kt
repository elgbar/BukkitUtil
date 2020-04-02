package no.kh498.util.jackson.mixIn.meta

import com.fasterxml.jackson.annotation.JsonProperty
import no.kh498.util.jackson.mixIn.ItemMetaMixIn
import org.bukkit.FireworkEffect

/**
 * @author Elg
 */
abstract class FireworkMetaMixIn : ItemMetaMixIn() {
    companion object {
        const val FLIGHT = "power"
        const val EXPLOSIONS = "firework-effects"
    }

    @JsonProperty(EXPLOSIONS)
    lateinit var effects: List<FireworkEffect>

    @JsonProperty(FLIGHT)
    var power: Int = 0
}
