package no.kh498.util.regionEvents.v6;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.session.MoveType;
import com.sk89q.worldguard.session.Session;
import com.sk89q.worldguard.session.handler.Handler;
import no.kh498.util.regionEvents.events.RegionExitEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * @author Elg
 */
public class ExitHandler extends RegionMoveHandler {


    public static final Factory FACTORY = new Factory();

    private ExitHandler(final Session session) {
        super(session);
    }

    @Override
    public boolean testNewRegion(@NotNull final Set<ProtectedRegion> entered,
                                 @NotNull final Set<ProtectedRegion> exited) {
        return !exited.isEmpty();
    }

    /**
     * The event to call
     */
    @Override
    public boolean callEvent(@NotNull final Player player, @NotNull final Location from, @NotNull final Location to,
                             @NotNull final Set<ProtectedRegion> entered, @NotNull final Set<ProtectedRegion> exited,
                             @NotNull final MoveType moveType, final boolean hasBypass) {
        final RegionExitEvent regionExitEvent = new RegionExitEvent(player, from, to, exited, moveType, hasBypass);
        Bukkit.getPluginManager().callEvent(regionExitEvent);
        return regionExitEvent.isCancelled();
    }

    @Override
    public StateFlag getFlagType() {
        return DefaultFlag.EXIT;
    }

    private static class Factory extends Handler.Factory<ExitHandler> {

        @NotNull
        @Override
        public ExitHandler create(final Session session) {
            return new ExitHandler(session);
        }
    }
}
