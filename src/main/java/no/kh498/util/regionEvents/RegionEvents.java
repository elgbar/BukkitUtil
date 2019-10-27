package no.kh498.util.regionEvents;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import no.kh498.util.regionEvents.flags.EntryEventFlag;
import no.kh498.util.regionEvents.flags.ExitEventFlag;

/**
 * @author Elg
 */
public class RegionEvents {

    /**
     * Called for the region enter/exit events to be called. Must be called for the events to be called
     */
    public static void initiate() {
        WorldGuardPlugin.inst().getSessionManager().registerHandler(EntryEventFlag.FACTORY, null);
        WorldGuardPlugin.inst().getSessionManager().registerHandler(ExitEventFlag.FACTORY, null);
    }
}
