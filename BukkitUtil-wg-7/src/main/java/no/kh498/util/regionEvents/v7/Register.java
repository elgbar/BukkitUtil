package no.kh498.util.regionEvents.v7;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.internal.platform.WorldGuardPlatform;
import com.sk89q.worldguard.session.SessionManager;
import com.sk89q.worldguard.session.handler.Handler;
import no.kh498.util.regionEvents.RegionEvents;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Elg
 */
public class Register {

    /**
     * Initiate calling of entry/exit world guard events for WorldGuard 7.0+
     * <p>
     * This has to be done with reflection as WorldGuard 6.x are expecting
     * {@code com.sk89q.worldguard.session.SessionManager}
     * to be a class while WorldGuard 7.x are expecting it to be an interface.
     */
    public static void initiate7x() {
        SessionManager sm = WorldGuard.getInstance().getPlatform().getSessionManager();
        sm.registerHandler(EntryEventFlag.FACTORY, null);
        sm.registerHandler(ExitEventFlag.FACTORY, null);
    }
}
