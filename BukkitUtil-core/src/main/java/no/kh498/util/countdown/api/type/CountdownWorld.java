package no.kh498.util.countdown.api.type;

import com.google.common.base.Preconditions;
import java.util.Collection;
import no.kh498.util.countdown.api.Countdown;
import no.kh498.util.countdown.api.timeFormat.TimeFormat;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Let all the players in a world see the countdown
 * <p>
 * {@inheritDoc}
 *
 * @author Elg
 */
public class CountdownWorld extends Countdown {

  @Nullable
  private final World world;

  /**
   * @param plugin
   *   The plugin that is using the countdown
   * @param text
   *   The text to display in the action bar. Must contain a {@code %s} where the time left is inserted
   * @param time
   *   How long the countdown should be in milliseconds
   * @param timeFormat
   *   The way time is displayed
   * @param world
   *   The world to send the countdown to
   */
  public CountdownWorld(final Plugin plugin, @NotNull final String text, final long time, final TimeFormat timeFormat,
                        @Nullable final World world) {
    super(plugin, text, time, timeFormat);
    Preconditions.checkArgument(world != null);
    this.world = world;
  }

  @Nullable
  public World getWorld() {
    return world;
  }

  @Override
  public @NotNull Collection<Player> getPlayers() {
    //TODO maybe cache this and check less often?
    return world.getPlayers();
  }
}
