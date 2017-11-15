package no.kh498.util;

import com.google.common.base.Preconditions;
import no.kh498.util.log.Logger;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * @since 0.1.0
 */
public class FileUtils {

    /**
     * @param plugin
     *     The plugin
     *
     * @return Get the absolute path of the plugins folder
     */
    public static String getPluginsFolder(final Plugin plugin) {
        return plugin.getDataFolder().getAbsolutePath();
    }

    /**
     * Saves the contents of an InputStream in a file.
     *
     * @param in
     *     The InputStream to read from. This stream will not be closed when this method returns.
     * @param file
     *     The file to save to. Will be replaced if it exists, or created if it doesn't.
     */
    public static void save(final Plugin plugin, final InputStream in, final File file) throws IOException {
        Preconditions.checkNotNull(plugin, "Plugin cannot be null");
        Logger.setPluginIfNotSet(plugin);
        if (!file.getParentFile().isDirectory() && !file.getParentFile().mkdirs()) {
            Logger.severe("Failed to create the parent folder '" + file.getParentFile().toString() + "'");
            return;
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            final byte[] buffer = new byte[16 * 1024];
            int read;
            while ((read = in.read(buffer)) > 0) {
                out.write(buffer, 0, read);
            }
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * @param plugin
     *     The plugin that writes the json
     * @param subPath
     *     Path from /plugins/{$plugin_name}/
     * @param fileName
     *     File name of the file (no ending)
     * @param JsonString
     *     JsonString Valid Json string (Use Gson)
     */
    public static void writeJSON(final Plugin plugin, final String subPath, final String fileName,
                                 final String JsonString) {
        Preconditions.checkNotNull(plugin, "Plugin cannot be null");
        Preconditions.checkNotNull(subPath, "subPath cannot be null");
        Preconditions.checkNotNull(fileName, "fileName cannot be null");
        Logger.setPluginIfNotSet(plugin);
        try {
            final File file =
                new File(getPluginsFolder(plugin) + File.separator + subPath + File.separator + fileName + ".json");
            final File filePath = new File(getPluginsFolder(plugin) + File.separator + subPath);
            if (!filePath.isDirectory() && !filePath.mkdirs()) {
                Logger.severe("Failed to create folder for '" + filePath.toString() + "'");
                return;
            }

            if (!file.exists()) {
                if (!file.createNewFile()) {
                    Logger.severe("Failed to create files in '" + filePath.toString() + "'");
                    return;
                }
            }
            final FileWriter fileWriter = new FileWriter(file);

            fileWriter.write(JsonString);

            fileWriter.flush();
            fileWriter.close();
        } catch (final Exception e) {
            Logger.severe("Failed to write the json");
        }
    }

    /**
     * @param plugin
     *     The plugin that reads the json
     * @param subPath
     *     Path from /plugins/{$plugin_name}/
     * @param fileName
     *     File name of the file
     * @param addEnding
     *     If the method should add an ending eg "file" becomes "file.json"
     */
    public static String readJSON(final Plugin plugin, final String subPath, final String fileName,
                                  final Boolean addEnding) {
        Preconditions.checkNotNull(plugin, "Plugin cannot be null");
        Preconditions.checkNotNull(subPath, "subPath cannot be null");
        Preconditions.checkNotNull(fileName, "fileName cannot be null");
        Logger.setPluginIfNotSet(plugin);
        try {
            final File file = new File(getPluginsFolder(plugin) + File.separator + subPath + File.separator + fileName +
                                       (addEnding ? ".json" : ""));
            final File filePath = new File(getPluginsFolder(plugin) + File.separator + subPath);

            if (!filePath.isDirectory() && !filePath.mkdirs()) {
                Logger.severe("Failed to create folder for '" + filePath.toString() + "'");
                return null;
            }

            if (!file.exists()) {
                Logger.info("Could not find the files to read, is this the first time you load this plugin?");
                return null;
            }
            return org.apache.commons.io.FileUtils.readFileToString(file, Charset.defaultCharset());
        } catch (final IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param subPath
     *     Path from /plugins/{$plugin_name}/
     *
     * @return Get a list of the names of all the files in the {@code subPath}
     */
    public static ArrayList<String> getFileNames(final Plugin plugin, final String subPath) {
        Preconditions.checkNotNull(plugin, "Plugin cannot be null");
        Preconditions.checkNotNull(subPath, "subPath cannot be null");
        final File folder = new File(getPluginsFolder(plugin) + File.separator + subPath);
        final File[] listOfFiles = folder.listFiles();
        final ArrayList<String> listOfFileName = new ArrayList<>();

        if (listOfFiles != null) {
            for (final File file : listOfFiles) {
                if (file.isFile()) {
                    listOfFileName.add(file.getName());
                }
            }
        }
        return listOfFileName;
    }
}