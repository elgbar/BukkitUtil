package no.kh498.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class YAMLUtil {

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
    public static FileConfiguration getYaml(Plugin plugin, String filename) {
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
}
