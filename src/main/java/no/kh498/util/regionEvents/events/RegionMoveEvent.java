package no.kh498.util.regionEvents.events;

import com.sk89q.worldguard.session.MoveType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author karl henrik
 */
abstract class RegionMoveEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final Location from;
    private final Location to;
    private final MoveType moveType;
    private final boolean bypass;

    private boolean cancelled = false;

    RegionMoveEvent(final Player player, final Location from, final Location to, final MoveType moveType,
                    final boolean bypass) {
        this.player = player;
        this.from = from;
        this.to = to;
        this.moveType = moveType;
        this.bypass = bypass;
    }

    /**
     * @return The player who walked through a region boundary
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * @return Location where the player was
     */
    public Location getFrom() {
        return this.from;
    }

    /**
     * @return Location where the player is going to be
     */
    public Location getTo() {
        return this.to;
    }

    /**
     * @return How the player traveled to the current location
     */
    public MoveType getMoveType() {
        return this.moveType;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(final boolean b) {
        this.cancelled = b;
    }

    public boolean hasBypass() {
        return this.bypass;
    }
}
