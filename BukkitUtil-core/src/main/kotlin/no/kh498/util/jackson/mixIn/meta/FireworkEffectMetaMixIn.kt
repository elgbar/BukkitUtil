package no.kh498.util.jackson.mixIn.meta

import com.fasterxml.jackson.annotation.JsonProperty
import no.kh498.util.jackson.mixIn.ItemMetaMixIn
import org.bukkit.FireworkEffect

/**
 * @author Elg
 */
abstract class FireworkEffectMetaMixIn : ItemMetaMixIn() {
    companion object {

        const val EXPLOSION = "firework-effect"
    }

    @JsonProperty(EXPLOSION)
    lateinit var effect: FireworkEffect
}
