package no.kh498.util.regionEvents.v6;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.session.MoveType;
import com.sk89q.worldguard.session.Session;
import no.kh498.util.regionEvents.events.RegionEnterEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * @author Elg
 */
public class EntryHandler extends RegionMoveHandler {

    public static final Factory FACTORY = new Factory();

    private EntryHandler(final Session session) {
        super(session);
    }

    @Override
    public boolean testNewRegion(@NotNull final Set<ProtectedRegion> entered,
                                 @NotNull final Set<ProtectedRegion> exited) {
        return !entered.isEmpty();
    }

    /**
     * The event to call
     */
    @Override
    public boolean callEvent(@NotNull final Player player, @NotNull final Location from, @NotNull final Location to,
                             @NotNull final Set<ProtectedRegion> entered, @NotNull final Set<ProtectedRegion> exited,
                             @NotNull final MoveType moveType, final boolean hasBypass) {
        final RegionEnterEvent regionEnterEvent = new RegionEnterEvent(player, from, to, entered, moveType, hasBypass);
        Bukkit.getPluginManager().callEvent(regionEnterEvent);
        return regionEnterEvent.isCancelled();
    }

    @Override
    public StateFlag getFlagType() {
        return DefaultFlag.ENTRY;
    }

    private static class Factory extends com.sk89q.worldguard.session.handler.Handler.Factory<EntryHandler> {

        @NotNull
        @Override
        public EntryHandler create(final Session session) {
            return new EntryHandler(session);
        }
    }
}
