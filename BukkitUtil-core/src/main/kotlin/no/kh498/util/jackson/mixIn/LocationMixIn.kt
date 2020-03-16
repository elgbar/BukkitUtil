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

    @get:JsonProperty("world", index = 0)
    abstract val world: World

    @get:JsonProperty("x", index = 1)
    abstract val x: Double

    @get:JsonProperty("y", index = 2)
    abstract val y: Double

    @get:JsonProperty("z", index = 3)
    abstract val z: Double

    @get:JsonProperty("yaw", index = 4)
    abstract val yaw: Float

    @get:JsonProperty("pitch", index = 5)
    abstract val pitch: Float

    //Ignored properties

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
