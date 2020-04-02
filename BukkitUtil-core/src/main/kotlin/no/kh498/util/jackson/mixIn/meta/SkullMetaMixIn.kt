package no.kh498.util.jackson.mixIn.meta

import com.fasterxml.jackson.annotation.JsonProperty
import no.kh498.util.jackson.mixIn.ItemMetaMixIn

/**
 * @author Elg
 */
abstract class SkullMetaMixIn : ItemMetaMixIn() {

    companion object {
        const val SKULL_OWNER = "skull-owner"
    }

    @JsonProperty(SKULL_OWNER)
    lateinit var owner: String
}
