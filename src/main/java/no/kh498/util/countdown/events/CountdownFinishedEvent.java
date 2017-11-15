package no.kh498.util.countdown.events;

import no.kh498.util.countdown.api.Countdown;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author karl henrik
 */
public class CountdownFinishedEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Countdown countdown;

    public CountdownFinishedEvent(final Countdown countdown) {
        this.countdown = countdown;
    }

    public Countdown getCountdown() {return this.countdown;}

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
