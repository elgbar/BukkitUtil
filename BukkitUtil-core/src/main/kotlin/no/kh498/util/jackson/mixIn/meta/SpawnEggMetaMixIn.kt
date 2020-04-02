package no.kh498.util.jackson.mixIn.meta

import com.fasterxml.jackson.annotation.JsonProperty
import no.kh498.util.jackson.mixIn.ItemMetaMixIn

/**
 * @author Elg
 */
abstract class SpawnEggMetaMixIn : ItemMetaMixIn() {
    companion object {
        const val ENTITY_ID = "id"
    }

    @JsonProperty(ENTITY_ID)
    lateinit var id: String
}
