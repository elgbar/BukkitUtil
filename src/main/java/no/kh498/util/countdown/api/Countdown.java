package no.kh498.util.countdown.api;

import no.kh498.util.chat.AdvancedChat;
import no.kh498.util.countdown.api.timeFormat.TimeFormat;
import no.kh498.util.countdown.events.CountdownFinishedEvent;
import no.kh498.util.log.Logger;
import no.kh498.util.log.Severity;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.HashMap;

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
 * @author karl henrik
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class Countdown implements Runnable {

    private String text;
    private long time;
    private final Plugin plugin;
    private boolean running;
    private long startTime;
    private HashMap<Player, Interrupt> lastInterrupt;
    private final long delay;
    private final TimeFormat timeFormat;

    /**
     * @param plugin
     *     The plugin that is using the countdown
     * @param text
     *     The text to display in the action bar. Must contain a {@code %s} where the time left is inserted
     * @param time
     *     How long the countdown should be in milliseconds
     * @param delay
     *     How long to wait between each time run is called (in ms)
     * @param timeFormat
     *     The way time is displayed
     */
    public Countdown(final Plugin plugin, final String text, final long time, final long delay,
                     final TimeFormat timeFormat) {

        if (text == null || !text.contains("%s")) {
            throw new IllegalArgumentException("The text must contain a %s where the time left is inserted");
        }
        if (plugin == null || timeFormat == null) {
            throw new IllegalArgumentException("Neither the plugin nor the timeFormat can be null");
        }
        if (time < 0 || delay < 0) {
            throw new IllegalArgumentException("Neither the time nor the delay can be less than 0");
        }
        this.timeFormat = timeFormat;
        this.plugin = plugin;
        this.text = text;
        this.time = time;
        this.delay = delay;
    }

    /**
     * @return The players who should see the countdown.
     */
    public abstract Collection<Player> getPlayers();

    @Override
    public void run() {
        if (!isRunning()) {
            return;
        }

        //calculate the time left
        final long timeLeft = (this.startTime + this.time) - System.currentTimeMillis();
        if (timeLeft < 0) {
            stop(true);
            return;
        }

        final String message = String.format(this.text, this.timeFormat.formatTime(timeLeft));

        for (final Player player : getPlayers()) {
            String msgCpy = message;

            //display the interrupt message if there is one, delete old interrupt messages
            final Interrupt interrupt = this.lastInterrupt.get(player);
            if (interrupt != null) {
                if (interrupt.shouldInterrupt()) {
                    msgCpy = interrupt.getMessage();
                }
                else {
                    this.lastInterrupt.remove(player);
                }
            }
            AdvancedChat.sendActionbar(msgCpy, player);
        }
        Bukkit.getScheduler().runTaskLater(getPlugin(), this, this.delay);
    }

    /**
     * Start the countdown
     */
    public void start() {
        Logger.log(Severity.FINER, "Countdown started");
        this.running = true;
        run();
    }

    /**
     * Stop the countdown
     *
     * @param callEvent
     *     If the {@link CountdownFinishedEvent} event should be called
     */
    public void stop(final boolean callEvent) {
        Logger.log(Severity.FINER, "Stop called for countdown");
        this.running = false;
        if (callEvent) {
            Logger.log(Severity.FINER, "Calling event on stop");
            Bukkit.getPluginManager().callEvent(new CountdownFinishedEvent(this));
        }
    }

    /**
     * Reset the countdown to {@link #time}
     */
    public void reset() {
        Logger.log(Severity.FINER, "Resetting countdown");
        this.startTime = System.currentTimeMillis();
        this.lastInterrupt = new HashMap<>();
    }

    /**
     * @param player
     *     The player to send the interrupt to
     * @param interruptText
     *     The interruption text
     * @param time
     *     The duration of the interrupt in milliseconds
     * @param force
     *     if this messages should be forced to replace another interrupt
     */
    public void interrupt(final Player player, final String interruptText, final int time, final boolean force) {
        final Interrupt interrupt = new Interrupt(interruptText, time);
        if (force) {
            this.lastInterrupt.put(player, interrupt);
        }
        this.lastInterrupt.putIfAbsent(player, interrupt);
    }

    /**
     * @return If the countdown is running or not
     */
    public boolean isRunning() {
        return this.running;
    }

    /**
     * Try to send a interrupt message, if no countdown is running send a actionbar message instead
     *
     * @param player
     *     The player to send the interrupt to
     * @param interruptText
     *     The interruption text
     * @param time
     *     The duration of the interrupt in milliseconds
     * @param force
     *     if this messages should be forced to replace another interrupt
     */
    public void tryInterrupt(final Player player, final String interruptText, final int time, final boolean force) {
        if (isRunning()) {
            interrupt(player, interruptText, time, force);
        }
        else {
            AdvancedChat.sendActionbar(interruptText, player);
        }
    }

    public void setText(final String text) {
        this.text = text;
    }

    public void setTime(final int time) {
        this.time = time;
    }

    public String getText() {
        return this.text;
    }

    public long getTime() {
        return this.time;
    }

    private Plugin getPlugin() {
        return this.plugin;
    }
}
