package no.kh498.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
     * Save the precise location with pitch and yaw, if no world is defined in the location it will not be saved
     *
     * @param location
     *     The location to save
     *
     * @return A ConfigurationSection containing the serialized location
     */
    @NotNull
    public static ConfigurationSection locationToConfig(@NotNull Location location, boolean useUUID) {
        ConfigurationSection conf = new YamlConfiguration();

        if (location.getWorld() != null) {
            if (useUUID) { conf.set(WORLD_UID, location.getWorld().getUID()); }
            else { conf.set(WORLD_NAME, location.getWorld().getName()); }
        }

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
    @Nullable
    public static Location locationFromConfig(@NotNull ConfigurationSection conf, boolean useUUID) {
        World world = worldFromConfig(conf, useUUID);

        if (world == null) {
            return null;
        }

        try {
            double x = Double.parseDouble(conf.getString(X));
            double y = Double.parseDouble(conf.getString(Y));
            double z = Double.parseDouble(conf.getString(Z));
            float yaw = Float.parseFloat(conf.getString(YAW));
            float pitch = Float.parseFloat(conf.getString(PITCH));

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
    @NotNull
    public static ConfigurationSection blockLocationToConfig(@NotNull Location location, boolean useUUID) {
        ConfigurationSection conf = new YamlConfiguration();

        if (location.getWorld() != null) {
            if (useUUID) { conf.set(WORLD_UID, location.getWorld().getUID()); }
            else { conf.set(WORLD_NAME, location.getWorld().getName()); }
        }

        conf.set(X, location.getBlockX());
        conf.set(Y, location.getBlockY());
        conf.set(Z, location.getBlockZ());
        return conf;
    }

    /**
     * @param conf
     *     The config to get the world from
     * @param useUUID
     *     If {@link #WORLD_UID} should be used, if false the {@link #WORLD_NAME} will be used
     *
     * @return The location saved in {@code conf}
     *
     * @see #worldlessBlockLocationFromConfig(ConfigurationSection)
     */
    @Nullable
    public static Location blockLocationFromConfig(@NotNull ConfigurationSection conf, boolean useUUID) {
        World world = worldFromConfig(conf, useUUID);
        if (world == null) {
            return null;
        }
        Location loc = worldlessBlockLocationFromConfig(conf);
        if (loc == null) {
            return null;
        }
        loc.setWorld(world);
        return loc;
    }

    /**
     * @param conf
     *     The config to get the world from
     *
     * @return A location with the x, y and z defined but not the world
     *
     * @see #blockLocationFromConfig(ConfigurationSection, boolean)
     */
    @Nullable
    public static Location worldlessBlockLocationFromConfig(@NotNull ConfigurationSection conf) {
        try {
            int x = Integer.parseInt(conf.getString(X));
            int y = Integer.parseInt(conf.getString(Y));
            int z = Integer.parseInt(conf.getString(Z));
            return new Location(null, x, y, z);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    @Nullable
    public static World worldFromConfig(@NotNull ConfigurationSection conf, boolean useUUID) {
        if (useUUID) {
            UUID worldUUID;
            try {
                worldUUID = UUID.fromString(conf.getString(WORLD_UID, ""));
            } catch (IllegalArgumentException e) {
                return null;
            }
            return Bukkit.getWorld(worldUUID);
        }
        else {
            String worldName = conf.getString(WORLD_NAME);
            if (worldName == null) {
                return null;
            }
            return Bukkit.getWorld(worldName);
        }
    }
}
