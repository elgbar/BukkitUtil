package no.kh498.util.regionEvents.v6;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

/**
 * @author Elg
 */
public class Register {

    /**
     * Initiate calling of entry/exit world guard events for WorldGuard 6.2+
     */
    public static void initiate6x() {
        WorldGuardPlugin.inst().getSessionManager().registerHandler(EntryHandler.FACTORY, null);
        WorldGuardPlugin.inst().getSessionManager().registerHandler(ExitHandler.FACTORY, null);
    }
}
