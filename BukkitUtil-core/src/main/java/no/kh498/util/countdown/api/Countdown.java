package no.kh498.util.countdown.api;

import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import no.kh498.util.chat.AdvancedChat;
import no.kh498.util.countdown.api.timeFormat.TimeFormat;
import no.kh498.util.countdown.events.CountdownFinishedEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The countdown classes are a way to easily make a countdown by using the actionbar. There is also a interrupter where
 * you can for a limited time display another message for a certain player.
 * <p>
 * The api is made so users can easily customize who sees the countdown and how it is viewed.
 * <p>
 * To change the players who sees the count simply extend this class and make your own interpretation of {@link
 * #getPlayers()}
 * <p>
 * To change the way the time is viewed implement {@link TimeFormat} in your own class
 *
 * @author Elg
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class Countdown implements Runnable {

  // For a bukkit implementation you can use https://github.com/rjenkinsjr/slf4bukkit
  private static final Logger LOG = LoggerFactory.getLogger(Countdown.class);
  @NotNull
  private final Plugin plugin;
  @NotNull
  private final TimeFormat timeFormat;
  private String text;
  private long time;
  private boolean running;
  private long startTime;
  @NotNull
  private final HashMap<@NotNull Player, @NotNull Interrupt> lastInterrupt = new HashMap<>();
  @Nullable
  private BukkitTask bukkitTask = null;

  /**
   * @param plugin
   *   The plugin that is using the countdown
   * @param text
   *   The text to display in the action bar. Must contain a {@code %s} where the time left is inserted
   * @param timeMS
   *   How long the countdown should be in milliseconds
   * @param timeFormat
   *   The way time is displayed
   */
  public Countdown(@NotNull final Plugin plugin, @NotNull final String text, final long timeMS,
                   @NotNull final TimeFormat timeFormat) {
    Preconditions.checkArgument(timeMS > 0, "The time must be larger than 0");
    Preconditions.checkArgument(text.contains("%s"), "The text must contain a %s where the time left is inserted");

    this.timeFormat = timeFormat;
    this.plugin = plugin;
    this.text = text;
    time = timeMS;

    reset();
  }

  /**
   * Reset the countdown to {@link #time}
   */
  public void reset() {
    LOG.debug("Resetting countdown");
    startTime = System.currentTimeMillis();
    lastInterrupt.clear();
  }

  /**
   * Try to send a interrupt message, if no countdown is running send a actionbar message instead
   *
   * @param c
   *   The countdown to use
   * @param player
   *   The player to send the interrupt to
   * @param interruptText
   *   The interruption text
   * @param time
   *   The duration of the interrupt in milliseconds
   * @param force
   *   if this messages should be forced to replace another interrupt
   */
  public static void tryInterrupt(@Nullable final Countdown c, @NotNull final Player player,
                                  @NotNull final String interruptText, final long time, final boolean force) {
    if (c != null && c.running) {
      c.interrupt(player, interruptText, time, force);
    }
    else {
      AdvancedChat.sendActionbar(interruptText, player);
    }
  }

  /**
   * Start the countdown
   */
  public void start() {
    LOG.debug("Countdown started");
    running = true;
    run();
  }

  @Override
  public void run() {
    if (!running) {
      LOG.trace("called run while not running");
      return;
    }

    final long timeLeft = getTimeLapsed();
    if (timeLeft < 0) {
      LOG.trace("no more time left, calling event");
      stop(true);
      return;
    }

    final String message = String.format(text, timeFormat.formatTime(timeLeft));

    for (final Player player : getPlayers()) {
      String msgCpy = message;

      //display the interrupt message if there is one, delete old interrupt messages
      final Interrupt interrupt = lastInterrupt.get(player);
      if (interrupt != null) {
        if (interrupt.shouldInterrupt()) {
          msgCpy = interrupt.getMessage();
        }
        else {
          lastInterrupt.remove(player);
        }
      }
      AdvancedChat.sendActionbar(msgCpy, player);
    }
    bukkitTask = Bukkit.getScheduler().runTaskLater(plugin, this, timeFormat.delay());
  }

  /**
   * @return calculate the time left in milliseconds
   */
  public long getTimeLapsed() {
    return (startTime + time) - System.currentTimeMillis();
  }

  /**
   * Stop the countdown
   *
   * @param callEvent
   *   If the {@link CountdownFinishedEvent} event should be called
   */
  public void stop(final boolean callEvent) {
    LOG.debug("Stop called for countdown");
    running = false;
    if (bukkitTask != null) {
      bukkitTask.cancel();
    }
    if (callEvent) {
      LOG.debug("Calling event on stop");
      Bukkit.getPluginManager().callEvent(new CountdownFinishedEvent(this));
    }
  }

  /**
   * @return The players who should see the countdown.
   */
  @NotNull
  public abstract Collection<? extends Player> getPlayers();

  /**
   * {@link #reset()} and set the start time to {@code timeLapsed}. This can be used when resuming a timer saved to
   * disk.
   *
   * @param timeLapsed
   *   How many milliseconds to skip
   */
  public void setTimeLapsed(final long timeLapsed) {
    reset();
    startTime -= timeLapsed;
  }

  /**
   * @return If the countdown is running or not
   */
  public boolean isRunning() {
    return running;
  }

  /**
   * Try to send a interrupt message, if no countdown is running send a actionbar message instead
   *
   * @param player
   *   The player to send the interrupt to
   * @param interruptText
   *   The interruption text
   * @param time
   *   The duration of the interrupt in milliseconds
   * @param force
   *   if this messages should be forced to replace another interrupt
   *
   * @deprecated Use the static method to support the countdown being null
   */
  @Deprecated
  public void tryInterrupt(final Player player, @NotNull final String interruptText, final long time,
                           final boolean force) {
    if (running) {
      interrupt(player, interruptText, time, force);
    }
    else {
      AdvancedChat.sendActionbar(interruptText, player);
    }
  }

  /**
   * @param player
   *   The player to send the interrupt to
   * @param interruptText
   *   The interruption text
   * @param time
   *   The duration of the interrupt in milliseconds
   * @param force
   *   if this messages should be forced to replace another interrupt
   */
  public void interrupt(@NotNull final Player player, final String interruptText, final long time,
                        final boolean force) {
    final Interrupt interrupt = new Interrupt(interruptText, time);
    if (force) {
      lastInterrupt.put(player, interrupt);
    }
    lastInterrupt.putIfAbsent(player, interrupt);
  }

  /**
   * @return The text displayed in the actionbar chat
   */
  public String getText() {
    return text;
  }

  public void setText(final String text) {
    this.text = text;
  }

  /**
   * @return How long each countdown will be
   */
  public long getTime() {
    return time;
  }

  public void setTime(final long time) {
    this.time = time;
  }

  @Nullable
  private Plugin getPlugin() {
    return plugin;
  }

  @Override
  public int hashCode() {

    return Objects.hash(text, time, plugin, startTime, timeFormat);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) { return true; }
    if (o == null || getClass() != o.getClass()) { return false; }
    Countdown countdown = (Countdown) o;
    return time == countdown.time && startTime == countdown.startTime && Objects.equals(text, countdown.text) &&
           Objects.equals(plugin, countdown.plugin) && Objects.equals(timeFormat, countdown.timeFormat) &&
           Objects.equals(bukkitTask, countdown.bukkitTask);
  }
}
