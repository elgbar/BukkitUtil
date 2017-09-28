package no.kh498.util.log;

import org.bukkit.ChatColor;

import java.util.logging.Level;

/**
 * @author kh498
 * @since 0.1.0
 */
public enum Severity {
    SEVERE(Level.SEVERE, ChatColor.RED),
    WARNING(Level.WARNING, ChatColor.YELLOW),
    INFO(Level.INFO, ChatColor.GRAY),
    CONFIG(Level.CONFIG, ChatColor.GRAY),
    // The rest is white
    FINE(Level.FINE),
    FINER(Level.FINER),
    FINEST(Level.FINEST);

    Level level;
    ChatColor color;

    Severity(final Level level, final ChatColor color) {
        this.level = level;
        this.color = color;
    }

    Severity(final Level level) {
        this.level = level;
        this.color = ChatColor.WHITE;
    }

    @Override
    public String toString() {
        return this.color + "[" + this.name() + "]";
    }
}
