package no.kh498.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.UUID;

/**
 * @author Elg
 */
public class WorldLoader {


    public static final String WORLD_UID = "world_uid";
    public static final String WORLD_NAME = "world";
    public static final String X = "x";
    public static final String Y = "y";
    public static final String Z = "z";
    public static final String YAW = "yaw";
    public static final String PITCH = "pitch";


    /**
     * Save the precise location with pitch and yaw
     *
     * @param location
     *     The location to save
     *
     * @return A ConfigurationSection containing the serialized location
     */
    public static ConfigurationSection locationToConfig(Location location) {
        return locationToConfig(location, true);
    }

    /**
     * Save the precise location with pitch and yaw
     *
     * @param location
     *     The location to save
     *
     * @return A ConfigurationSection containing the serialized location
     */
    public static ConfigurationSection locationToConfig(Location location, boolean useUUID) {
        ConfigurationSection conf = new YamlConfiguration();

        if (useUUID) { conf.set(WORLD_UID, location.getWorld().getUID()); }
        else { conf.set(WORLD_NAME, location.getWorld().getName()); }

        conf.set(X, location.getX());
        conf.set(Y, location.getY());
        conf.set(Z, location.getZ());
        conf.set(YAW, location.getYaw());
        conf.set(PITCH, location.getPitch());
        return conf;
    }

    /**
     * @return The location saved in {@code conf}
     */
    public static Location locationFromConfig(ConfigurationSection conf) {
        return locationFromConfig(conf, true);
    }

    /**
     * @return The location saved in {@code conf}
     */
    public static Location locationFromConfig(ConfigurationSection conf, boolean useUUID) {
        World world;
        if (useUUID) { world = Bukkit.getWorld(UUID.fromString(conf.getString(WORLD_UID))); }
        else { world = Bukkit.getWorld(conf.getString(WORLD_NAME)); }

        if (world == null && !useUUID) {
            return null;
        }

        try {
            double x = Double.valueOf(conf.getString(X));
            double y = Double.valueOf(conf.getString(Y));
            double z = Double.valueOf(conf.getString(Z));
            float yaw = Float.valueOf(conf.getString(YAW));
            float pitch = Float.valueOf(conf.getString(PITCH));

            return new Location(world, x, y, z, yaw, pitch);
        } catch (NumberFormatException ex) {
            return null;
        }
    }


    /**
     * Save the location as precise to the nearest block, without yaw and pitch
     *
     * @param location
     *     The location to save
     *
     * @return A ConfigurationSection containing the serialized location
     */
    public static ConfigurationSection blockLocationToConfig(Location location) {
        return blockLocationToConfig(location, true);
    }

    /**
     * Save the location as precise to the nearest block, without yaw and pitch
     *
     * @param location
     *     The location to save
     *
     * @return A ConfigurationSection containing the serialized location
     */
    public static ConfigurationSection blockLocationToConfig(Location location, boolean useUUID) {
        ConfigurationSection conf = new YamlConfiguration();

        if (useUUID) { conf.set(WORLD_UID, location.getWorld().getUID()); }
        else { conf.set(WORLD_NAME, location.getWorld().getName()); }

        conf.set(X, location.getBlockX());
        conf.set(Y, location.getBlockY());
        conf.set(Z, location.getBlockZ());
        return conf;
    }

    /**
     * @return The location saved in {@code conf}
     */
    public static Location blockLocationFromConfig(ConfigurationSection conf) {
        return blockLocationFromConfig(conf, true);
    }

    /**
     * @return The location saved in {@code conf}
     */
    public static Location blockLocationFromConfig(ConfigurationSection conf, boolean useUUID) {
        World world = null;
        if (useUUID && conf.contains(WORLD_UID)) {
            world = Bukkit.getWorld(UUID.fromString(conf.getString(WORLD_UID)));
        }
        else if (conf.contains(WORLD_NAME)) { world = Bukkit.getWorld(conf.getString(WORLD_NAME)); }
        if (world == null && !useUUID) {
            return null;
        }

        try {
            int x = Integer.valueOf(conf.getString(X));
            int y = Integer.valueOf(conf.getString(Y));
            int z = Integer.valueOf(conf.getString(Z));

            return new Location(world, x, y, z);
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
