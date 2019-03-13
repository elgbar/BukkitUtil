package no.kh498.util.regionEvents.events;

import com.sk89q.worldguard.session.MoveType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * @author Elg
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

    @NotNull
    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * @return The player who walked through a region boundary
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return Location where the player was
     */
    public Location getFrom() {
        return from;
    }

    /**
     * @return Location where the player is going to be
     */
    public Location getTo() {
        return to;
    }

    /**
     * @return How the player traveled to the current location
     */
    public MoveType getMoveType() {
        return moveType;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(final boolean b) {
        cancelled = b;
    }

    public boolean hasBypass() {
        return bypass;
    }
}
