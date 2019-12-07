package no.kh498.util.countdown.api.type;

import com.google.common.base.Preconditions;
import no.kh498.util.countdown.api.Countdown;
import no.kh498.util.countdown.api.timeFormat.TimeFormat;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;

/**
 * Let only a single player see the countdown
 * <p>
 * {@inheritDoc}
 *
 * @author Elg
 */
public class CountdownSingle extends Countdown {

    @NotNull
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
    public CountdownSingle(final Plugin plugin, @NotNull final String text, final long time,
                           final TimeFormat timeFormat, @Nullable final Player player) {
        super(plugin, text, time, timeFormat);
        Preconditions.checkArgument(player != null);
        this.player = Collections.singletonList(player);
    }

    @NotNull
    @Override
    public Collection<Player> getPlayers() {
        return player;
    }
}
