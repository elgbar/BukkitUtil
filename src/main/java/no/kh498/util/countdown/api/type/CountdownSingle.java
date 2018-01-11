package no.kh498.util.countdown.api.type;

import no.kh498.util.countdown.api.Countdown;
import no.kh498.util.countdown.api.timeFormat.TimeFormat;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.Collections;

/**
 * Let only a single player see the countdown
 * <p>
 * {@inheritDoc}
 *
 * @author karl henrik
 */
public class CountdownSingle extends Countdown {

    private final Collection<Player> player;

    /**
     * @param plugin
     *     The plugin that is using the countdown
     * @param text
     *     The text to display in the action bar. Must contain a {@code %s} where the time left is inserted
     * @param time
     *     How long the countdown should be in milliseconds
     * @param timeFormat
     *     The way time is displayed
     * @param player
     *     the player to see the countdown
     */
    public CountdownSingle(final Plugin plugin, final String text, final long time, final TimeFormat timeFormat,
                           final Player player) {
        super(plugin, text, time, timeFormat);
        this.player = Collections.singletonList(player);
    }

    @Override
    public Collection<Player> getPlayers() {
        return this.player;
    }
}
