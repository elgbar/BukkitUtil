package no.kh498.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * @author karl henrik
 */
@SuppressWarnings("WeakerAccess")
public class ChtUtil {

    /**
     * @param sender
     *     The receiver of the message
     * @param msg
     *     The message to format, supports '&amp;' as chat color prefix
     * @param obj
     *     The objects to replace
     */
    public static void sendFormattedMsg(final CommandSender sender, final String msg, final Object... obj) {
        sender.sendMessage(createFormattedMsg(msg, obj));
    }

    /**
     * @param msg
     *     The message to format
     * @param obj
     *     The objects to replace
     *
     * @return A message that can use '&amp;' as bukkit color and {@link String#format(String, Object...)}
     */
    public static String createFormattedMsg(final String msg, final Object... obj) {
        return toBukkitColor(String.format(msg, obj));
    }

    /**
     * @param str
     *     The String to colorify
     *
     * @return Add color to string that is using the color code '&amp;'
     */
    public static String toBukkitColor(final String str) {
        return ChatColor.translateAlternateColorCodes('&', sanitizeSpecialChars(str));
    }

    /**
     * Minecraft only accept '\n' as a new line. And also not \t
     *
     * @param str
     *     The string to sanitize
     *
     * @return convert windows and mac os new line to linux newline, and replace tab char with four spaces
     */
    public static String sanitizeSpecialChars(final String str) {
        return str.replace("\n\r", "\n").replace('\r', '\n').replace("\t", "    ");
    }
}
