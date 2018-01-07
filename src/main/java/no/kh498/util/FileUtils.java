package no.kh498.util;

import com.google.common.base.Preconditions;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * @since 0.1.0
 */
@SuppressWarnings("WeakerAccess")
public final class FileUtils {

    // For a bukkit implementation you can use https://github.com/rjenkinsjr/slf4bukkit
    private static final Logger LOG = LoggerFactory.getLogger(FileUtils.class);

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

        if (!file.getParentFile().isDirectory() && !file.getParentFile().mkdirs()) {
            LOG.error("Failed to create the parent folder '" + file.getParentFile().toString() + "'");
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
                                 final String JsonString, final boolean addEnding) {
        Preconditions.checkNotNull(plugin, "Plugin cannot be null");
        Preconditions.checkNotNull(subPath, "subPath cannot be null");
        Preconditions.checkNotNull(fileName, "fileName cannot be null");

        BufferedWriter wtr = null;

        try {
            final File file = new File(getPluginsFolder(plugin) + File.separator + subPath + File.separator + fileName +
                                       (addEnding ? ".json" : ""));
            final File filePath = new File(getPluginsFolder(plugin) + File.separator + subPath);
            if (!filePath.isDirectory() && !filePath.mkdirs()) {
                LOG.error("Failed to create folder for '" + filePath.toString() + "'");
                return;
            }

            if (!file.exists()) {
                if (!file.createNewFile()) {
                    LOG.error("Failed to create files in '" + filePath.toString() + "'");
                    return;
                }
            }
            wtr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));

            wtr.write(JsonString);

            wtr.flush();

        } catch (final IOException e) {
            LOG.error("Failed to write the json");
        } finally {
            if (wtr != null) {
                try {
                    wtr.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param plugin
     *     The plugin that reads the json
     * @param subPath
     *     Path from /plugins/{$plugin_name}/
     * @param fileName
     *     File name of the file
     */
    public static String readFileToString(final Plugin plugin, final String subPath, final String fileName) {
        //noinspection deprecation
        return readJSON(plugin, subPath, fileName, false);
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
     *
     * @deprecated The name make it seem like it only reads json files. Instead use
     * {@link #readFileToString(Plugin, String, String)}
     */
    @Deprecated
    public static String readJSON(final Plugin plugin, final String subPath, final String fileName,
                                  final Boolean addEnding) {
        Preconditions.checkNotNull(plugin, "Plugin cannot be null");
        Preconditions.checkNotNull(subPath, "subPath cannot be null");
        Preconditions.checkNotNull(fileName, "fileName cannot be null");
        try {
            final File file = new File(getPluginsFolder(plugin) + File.separator + subPath + File.separator + fileName +
                                       (addEnding ? ".json" : ""));
            final File filePath = new File(getPluginsFolder(plugin) + File.separator + subPath);

            if (!filePath.isDirectory() && !filePath.mkdirs()) {
                LOG.error("Failed to create folder for '" + filePath.toString() + "'");
                return null;
            }

            if (!file.exists()) {
                LOG.warn("Could not find the files to read, is this the first time you load this plugin?");
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