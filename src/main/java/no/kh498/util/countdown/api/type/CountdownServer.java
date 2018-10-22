package no.kh498.util.countdown.api.type;

import no.kh498.util.countdown.api.Countdown;
import no.kh498.util.countdown.api.timeFormat.TimeFormat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collection;

/**
 * Let everyone on a server see the countdown
 * <p>
 * {@inheritDoc}
 *
 * @author Elg
 */
public class CountdownServer extends Countdown {

    /**
     * @param plugin
     *     The plugin that is using the countdown
     * @param text
     *     The text to display in the action bar. Must contain a {@code %s} where the time left is inserted
     * @param time
     *     How long the countdown should be in milliseconds
     * @param timeFormat
     *     The way time is displayed
     */
    public CountdownServer(final Plugin plugin, final String text, final long time, final TimeFormat timeFormat) {
        super(plugin, text, time, timeFormat);
    }

    @Override
    public Collection<? extends Player> getPlayers() {
        return Bukkit.getServer().getOnlinePlayers();
    }
}
