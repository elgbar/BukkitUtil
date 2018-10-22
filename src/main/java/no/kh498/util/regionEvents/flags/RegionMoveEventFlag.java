package no.kh498.util.regionEvents.flags;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.session.MoveType;
import com.sk89q.worldguard.session.Session;
import com.sk89q.worldguard.session.handler.Handler;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Set;

public abstract class RegionMoveEventFlag extends Handler {

    RegionMoveEventFlag(final Session session) {
        super(session);
    }

    @Override
    public boolean onCrossBoundary(final Player player, final Location from, final Location to,
                                   final ApplicableRegionSet toSet, final Set<ProtectedRegion> entered,
                                   final Set<ProtectedRegion> exited, final MoveType moveType) {
        final LocalPlayer localPlayer = getPlugin().wrapPlayer(player);
        final boolean allowed = toSet.testState(localPlayer, getFlagType());
        final boolean hasBypass = getSession().getManager().hasBypass(player, to.getWorld());

        //noinspection SimplifiableIfStatement do not simplify as this is way more readable
        if (testNewRegion(entered, exited) && (hasBypass || allowed || !moveType.isCancellable())) {
            final boolean isCancelled = callEvent(player, from, to, entered, exited, moveType, hasBypass);
            return !isCancelled;
        }
        return true;
    }

    abstract StateFlag getFlagType();

    /**
     * @return {@code true} if an event should be called
     */
    abstract boolean testNewRegion(final Set<ProtectedRegion> entered, final Set<ProtectedRegion> exited);

    /**
     * The event to call
     *
     * @return if the event is cancelled or not
     */
    abstract boolean callEvent(Player player, Location from, Location to, Set<ProtectedRegion> entered,
                               Set<ProtectedRegion> exited, MoveType moveType, boolean hasBypass);
}

