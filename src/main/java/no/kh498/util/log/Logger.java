package no.kh498.util.log;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

/**
 * @author kh498
 * @since 0.1.0
 */
@SuppressWarnings("unused")
public class Logger {

    //let the variables be changed from everywhere
    public static Level logLevel = Level.INFO;
    public static String adminPerm;

    private final static String DEFAULT_LOG_PREFIX = "[ ] ";

    private static String LOG_PREFIX = DEFAULT_LOG_PREFIX;


    public static void setPlugin(final Plugin plugin) {
        LOG_PREFIX = "[" + plugin.getName() + "] ";
    }

    public static void setPluginIfNotSet(final Plugin plugin) {
        if (!hasInitiatedLogger()) {
            setPlugin(plugin);
        }
    }

    /**
     * @return If the Logger has been initiated by a plugin
     */
    public static boolean hasInitiatedLogger() {
        return !DEFAULT_LOG_PREFIX.equals(LOG_PREFIX);
    }

    /**
     * Handle error logging for you, a colorized message will be dispatched to the console and another message to the
     * player if not null
     *
     * @param level
     *     Level of the error
     * @param msg
     *     The message to display
     * @param player
     *     The player to send the error to
     */
    public static void log(final Severity level, final Object msg, final Player player) {

        //filter the messages
        if (logLevel.intValue() > level.level.intValue()) {
            return;
        }
        final String error = colorError(level, msg.toString());

        Bukkit.getServer().getConsoleSender().sendMessage(LOG_PREFIX + error);

        if (player != null && player.isOnline()) {
            player.sendMessage(error);
        }

        /* Send errors directly to online admins */
        if (adminPerm != null && (level == Severity.SEVERE || level == Severity.WARNING)) {
            for (final Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (!onlinePlayer.equals(player) && onlinePlayer.hasPermission(adminPerm)) {
                    onlinePlayer.sendMessage(error);
                }
            }
        }
    }

    /**
     * Handle error logging for you, a colorized message will be dispatched to the console
     *
     * @param level
     *     Error level of the error
     * @param player
     *     Player to send message to
     * @param msg
     *     The message to display
     * @param args
     *     Arguments to be replaced using {@code String.format(msg, args)}
     */
    public static void log(final Severity level, final Player player, final String msg, final Object... args) {
        log(level, String.format(msg, args), player);
    }

    /**
     * Handle error logging for you, a colorized message will be dispatched to the console
     *
     * @param level
     *     Error level of the error
     * @param msg
     *     The message to display
     */
    public static void log(final Severity level, final Object msg) {
        log(level, msg, (Player) null);
    }

    /**
     * Handle error logging for you, a colorized message will be dispatched to the console
     *
     * @param level
     *     Error level of the error
     * @param msg
     *     The message to display
     * @param args
     *     Arguments to be replaced using {@code String.format(msg, args)}
     */
    public static void log(final Severity level, final String msg, final Object... args) {
        log(level, String.format(msg, args), (Player) null);
    }

    /**
     * Log an message with {@code Severity.INFO}
     *
     * @param msg
     *     The message to display
     */
    public static void info(final Object msg) {
        log(Severity.INFO, msg);
    }

    /**
     * Log an message with {@code Severity.INFO}
     *
     * @param msg
     *     The message to display
     * @param args
     *     Arguments to be replaced using {@code String.format(msg, args)}
     */
    public static void info(final String msg, final Object... args) {
        log(Severity.INFO, msg, args);
    }

    /**
     * Log an message with {@code Severity.WARNING}
     *
     * @param msg
     *     The message to display
     */
    public static void warn(final Object msg) {
        log(Severity.WARNING, msg);
    }

    /**
     * Log an message with {@code Severity.WARNING}
     *
     * @param msg
     *     The message to display
     * @param args
     *     Arguments to be replaced using {@code String.format(msg, args)}
     */
    public static void warn(final String msg, final Object... args) {
        log(Severity.WARNING, msg, args);
    }

    /**
     * Log an message with {@code Severity.SEVERE}
     *
     * @param msg
     *     The message to display
     */
    public static void severe(final Object msg) {
        log(Severity.SEVERE, msg);
    }

    /**
     * Log an message with {@code Severity.SEVERE}
     *
     * @param msg
     *     The message to display
     * @param args
     *     Arguments to be replaced using {@code String.format(msg, args)}
     */
    public static void severe(final String msg, final Object... args) {
        log(Severity.SEVERE, msg, args);
    }

    /**
     * Get a nice error message
     *
     * @param level
     *     Level of the error
     * @param msg
     *     The message to display
     *
     * @return A colorized error message (includes {@code [{SEVERITY}]})
     */
    private static String colorError(final Severity level, final String msg) {
        return level.toString() + " " + msg;
    }

}


