package no.kh498.util.jackson.mixIn

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.util.Vector

/**
 * @author Elg
 */
abstract class LocationMixIn
@JsonCreator
constructor(@JsonProperty("world") world: World,
            @JsonProperty("x") x: Double,
            @JsonProperty("y") y: Double,
            @JsonProperty("z") z: Double,
            @JsonProperty("yaw") yaw: Float,
            @JsonProperty("pitch") pitch: Float) {

    @get:JsonIgnore
    abstract val direction: Vector

    @get:JsonIgnore
    abstract val block: Block

    @get:JsonIgnore
    abstract val chunk: Chunk

    @get:JsonIgnore
    abstract val blockX: Int

    @get:JsonIgnore
    abstract val blockY: Int

    @get:JsonIgnore
    abstract val blockZ: Int

}
