package no.kh498.util.jackson.mixIn

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSetter

/**
 * @author Elg
 */
interface VectorMixIn {

  @get:JsonProperty(index = 0)
  val x: Double

  @get:JsonProperty(index = 1)
  val y: Double

  @get:JsonProperty(index = 2)
  val z: Double

  @get:JsonIgnore
  val blockX: Double

  @get:JsonIgnore
  val blockY: Double

  @get:JsonIgnore
  val blockZ: Double

  @JsonSetter
  fun setX(x: Double)

  @JsonSetter
  fun setY(y: Double)

  @JsonSetter
  fun setZ(z: Double)
}
