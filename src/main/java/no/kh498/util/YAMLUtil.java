package no.kh498.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
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

public class YAMLUtil {

    private static final String WORLD_UID = "WORLD_UID";
    private static final String X = "X";
    private static final String Y = "Y";
    private static final String Z = "Z";
    private static final String YAW = "YAW";
    private static final String PITCH = "PITCH";

    private static final Logger logger = LoggerFactory.getLogger(YAMLUtil.class);

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
     * @return A FileConfiguration from the relative plugin path
     */
    public static FileConfiguration getYaml(Plugin plugin, String... filename) {
        return YamlConfiguration.loadConfiguration(FileUtils.getDatafolderFile(plugin, filename));
    }

    /**
     * Save a FileConfiguration to the datafolder of a plugin
     */
    public static void saveYaml(Plugin plugin, FileConfiguration conf, String savePath) {
        File saveFile = FileUtils.getDatafolderFile(plugin, savePath);
        try {
            conf.save(saveFile);
        } catch (IOException e) {
            logger.error("Failed to save file {} to {}", saveFile.getName(), saveFile.getPath());
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
    public static Map<String, Object> getSection(ConfigurationSection conf, String path) {
        try {
            MemorySection memProp = (MemorySection) conf.get(path);
            return memProp.getValues(false);
        } catch (ClassCastException e1) {
            try {
                //noinspection unchecked
                return (Map<String, Object>) conf.get(path);
            } catch (ClassCastException e) {
                return new HashMap<>();
            }
        }
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
        ConfigurationSection conf = new YamlConfiguration();
        conf.set(WORLD_UID, location.getWorld().getUID());
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
        World world = Bukkit.getWorld(UUID.fromString(conf.getString(WORLD_UID)));
        double x = conf.getDouble(X);
        double y = conf.getDouble(Y);
        double z = conf.getDouble(Z);
        float yaw = (float) conf.getDouble(YAW);
        float pitch = (float) conf.getDouble(PITCH);
        return new Location(world, x, y, z, yaw, pitch);
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
        ConfigurationSection conf = new YamlConfiguration();
        conf.set(WORLD_UID, location.getWorld().getUID());
        conf.set(X, location.getBlockX());
        conf.set(Y, location.getBlockY());
        conf.set(Z, location.getBlockZ());
        return conf;
    }

    /**
     * @return The location saved in {@code conf}
     */
    public static Location blockLocationFromConfig(ConfigurationSection conf) {
        World world = Bukkit.getWorld(UUID.fromString(conf.getString(WORLD_UID)));
        int x = conf.getInt(X);
        int y = conf.getInt(Y);
        int z = conf.getInt(Z);
        return new Location(world, x, y, z);
    }
}
