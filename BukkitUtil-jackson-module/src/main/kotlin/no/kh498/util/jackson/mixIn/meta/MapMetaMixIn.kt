package no.kh498.util.jackson.mixIn.meta

import com.fasterxml.jackson.annotation.JsonProperty
import no.kh498.util.jackson.mixIn.ItemMetaMixIn
import org.bukkit.Color

/**
 * @author Elg
 */
abstract class MapMetaMixIn : ItemMetaMixIn() {

    companion object {
        const val MAP_SCALING = "scaling"
        const val MAP_LOC_NAME = "display-loc-name"
        const val MAP_COLOR = "display-map-color"
    }

    @JsonProperty(MAP_SCALING)
    var scaling: Byte = 0

    @JsonProperty(MAP_LOC_NAME)
    lateinit var locName: String

    @JsonProperty(MAP_COLOR)
    lateinit var color: Color
}
