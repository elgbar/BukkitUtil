package no.kh498.util;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Elg
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class ConfigUtil {

    public static Logger logger = LoggerFactory.getLogger(ConfigUtil.class);

    /**
     * Load all default files from the plugin's jar and place them in the datafolder.
     * If the resource does not exist in the jar an empty file will be created
     *
     * @param plugin
     *     The plugin to save from
     * @param resources
     *     The absolute path in plugin jar
     */
    public static void saveDefaultResources(@NotNull Plugin plugin, @NotNull String... resources) throws IOException {
        for (String resource : resources) {
            if (resource == null) {
                logger.error("One of the resource given was null!");
                continue;
            }
            else if (resource.isEmpty()) {
                logger.warn("Cannot save a resource with an empty path");
                continue;
            }
            String[] path = resource.split("[/\\\\]");
            if (FileUtils.getDatafolderFile(plugin, path).exists()) {
                continue;
            }
            File outFile = FileUtils.createDatafolderFile(plugin, path);

            InputStream is = FileUtils.getInternalFileStream(path);
            if (is != null) {
                org.apache.commons.io.FileUtils.copyInputStreamToFile(is, outFile);
            }
        }
    }


    /**
     * @return A FileConfiguration from the relative plugin path or {@code null} if invalid yaml or no file found found
     */
    @Nullable
    public static FileConfiguration getYaml(@NotNull Plugin plugin, String... filename) {
        return getYaml(FileUtils.getDatafolderFile(plugin, filename));
    }

    /**
     * @return A FileConfiguration from the given file or {@code null} if invalid yaml or no file found found
     */
    @Nullable
    public static FileConfiguration getYaml(@NotNull File file) {
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
    public static void saveYaml(@NotNull Plugin plugin, FileConfiguration conf, String... savePath) {
        saveYaml(conf, FileUtils.getDatafolderFile(plugin, savePath));
    }

    public static void saveYaml(@Nullable FileConfiguration conf, @Nullable File file) {
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
    @NotNull
    public static Map<String, Object> getMapSection(@NotNull ConfigurationSection conf, @NotNull String path) {
        return getMapSection(conf.get(path));
    }

    @NotNull
    public static Map<String, Object> getMapSection(@Nullable Object obj) {
        if (obj == null) { return new HashMap<>(); }
        try {
            MemorySection memProp = (MemorySection) obj;
            return memProp.getValues(true);
        } catch (ClassCastException e1) {
            try {
                //noinspection unchecked
                return (Map<String, Object>) obj;
            } catch (ClassCastException e) {
                return new HashMap<>();
            }
        }
    }

    @NotNull
    public static ConfigurationSection getSection(@NotNull Object obj) {
        if (obj instanceof ConfigurationSection) {
            return (ConfigurationSection) obj;
        }
        return getSectionFromMap(getMapSection(obj));
    }

    /**
     * Convert a map into a configuration section
     */
    @NotNull
    public static ConfigurationSection getSectionFromMap(@NotNull Map<String, Object> map) {
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

    public static boolean isEmpty(@Nullable ConfigurationSection conf) {
        return conf == null || conf.getKeys(false).isEmpty();
    }

    @NotNull
    public static FileConfiguration toFileConf(@NotNull ConfigurationSection conf) {
        FileConfiguration fileConf = new YamlConfiguration();
        for (Map.Entry<String, Object> entry : getMapSection(conf, "").entrySet()) {
            fileConf.set(entry.getKey(), entry.getValue());
        }
        return fileConf;
    }
}
