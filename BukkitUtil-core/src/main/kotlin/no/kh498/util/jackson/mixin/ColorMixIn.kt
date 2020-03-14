package no.kh498.util.jackson.mixin

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author Elg
 */
interface ColorMixIn {
    @get:JsonProperty("RED")
    val red: Int

    @get:JsonProperty("GREEN")
    val green: Int

    @get:JsonProperty("BLUE")
    val blue: Int
}
