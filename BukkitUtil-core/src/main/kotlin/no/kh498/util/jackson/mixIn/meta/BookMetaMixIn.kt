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

//    "title" to String::class.type(),
//                "author" to String::class.type(),
//                "pages" to mapper.typeFactory.constructCollectionType(List::class.java, String::class.java)

//    @JsonIgnore
//    abstract fun getPageCount(): Int


//    @JacksonInject(TYPE_FIELD)
//    @JsonProperty(TYPE_FIELD)
//    abstract override fun getMetaType(): String
}
