package no.kh498.util.jackson.mixIn

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author Elg
 */
interface ColorMixIn {

    @get:JsonProperty("RED", index = 0)
    val red: Int

    @get:JsonProperty("GREEN", index = 1)
    val green: Int

    @get:JsonProperty("BLUE", index = 2)
    val blue: Int
}
