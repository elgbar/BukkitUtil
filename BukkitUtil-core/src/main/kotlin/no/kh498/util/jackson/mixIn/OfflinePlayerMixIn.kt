package no.kh498.util.jackson.mixIn

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.annotation.JsonValue
import java.util.*

/**
 * @author Elg
 */
interface OfflinePlayerMixIn {

    @JsonValue
    fun getUniqueId(): UUID
}
