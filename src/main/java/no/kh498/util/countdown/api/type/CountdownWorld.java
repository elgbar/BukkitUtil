package no.kh498.util.countdown.api.type;

import no.kh498.util.countdown.api.Countdown;
import no.kh498.util.countdown.api.timeFormat.TimeFormat;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collection;

/**
 * {@inheritDoc}
 *
 * @author karl henrik
 */
public class CountdownWorld extends Countdown {

    private final World world;

    /**
     * @param plugin
     *     The plugin that is using the countdown
     * @param text
     *     The text to display in the action bar. Must contain a {@code %s} where the time left is inserted
     * @param time
     *     How long the countdown should be in milliseconds
     * @param delay
     *     How long to wait between each time run is called (in ms)
     * @param world
     *     The world to send the countdown to
     */
    public CountdownWorld(final Plugin plugin, final String text, final long time, final long delay,
                          final TimeFormat timeFormat, final World world) {
        super(plugin, text, time, delay, timeFormat);
        this.world = world;
    }

    public World getWorld() {
        return this.world;
    }

    @Override
    public Collection<Player> getPlayers() {
        //TODO maybe cache this and check less often?
        return this.world.getPlayers();
    }
}
