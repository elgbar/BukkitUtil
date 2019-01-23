package no.kh498.util;

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

/**
 * @author Elg
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class ConfigUtil {

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
            logger.warn("YAML in file '{}' is invalid.\n{}", file, e.getMessage());
            return null;
        } catch (IOException e) {
            logger.error("Failed to find the file '{}'", file);
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
        if (file == null || conf == null) {
            logger.error("Failed to save Yaml. Got invalid parameters: conf = '{}' file = = '{}'", conf, file);
            return;
        }
        try {
            conf.save(file);
        } catch (IOException e) {
            logger.error("Failed to save file '{}' to '{}'", file.getName(), file.getPath());
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

    public static ConfigurationSection getSection(Object obj) {
        if (obj instanceof ConfigurationSection) {
            return (ConfigurationSection) obj;
        }
        return getSectionFromMap(getMapSection(obj));
    }

    /**
     * Convert a map into a configuration section
     */
    public static ConfigurationSection getSectionFromMap(Map<String, Object> map) {
        YamlConfiguration conf = new YamlConfiguration();
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

    public static boolean isEmpty(ConfigurationSection conf) {
        return conf == null || conf.getKeys(false).isEmpty();
    }

    public static FileConfiguration toFileConf(ConfigurationSection conf) {
        FileConfiguration fileConf = new YamlConfiguration();
        for (Map.Entry<String, Object> entry : getMapSection(conf, "").entrySet()) {
            fileConf.set(entry.getKey(), entry.getValue());
        }
        return fileConf;
    }
}
