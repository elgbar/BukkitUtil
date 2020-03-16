package no.kh498.util.jackson.mixIn

import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.bukkit.util.BlockVector

/**
 * @author Elg
 */
@JsonIgnoreProperties("blockX", "blockY", "blockZ", "x", "y", "z")
abstract class BlockVectorMixIn {


    @JsonAnySetter
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    abstract fun deserialize(args: Map<String, Any>): BlockVector

}
