package no.kh498.util;

import info.ronjenkins.slf4bukkit.ColorString;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * @author Elg
 */
@SuppressWarnings("WeakerAccess")
public class ChtUtil {

    public static void sendFormattedMsg(CommandSender sender, ColorString msg, final Object... obj) {
        sendFormattedMsg(sender, msg.toString(), obj);
    }

    /**
     * @param sender
     *     The receiver of the message
     * @param msg
     *     The message to format, supports '&amp;' as chat color prefix
     * @param obj
     *     The objects to replace
     */
    public static void sendFormattedMsg(final CommandSender sender, final String msg, final Object... obj) {
        sender.sendMessage(createFormattedMsg(msg, obj).split("\n"));
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
        if (msg == null) { return null; }
        return toBukkitColor(String.format(msg, obj));
    }

    /**
     * @param str
     *     The String to colorify
     *
     * @return Add color to string that is using the color code '&amp;'
     */
    public static String toBukkitColor(final String str) {
        if (str == null) { return null; }
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
        if (str == null) { return null; }
        return str.replace("\n\r", "\n").replace('\r', '\n').replace("\t", "    ");
    }
}
