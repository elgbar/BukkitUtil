package no.kh498.util.jackson.mixIn

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.annotation.JsonValue
import java.util.*

/**
 * @author Elg
 */
//OK to use mr bean interface style as we get the real values from Bukkit instance
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
interface OfflinePlayerMixIn {

    @JsonValue
    fun getUniqueId(): UUID
}
