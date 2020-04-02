package no.kh498.util.jackson.mixIn.meta

import com.fasterxml.jackson.annotation.JsonProperty
import no.kh498.util.jackson.mixIn.ItemMetaMixIn

/**
 * @author Elg
 */
abstract class KnowledgeBookMetaMixIn : ItemMetaMixIn() {
    companion object {
        const val BOOK_RECIPES = "Recipes"
    }

    @JsonProperty(BOOK_RECIPES)
    lateinit var recipes: List<String>
}
