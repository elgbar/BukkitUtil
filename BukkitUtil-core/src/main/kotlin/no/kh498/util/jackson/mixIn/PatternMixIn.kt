package no.kh498.util.jackson.mixIn

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.bukkit.DyeColor
import org.bukkit.block.banner.PatternType

/**
 * @author Elg
 */
abstract class PatternMixIn
@JsonCreator
constructor(@JsonProperty("color") color: DyeColor,
            @JsonProperty("pattern") pattern: PatternType) {
}
