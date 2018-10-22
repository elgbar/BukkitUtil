package no.kh498.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Elg
 */
public class ConfigUtil {

    public static final String WORLD_UID = "world_uid";
    public static final String WORLD_NAME = "world";
    public static final String X = "x";
    public static final String Y = "y";
    public static final String Z = "z";
    public static final String YAW = "yaw";
    public static final String PITCH = "pitch";

    private static final Logger logger = LoggerFactory.getLogger(ConfigUtil.class);

    /**
     * Load all default files from the plugin's jar and place them in the datafolder.
     * If the resource does not exist in the jar an empty file will be created
     *
     * @param plugin
     *     The plugin to save from
     * @param resources
     *     The absolute path in plugins jar
     */
    public static void saveDefaultResources(Plugin plugin, String... resources) {
        for (String resource : resources) {
            try {
                plugin.saveResource(resource, false);
            } catch (IllegalArgumentException ex) {
                File outFile = new File(plugin.getDataFolder(), resource);

                if (outFile.exists()) {
                    continue;
                }

                int lastIndex = resource.lastIndexOf(47);
                File outDir = new File(plugin.getDataFolder(), resource.substring(0, lastIndex >= 0 ? lastIndex : 0));
                if (!outDir.exists()) {
                    //noinspection ResultOfMethodCallIgnored
                    outDir.mkdirs();
                }
                try {
                    //noinspection ResultOfMethodCallIgnored
                    outFile.createNewFile();
                } catch (IOException e) {
                    logger.error("Failed to create empty file {} in {}", resource, outDir);
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * @return A FileConfiguration from the relative plugin path or {@code null} if invalid yaml or no file found found
     */
    public static FileConfiguration getYaml(Plugin plugin, String... filename) {
        return getYaml(FileUtils.getDatafolderFile(plugin, filename));
    }

    /**
     * @return A FileConfiguration from the given file or {@code null} if invalid yaml or no file found found
     */
    public static FileConfiguration getYaml(File file) {
        YamlConfiguration conf = new YamlConfiguration();
        try {
            conf.load(file);
        } catch (InvalidConfigurationException e) {
            logger.warn("YAML in file {} is invalid.\n{}", file, e.getMessage());
            return null;
        } catch (IOException e) {
            logger.error("Failed to find the file", file);
            return null;
        }
        return conf;
    }

    /**
     * Save a FileConfiguration to the datafolder of a plugin
     */
    public static void saveYaml(Plugin plugin, FileConfiguration conf, String... savePath) {
        saveYaml(conf, FileUtils.getDatafolderFile(plugin, savePath));
    }

    public static void saveYaml(FileConfiguration conf, File file) {
        try {
            conf.save(file);
        } catch (IOException e) {
            logger.error("Failed to save file {} to {}", file.getName(), file.getPath());
            if (logger.isDebugEnabled()) { e.printStackTrace(); }
        }
    }

    /**
     * @param conf
     *     The config to load the values from
     * @param path
     *     The path to section to get
     *
     * @return A map of all nodes at the given path, if an error occurred an empty map will be returned
     */
    public static Map<String, Object> getMapSection(ConfigurationSection conf, String path) {
        return getMapSection(conf.get(path));
    }

    public static Map<String, Object> getMapSection(Object obj) {
        if (obj == null) { return new HashMap<>(); }
        try {
            MemorySection memProp = (MemorySection) obj;
            return memProp.getValues(false);
        } catch (ClassCastException e1) {
            try {
                //noinspection unchecked
                return (Map<String, Object>) obj;
            } catch (ClassCastException e) {
                return new HashMap<>();
            }
        }
    }

    public static ConfigurationSection getSection(ConfigurationSection conf, String path) {
        return getSectionFromMap(getMapSection(conf, path));
    }

    public static ConfigurationSection getSection(Object obj) {
        return getSectionFromMap(getMapSection(obj));
    }

    /**
     * Convert a map into a configuration section
     */
    public static ConfigurationSection getSectionFromMap(Map<String, Object> map) {
        ConfigurationSection conf = new YamlConfiguration();

        map.forEach((path, obj) -> {
            if (obj instanceof Map) {
                //recursively find sections
                conf.set(path, getSectionFromMap(getMapSection(obj)));
            }
            else {
                conf.set(path, obj);
            }
        });
        return conf;
    }

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


    public static boolean isEmpty(ConfigurationSection conf) {
        return conf == null || conf.getKeys(false).isEmpty();
    }
}
