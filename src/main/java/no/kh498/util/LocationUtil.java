package no.kh498.util;

import org.bukkit.Location;

import static java.lang.String.valueOf;

/**
 * @author kh498
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
     * @return A string in the format of "{@code x, y, z}" excluding the quotation marks. If location is null "null"
     * is returned
     */
    public static String beautify(final Location location) {
        if (location == null) {
            return "null";
        }
        return valueOf(location.getBlockX()) + ", " + location.getBlockY() + ", " + location.getBlockZ();
    }
}
