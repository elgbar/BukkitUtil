package no.kh498.util.jackson.mixIn.meta

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import no.kh498.util.jackson.mixIn.ItemMetaMixIn
import org.bukkit.inventory.meta.BookMeta

/**
 * @author Elg
 */
abstract class BookMetaMixIn : ItemMetaMixIn() {

    @JsonIgnore
    abstract fun getPageCount(): Int

    @JsonProperty
    abstract fun getGeneration(): BookMeta.Generation

    @JsonProperty
    abstract fun getTitle(): String

    @JsonProperty
    abstract fun getAuthor(): String

    @JsonProperty
    abstract fun getPages(): List<String>
}
