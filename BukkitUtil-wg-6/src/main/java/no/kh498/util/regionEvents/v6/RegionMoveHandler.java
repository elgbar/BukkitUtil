package no.kh498.util.regionEvents.v6;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.session.MoveType;
import com.sk89q.worldguard.session.Session;
import com.sk89q.worldguard.session.handler.Handler;
import no.kh498.util.regionEvents.RegionMove;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public abstract class RegionMoveHandler extends Handler implements RegionMove {

    RegionMoveHandler(@NotNull final Session session) {
        super(session);
    }

    @Override
    public boolean onCrossBoundary(@NotNull final Player player, final Location from, @NotNull final Location to,
                                   @NotNull final ApplicableRegionSet toSet, final Set<ProtectedRegion> entered,
                                   final Set<ProtectedRegion> exited, @NotNull final MoveType moveType) {
        final LocalPlayer localPlayer = WGBukkit.getPlugin().wrapPlayer(player);
        final boolean allowed = toSet.testState(localPlayer, getFlagType());
        final boolean hasBypass = getSession().getManager().hasBypass(player, to.getWorld());

        if (testNewRegion(entered, exited) && (hasBypass || allowed || !moveType.isCancellable())) {
            final boolean isCancelled = callEvent(player, from, to, entered, exited, moveType, hasBypass);
            return !isCancelled;
        }
        return true;
    }


}

