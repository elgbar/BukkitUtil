package no.kh498.util.jackson.mixIn.meta

import com.fasterxml.jackson.annotation.JsonProperty
import no.kh498.util.jackson.mixIn.ItemMetaMixIn
import org.bukkit.Color
import org.bukkit.potion.PotionData
import org.bukkit.potion.PotionEffect

/**
 * @author Elg
 */
abstract class PotionMetaMixIn : ItemMetaMixIn() {
    companion object {
        const val POTION_EFFECTS = "custom-effects"
        const val POTION_COLOR = "custom-color"
        const val DEFAULT_POTION = "potion-type"
    }

    @JsonProperty(DEFAULT_POTION)
    lateinit var type: PotionData

    @JsonProperty(POTION_EFFECTS)
    lateinit var customEffects: List<PotionEffect>

    @JsonProperty(POTION_COLOR)
    lateinit var color: Color

}
