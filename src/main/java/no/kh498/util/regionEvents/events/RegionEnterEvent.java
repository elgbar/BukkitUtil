package no.kh498.util.regionEvents.events;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.session.MoveType;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Set;

/**
 * @author karl henrik
 */
public class RegionEnterEvent extends RegionMoveEvent {

    private final Set<ProtectedRegion> entered;

    public RegionEnterEvent(final Player player, final Location from, final Location to,
                            final Set<ProtectedRegion> entered, final MoveType moveType, final boolean bypass) {
        super(player, from, to, moveType, bypass);
        this.entered = entered;
    }

    /**
     * @return List of all the regions the player entered
     */
    public Set<ProtectedRegion> getEnteredSet() {
        return this.entered;
    }

    /**
     * @return The first region the player entered, usually this is correct
     */
    public ProtectedRegion getFirstEntered() {
        return this.entered.iterator().next();
    }
}
