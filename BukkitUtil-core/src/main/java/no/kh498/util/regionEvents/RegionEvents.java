package no.kh498.util.regionEvents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;


/**
 * @author Elg
 */
public final class RegionEvents {

    private static final Logger logger = LoggerFactory.getLogger(RegionEvents.class);

    private RegionEvents() {}

    /**
     * Initiate calling of entry/exit world guard events for both WorldGuard 6.2+ and 7.0+
     */
    public static void initiate() {
        try {
            Class<?> v6Class = RegionEvents.class.getClassLoader().loadClass("no.kh498.util.regionEvents.v6.Register");
            v6Class.getMethod("initiate6x").invoke(null);
            return;
        } catch (NoSuchMethodError | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            logger.trace("Failed to initiate wg 6", e);
        }

        try {
            Class<?> v7Class = RegionEvents.class.getClassLoader().loadClass("no.kh498.util.regionEvents.v7.Register");
            v7Class.getMethod("initiate7x").invoke(null);
            return;
        } catch (NoSuchMethodError | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            logger.trace("Failed to initiate wg 7", e);
        }

        logger.error("Failed to initiate WorldGuard for both 6.x and 7.x WorldGuard events will not work.");

    }
}
