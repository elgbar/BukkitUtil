package no.kh498.util.jackson.mixIn

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonValue
import java.util.*

/**
 * @author Elg
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
interface WorldMixIn {

    @JsonValue
    fun getUID(): UUID

}
