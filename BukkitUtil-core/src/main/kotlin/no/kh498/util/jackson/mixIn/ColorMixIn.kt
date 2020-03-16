package no.kh498.util.jackson.mixIn

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.bukkit.Color

/**
 * @author Elg
 */
abstract class ColorMixIn
@JsonCreator
constructor(@JsonProperty("RED") red: Int,
            @JsonProperty("GREEN") green: Int,
            @JsonProperty("BLUE") blue: Int) {

    @get:JsonProperty("RED", index = 0)
    abstract val red: Int

    @get:JsonProperty("GREEN", index = 1)
    abstract val green: Int

    @get:JsonProperty("BLUE", index = 2)
    abstract val blue: Int

}
