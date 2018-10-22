package no.kh498.util;

import org.bukkit.Location;

/**
 * @author Elg
 */
public final class LocationUtil {

    /**
     * Round the xyz values of a location
     *
     * @param location
     *     Location to round
     *
     * @return The location's XYZ values set to {@code location.getBlockX/Y/Z()}
     */
    public static Location roundLocation(final Location location) {
        location.setX(location.getBlockX());
        location.setY(location.getBlockY());
        location.setZ(location.getBlockZ());
        location.setYaw(0);
        location.setPitch(0);
        return location;
    }

    /**
     * @param location
     *     The location to beautify
     *
     * @return A nicely formatted location, will not display world
     */
    public static String beautify(final Location location) {
        return beautify(location, false);
    }

    /**
     * @param location
     *     The location to beautify
     * @param displayWorld
     *     if the world should be displayed
     *
     * @return A nicely formatted location, if world is not null it is displayed as well
     */
    public static String beautify(final Location location, boolean displayWorld) {
        if (location == null) {
            return "null";
        }
        String world =
            displayWorld && location.getWorld() != null ? String.format(" in world '%s'", location.getWorld().getName())
                                                        : "";
        return String.format("%d, %d, %d%s", location.getBlockX(), location.getBlockY(), location.getBlockZ(), world);
    }


}