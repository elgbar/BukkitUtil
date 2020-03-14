package no.kh498.util.jackson.deserializers.key

import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.KeyDeserializer
import org.bukkit.enchantments.Enchantment

/**
 * @author Elg
 */
object EnchantmentKeyDeserializer : KeyDeserializer() {

    override fun deserializeKey(key: String, ctxt: DeserializationContext): Any {
        val id: Int
        id = try {
            key.toInt()
        } catch (e: NumberFormatException) {
            throw IllegalStateException("Expected to find an integer but found $key")
        }
        return Enchantment.getById(id)
    }
}
