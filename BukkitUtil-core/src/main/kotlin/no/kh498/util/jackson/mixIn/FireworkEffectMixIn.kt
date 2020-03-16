package no.kh498.util.jackson.mixIn

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import com.google.common.collect.ImmutableList
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.World

/**
 * @author Elg
 */
abstract class FireworkEffectMixIn
@JsonCreator
constructor(@JsonProperty("flicker") flicker: Boolean,
            @JsonProperty("trail") trail: Boolean,
            @JsonProperty("colors") colors: ImmutableList<Color>,
            @JsonProperty("fadeColors") fadeColors: ImmutableList<Color>,
            @JsonProperty("type") type: FireworkEffect.Type) {

    @JsonProperty("flicker")
    abstract fun hasFlicker(): Boolean

    @JsonProperty("trail")
    abstract fun hasTrail(): Boolean
}
