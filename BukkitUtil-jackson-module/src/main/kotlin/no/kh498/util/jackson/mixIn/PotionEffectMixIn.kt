package no.kh498.util.jackson.mixIn

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import org.bukkit.Color
import org.bukkit.potion.PotionEffectType

/**
 * @author Elg
 */
@JsonTypeName("PotionEffect")
abstract class PotionEffectMixIn
@JsonCreator
constructor(@JsonProperty(TYPE) type: PotionEffectType,
            @JsonProperty(DURATION) duration: Int,
            @JsonProperty(AMPLIFIER) amplifier: Int,
            @JsonProperty(AMBIENT) ambient: Boolean,
            @JsonProperty(PARTICLES) particles: Boolean,
            @JsonProperty("color") color: Color) {

    @get:JsonProperty(TYPE)
    abstract val type: PotionEffectType

    @get:JsonProperty(DURATION)
    abstract val duration: Int

    @get:JsonProperty(AMPLIFIER)
    abstract val amplifier: Int

    @get:JsonProperty(AMBIENT)
    abstract val ambient: Boolean

    //use property on getter to let jackson know it differs from
    // expected "isParticles" method
    @JsonProperty(PARTICLES)
    abstract fun hasParticles(): Boolean

    companion object {

        //these are defined in PotionEffect but are private so they are redefined here
        private const val AMPLIFIER = "amplifier"
        private const val DURATION = "duration"
        private const val TYPE = "effect"
        private const val AMBIENT = "ambient"
        private const val PARTICLES = "has-particles"
    }
}
