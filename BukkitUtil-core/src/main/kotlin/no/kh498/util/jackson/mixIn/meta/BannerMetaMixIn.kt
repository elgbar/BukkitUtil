package no.kh498.util.jackson.mixIn.meta

import com.fasterxml.jackson.annotation.JsonProperty
import no.kh498.util.jackson.mixIn.ItemMetaMixIn
import org.bukkit.DyeColor
import org.bukkit.block.banner.Pattern

/**
 * @author Elg
 */
abstract class BannerMetaMixIn : ItemMetaMixIn() {

    @JsonProperty(BASE)
    lateinit var base: DyeColor

    @JsonProperty(PATTERNS)
    lateinit var patterns: List<Pattern>

    companion object {
        const val BASE = "base-color"
        const val PATTERNS = "patterns"
    }
}
