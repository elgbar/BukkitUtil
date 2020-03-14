package no.kh498.util.jackson.mixin

import com.fasterxml.jackson.annotation.JsonIgnore

/**
 * @author Elg
 */
interface VectorMixIn {
    @get:JsonIgnore
    val blockX: Int

    @get:JsonIgnore
    val blockY: Int

    @get:JsonIgnore
    val blockZ: Int
}
