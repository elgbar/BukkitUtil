package no.kh498.util.regionEvents;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.internal.platform.WorldGuardPlatform;
import com.sk89q.worldguard.session.handler.Handler;
import no.kh498.util.regionEvents.flags.EntryEventFlag;
import no.kh498.util.regionEvents.flags.ExitEventFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * @author Elg
 */
public class RegionEvents {

    private static final Logger logger = LoggerFactory.getLogger(RegionEvents.class);

    /**
     * Initiate calling of entry/exit world guard events for both WorldGuard 6.2+ and 7.0+
     */
    public static void initiate() {
        try {
            initiate6x();
            return;
        } catch (NoSuchMethodError e) {
            logger.debug("Failed to initiate wg 6", e);
        }

        try {
            initiate7x();
            return;
        } catch (NoSuchMethodError | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            logger.debug("Failed to initiate wg 7", e);
        }

        logger.error("Failed to initiate WorldGuard for both 6.x and 7.x WorldGuard events will not work");

    }

    /**
     * Initiate calling of entry/exit world guard events for WorldGuard 6.2+
     */
    public static void initiate6x() {
        WorldGuardPlugin.inst().getSessionManager().registerHandler(EntryEventFlag.FACTORY, null);
        WorldGuardPlugin.inst().getSessionManager().registerHandler(ExitEventFlag.FACTORY, null);
    }

    /**
     * Initiate calling of entry/exit world guard events for WorldGuard 7.0+
     * <p>
     * This has to be done with reflection as WorldGuard 6.x are expecting
     * {@code com.sk89q.worldguard.session.SessionManager}
     * to be a class while WorldGuard 7.x are expecting it to be an interface.
     */
    public static void initiate7x()
    throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //must use class loader for WorldGuard 7 to make sure we get the correct classes
        ClassLoader cl = WorldGuard.class.getClassLoader();

        Class<?> bsm = cl.loadClass("com.sk89q.worldguard.session.SessionManager");
        //make sure we dont use this method when WorldGuard 6 is loaded (or is loaded before WorldGuard 7)
        if (!bsm.isInterface()) {
            logger.error("SessionManager is not an interface, are you running WorldGuard 6.x?");
        }

        WorldGuardPlatform wgp = WorldGuard.getInstance().getPlatform();
        //We must get the session manager via reflection as otherwise the compiler thinks we mean
        // the WorldGuard 6 class not the WorldGuard 7 interface
        Object sessionManager = wgp.getClass().getMethod("getSessionManager").invoke(wgp);

        Method regHand = sessionManager.getClass().getMethod("registerHandler", Handler.Factory.class,
                                                             Handler.Factory.class);
        regHand.invoke(sessionManager, EntryEventFlag.FACTORY, null);
        regHand.invoke(sessionManager, ExitEventFlag.FACTORY, null);
    }

}
