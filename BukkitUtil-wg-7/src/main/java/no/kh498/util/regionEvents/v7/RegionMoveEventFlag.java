package no.kh498.util.regionEvents.v7;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.session.MoveType;
import com.sk89q.worldguard.session.Session;
import com.sk89q.worldguard.session.handler.Handler;
import no.kh498.util.regionEvents.RegionMove;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public abstract class RegionMoveEventFlag extends Handler implements RegionMove {

    RegionMoveEventFlag(@NotNull final Session session) {
        super(session);
    }

    @Override
    public boolean onCrossBoundary(LocalPlayer player, Location from, Location to, ApplicableRegionSet toSet,
                                   Set<ProtectedRegion> entered, Set<ProtectedRegion> exited, MoveType moveType) {
        final boolean allowed = toSet.testState(player, getFlagType());
        final boolean hasBypass = getSession().getManager().hasBypass(player, (World) to.getExtent());

        if (testNewRegion(entered, exited) && (hasBypass || allowed || !moveType.isCancellable())) {
            final boolean isCancelled = callEvent(BukkitAdapter.adapt(player), BukkitAdapter.adapt(from),
                                                  BukkitAdapter.adapt(to), entered, exited, moveType, hasBypass);
            return !isCancelled;
        }
        return true;
    }
}

