package no.kh498.util.jackson.mixIn

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.common.collect.ImmutableList
import org.bukkit.Color
import org.bukkit.FireworkEffect

/**
 * @author Elg
 */
abstract class FireworkEffectMixIn
@JsonCreator
constructor(@JsonProperty(FLICKER) flicker: Boolean,
            @JsonProperty(TRAIL) trail: Boolean,
            @JsonProperty(COLORS) colors: ImmutableList<Color>,
            @JsonProperty(FADE_COLORS) fadeColors: ImmutableList<Color>,
            @JsonProperty(TYPE) type: FireworkEffect.Type) {

    @JsonProperty(FLICKER)
    abstract fun hasFlicker(): Boolean

    @JsonProperty(TRAIL)
    abstract fun hasTrail(): Boolean

    companion object {
        private const val FLICKER = "flicker"
        private const val TRAIL = "trail"
        private const val COLORS = "colors"
        private const val FADE_COLORS = "fade-colors"
        private const val TYPE = "type"
    }
}
