package no.kh498.util.countdown.events;

import no.kh498.util.countdown.api.Countdown;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Elg
 */
public final class CountdownFinishedEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Countdown countdown;

    public CountdownFinishedEvent(final Countdown countdown) {
        this.countdown = countdown;
    }

    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Countdown getCountdown() {return countdown;}

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
