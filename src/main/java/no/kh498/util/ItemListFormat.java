package no.kh498.util;

import org.bukkit.ChatColor;

/**
 * @author karl henrik
 */
public class ItemListFormat {

    /**
     * Create a list of string that is easier to ready than a mono colored text. Each object is colored by the secondary
     * color while the rest is colored by the primary color
     * <p>
     * Example:
     * <p>
     * {@code colorString(ChatColor.RED, ChatColor.GRAY, "test1","test2")}
     * <p>
     * returns
     * <p>
     * {@code ChatColor.GRAY + "test1" + ChatColor.RED + ", " + ChatColor.GRAY + "test1"}
     *
     * @param pri
     *     The primary color
     * @param sec
     *     The secondary color
     * @param args
     *     The objects to insert into the list
     *
     * @return A formatted string
     */
    public static String colorString(final ChatColor pri, final ChatColor sec, final Object[] args) {
        return colorString(pri, sec, "", args);
    }

    /**
     * Create a list of string that is easier to ready than a mono colored text. Each object is colored by the secondary
     * color while the rest is colored by the primary color
     * <p>
     * Example:
     * <p>
     * {@code colorString(ChatColor.RED, ChatColor.GRAY, "Test list: ", "test1","test2")}
     * <p>
     * returns
     * <p>
     * {@code ChatColor.RED + "Test list: " + ChatColor.GRAY + "test1" + ChatColor.RED + ", " + ChatColor.GRAY +
     * "test1"}
     *
     * @param pri
     *     The primary color
     * @param sec
     *     The secondary color
     * @param prefix
     *     The prefix of the list
     * @param args
     *     The objects to insert into the list
     *
     * @return A formatted string
     */
    public static String colorString(final ChatColor pri, final ChatColor sec, final String prefix,
                                     final Object[] args) {
        return colorString(pri, sec, prefix, ", ", args);
    }

    /**
     * Create a list of string that is easier to ready than a mono colored text. Each object is colored by the secondary
     * color while the rest is colored by the primary color
     * <p>
     * Example:
     * <p>
     * {@code colorString(ChatColor.RED, ChatColor.GRAY, "Test list: ", " | ", "test1","test2")}
     * <p>
     * returns
     * <p>
     * {@code ChatColor.RED + "Test list: " + ChatColor.GRAY + "test1" + ChatColor.RED + " | " + ChatColor.GRAY +
     * "test1"}
     *
     * @param pri
     *     The primary color
     * @param sec
     *     The secondary color
     * @param prefix
     *     The prefix of the list
     * @param divider
     *     What divides the different objects
     * @param args
     *     The objects to insert into the list
     *
     * @return A formatted string
     */
    public static String colorString(final ChatColor pri, final ChatColor sec, final String prefix,
                                     final String divider, final Object[] args) {
        final StringBuilder sb = new StringBuilder(pri + prefix);
        final String formattedDiv = pri + divider;

        for (final Object obj : args) {
            sb.append(sec).append(obj.toString()).append(formattedDiv);
        }
        sb.setLength(sb.length() - formattedDiv.length());
        return sb.toString();
    }
}
