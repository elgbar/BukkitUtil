package no.kh498.util.regionEvents.events;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.session.MoveType;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Set;

/**
 * @author Elg
 */
public class RegionExitEvent extends RegionMoveEvent {

    private final Set<ProtectedRegion> exited;

    public RegionExitEvent(final Player player, final Location from, final Location to,
                           final Set<ProtectedRegion> exited, final MoveType moveType, final boolean bypass) {
        super(player, from, to, moveType, bypass);
        this.exited = exited;
    }

    /**
     * @return List of all the regions the player exited
     */
    public Set<ProtectedRegion> getExitedSet() {
        return this.exited;
    }

    /**
     * @return The first region the player exited, usually this is correct
     */
    public ProtectedRegion getFirstExited() {
        return this.exited.iterator().next();
    }
}
