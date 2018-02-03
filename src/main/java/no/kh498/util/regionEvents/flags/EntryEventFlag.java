package no.kh498.util.regionEvents.flags;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.session.MoveType;
import com.sk89q.worldguard.session.Session;
import no.kh498.util.regionEvents.events.RegionEnterEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Set;

public class EntryEventFlag extends RegionMoveEventFlag {

    public static final EntryEventFlag.Factory FACTORY = new Factory();

    EntryEventFlag(final Session session) {
        super(session);
    }


    @Override
    public boolean testNewRegion(final Set<ProtectedRegion> entered, final Set<ProtectedRegion> exited) {
        return entered.size() > 0;
    }

    /**
     * The event to call
     */
    @Override
    boolean callEvent(final Player player, final Location from, final Location to, final Set<ProtectedRegion> entered,
                      final Set<ProtectedRegion> exited, final MoveType moveType, final boolean hasBypass) {
        final RegionEnterEvent regionEnterEvent = new RegionEnterEvent(player, from, to, entered, moveType, hasBypass);
        Bukkit.getPluginManager().callEvent(regionEnterEvent);
        return regionEnterEvent.isCancelled();
    }

    @Override
    public StateFlag getFlagType() {
        return DefaultFlag.ENTRY;
    }

    public static class Factory extends com.sk89q.worldguard.session.handler.Handler.Factory<EntryEventFlag> {

        Factory() {
        }

        @Override
        public EntryEventFlag create(final Session session) {
            return new EntryEventFlag(session);
        }
    }
}
