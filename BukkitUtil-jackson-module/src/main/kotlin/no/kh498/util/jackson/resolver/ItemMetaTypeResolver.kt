package no.kh498.util.jackson.resolver

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.DatabindContext
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase
import no.kh498.util.jackson.mixIn.ItemMetaMixIn
import no.kh498.util.jackson.type
import org.bukkit.inventory.meta.ItemMeta

/**
 * @author Elg
 */
class ItemMetaTypeResolver : TypeIdResolverBase() {

    private fun idFromClass(clazz: Class<out ItemMeta>): String {
        return ItemMetaMixIn.classMap[clazz] ?: error("Unknown ItemMeta class $clazz")
    }

    override fun idFromValue(value: Any): String {
        require(value is ItemMeta) { "Given value is not an instance of ItemMeta: got ${value.javaClass.name}" }
        return idFromClass(value.javaClass)
    }

    override fun idFromValueAndType(value: Any?, suggestedType: Class<*>?): String {
        if (value != null) {
            return idFromValue(value)
        } else {
            requireNotNull(suggestedType) { "Both value and suggested type cannot be null" }
            require(ItemMeta::class.java.isAssignableFrom(suggestedType)) {
                "Given suggested class is not an instance of ItemMeta: got ${suggestedType.name}"
            }
            @Suppress("UNCHECKED_CAST")
            return idFromClass(suggestedType as Class<out ItemMeta>)
        }
    }

    override fun typeFromId(context: DatabindContext, id: String): JavaType {
        val clazz = ItemMetaMixIn.classMap.toList().first { it.second == id }.first
        return clazz.type(context.typeFactory)
    }

    override fun getMechanism(): JsonTypeInfo.Id {
        return JsonTypeInfo.Id.CUSTOM
    }


}
