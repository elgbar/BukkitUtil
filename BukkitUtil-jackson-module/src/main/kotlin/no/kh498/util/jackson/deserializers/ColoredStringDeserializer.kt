package no.kh498.util.jackson.deserializers

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StringDeserializer
import no.kh498.util.ChatUtil
import org.bukkit.ChatColor

/**
 * A deserializer of strings that translate the commonly used color code prefix `&` with the correct one `§`. (see [ChatUtil.toBukkitColor] and [ChatColor.translateAlternateColorCodes])
 *
 * This will also make tab character `\t` appear as four spaces fix all newline issues (see [ChatUtil.sanitizeSpecialChars])
 *
 * To use this deserializer with jackson add `@JsonDeserialize(using = ColoredStringDeserializer.class)` above the wanted string variable
 *
 * @see ChatUtil.toBukkitColor
 * @see ChatUtil.sanitizeSpecialChars
 * @see ChatColor.translateAlternateColorCodes
 * @author Elg
 */
object ColoredStringDeserializer : StringDeserializer() {

    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): String? =
            ChatUtil.toBukkitColor(super.deserialize(p, ctxt))
}
