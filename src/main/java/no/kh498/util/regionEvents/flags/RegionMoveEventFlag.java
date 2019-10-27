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
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public abstract class RegionMoveEventFlag extends Handler {

    RegionMoveEventFlag(@NotNull final Session session) {
        super(session);
    }

    @Override
    public boolean onCrossBoundary(@NotNull final Player player, final Location from, @NotNull final Location to,
                                   @NotNull final ApplicableRegionSet toSet, final Set<ProtectedRegion> entered,
                                   final Set<ProtectedRegion> exited, @NotNull final MoveType moveType) {
        final LocalPlayer localPlayer = getPlugin().wrapPlayer(player);
        final boolean allowed = toSet.testState(localPlayer, getFlagType());
        final boolean hasBypass = getSession().getManager().hasBypass(player, to.getWorld());

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

