package no.kh498.util.countdown.api.type;

import com.google.common.base.Preconditions;
import no.kh498.util.countdown.api.Countdown;
import no.kh498.util.countdown.api.timeFormat.TimeFormat;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collection;

/**
 * Let all the players in a list see the countdown
 * <p>
 * {@inheritDoc}
 *
 * @author Elg
 */
public class CountdownList extends Countdown {

    private final Collection<Player> players;

    /**
     * @param plugin
     *     The plugin that is using the countdown
     * @param text
     *     The text to display in the action bar. Must contain a {@code %s} where the time left is inserted
     * @param time
     *     How long the countdown should be in milliseconds
     * @param timeFormat
     *     The way time is displayed
     * @param players
     *     the players to see the countdown
     */
    public CountdownList(final Plugin plugin, final String text, final long time, final TimeFormat timeFormat,
                         final Collection<Player> players) {
        super(plugin, text, time, timeFormat);
        Preconditions.checkArgument(players != null);
        this.players = players;
    }

    @Override
    public Collection<Player> getPlayers() {
        return this.players;
    }
}
