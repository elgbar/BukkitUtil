package no.kh498.util;

import com.google.common.base.Preconditions;
import org.apache.commons.io.IOUtils;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Elg
 * @since 0.1.0
 */
@SuppressWarnings("WeakerAccess")
public final class FileUtils {

    // For a bukkit implementation you can use https://github.com/rjenkinsjr/slf4bukkit
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * Saves the contents of an InputStream in a file.
     *
     * @param plugin
     *     The plugin that saves the InputStream
     * @param in
     *     The InputStream to read from. This stream will not be closed when this method returns.
     * @param file
     *     The file to save to. Will be replaced if it exists, or created if it doesn't.
     *
     * @throws IOException
     *     For the same reasons as {@link FileOutputStream} does
     */
    public static void save(final Plugin plugin, final InputStream in, final File file) throws IOException {
        Preconditions.checkNotNull(plugin, "Plugin cannot be null");

        if (!file.getParentFile().isDirectory() && !file.getParentFile().mkdirs()) {
            logger.error("Failed to create the parent folder '" + file.getParentFile().toString() + "'");
            return;
        }
        try (final FileOutputStream out = new FileOutputStream(file)) {
            final byte[] buffer = new byte[16 * 1024];
            int read;
            while ((read = in.read(buffer)) > 0) {
                out.write(buffer, 0, read);
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
     * @param str
     *     String to write to file
     */
    public static void writeStringToFile(final Plugin plugin, final String subPath, final String fileName,
                                         final String str) {
        writeJSON(plugin, subPath, fileName, str, false);
    }

    /**
     * @param plugin
     *     The plugin that writes the json
     * @param subPath
     *     Path from /plugins/{$plugin_name}/
     * @param fileName
     *     Filename of the file (no ending)
     * @param jsonString
     *     Json string
     * @param addEnding
     *     if a '.json' ending should be appended to the filename
     */
    public static void writeJSON(final Plugin plugin, final String subPath, final String fileName,
                                 final String jsonString, final boolean addEnding) {
        Preconditions.checkArgument(plugin != null, "Plugin cannot be null");
        Preconditions.checkArgument(subPath != null, "subPath cannot be null");
        Preconditions.checkArgument(fileName != null, "fileName cannot be null");
        Preconditions.checkArgument(jsonString != null, "Cannot write a null message to file!");

        BufferedWriter wtr = null;

        try {
            final File file = new File(getPluginsFolder(plugin) + File.separator + subPath + File.separator + fileName +
                                       (addEnding ? ".json" : ""));
            final File filePath = new File(getPluginsFolder(plugin) + File.separator + subPath);
            if (!filePath.isDirectory() && !filePath.mkdirs()) {
                logger.error("Failed to create folder for '" + filePath.toString() + "'");
                return;
            }

            if (!file.exists()) {
                if (!file.createNewFile()) {
                    logger.error("Failed to create files in '" + filePath.toString() + "'");
                    return;
                }
            }
            wtr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));

            wtr.write(jsonString);

            wtr.flush();

        } catch (final IOException e) {
            logger.error("Failed to write the json");
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
     *     The plugin
     *
     * @return Get the absolute path of the plugins folder
     */
    public static String getPluginsFolder(final Plugin plugin) {
        return plugin.getDataFolder().getAbsolutePath();
    }

    /**
     * @param plugin
     *     The plugin that reads the json
     * @param subPath
     *     Path from /plugins/{$plugin_name}/
     * @param fileName
     *     File name of the file
     *
     * @return The content of the file read as UTF-8 text
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
     * @return The content of the file read as UTF-8 text
     *
     * @deprecated The name make it seem like it only reads json files. Instead use
     * {@link #readFileToString(Plugin, String, String)}
     */
    @Deprecated
    public static String readJSON(final Plugin plugin, final String subPath, final String fileName,
                                  final boolean addEnding) {
        Preconditions.checkNotNull(plugin, "Plugin cannot be null");
        Preconditions.checkNotNull(subPath, "subPath cannot be null");
        Preconditions.checkNotNull(fileName, "fileName cannot be null");
        try {
            final File file = new File(getPluginsFolder(plugin) + File.separator + subPath + File.separator + fileName +
                                       (addEnding ? ".json" : ""));
            final File filePath = new File(getPluginsFolder(plugin) + File.separator + subPath);

            if (!filePath.isDirectory() && !filePath.mkdirs()) {
                logger.error("Failed to create folder for '" + filePath.toString() + "'");
                return null;
            }

            if (!file.exists()) {
                logger.warn("Could not find the files to read, is this the first time you load this plugin?");
                return null;
            }
            return org.apache.commons.io.FileUtils.readFileToString(file, Charset.defaultCharset());
        } catch (final IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param plugin
     *     The plugin that reads the json
     * @param subPath
     *     Path from /plugins/{$plugin_name}/
     * @param fileName
     *     File name of the file
     *
     * @return if the file specified exists or not
     */
    public static boolean existFile(final Plugin plugin, final String subPath, final String fileName) {
        Preconditions.checkNotNull(plugin, "Plugin cannot be null");
        Preconditions.checkNotNull(subPath, "subPath cannot be null");
        Preconditions.checkNotNull(fileName, "fileName cannot be null");
        final File file = new File(getPluginsFolder(plugin) + File.separator + subPath + File.separator + fileName);
        return file.exists();
    }

    /**
     * @param plugin
     *     The plugin that has the files
     * @param subPath
     *     Path from /plugins/{$plugin_name}/
     *
     * @return Get a list of the names of all the files in the {@code subPath}
     */
    public static List<String> getFileNames(final Plugin plugin, final String subPath) {
        Preconditions.checkNotNull(plugin, "Plugin cannot be null");
        Preconditions.checkNotNull(subPath, "subPath cannot be null");
        final File folder = new File(getPluginsFolder(plugin), subPath);
        final File[] listOfFiles = folder.listFiles();
        final List<String> listOfFileName = new ArrayList<>();

        if (listOfFiles != null) {
            for (final File file : listOfFiles) {
                if (file.isFile()) {
                    listOfFileName.add(file.getName());
                }
            }
        }
        return listOfFileName;
    }

    /**
     * @param plugin
     *     The plugin that has the files
     * @param subPath
     *     Path from /plugins/{$plugin_name}/
     *
     * @return Get a list of the names of all the files in the {@code subPath}
     */
    public static List<File> getFiles(final Plugin plugin, final String... subPath) {
        Preconditions.checkNotNull(plugin, "Plugin cannot be null");
        Preconditions.checkNotNull(subPath, "subPath cannot be null");
        final File folder = getDatafolderFile(plugin, subPath);
        File[] files = folder.listFiles();
        if (files == null || files.length == 0) { return new ArrayList<>(0); }
        return Arrays.asList(files);
    }

    /**
     * @return A file in a plugin's data folder
     */
    public static File getDatafolderFile(Plugin plugin, String... children) {
        return getDatafolderFile(plugin, Arrays.asList(children));
    }

    public static File getDatafolderFile(Plugin plugin, List<String> children) {
        StringBuilder childrenPath = new StringBuilder();
        for (String child : children) {
            if (child == null) { return null; }
            childrenPath.append(child).append(File.separatorChar);
        }
        return new File(plugin.getDataFolder(), childrenPath.toString());
    }

    /**
     * A more user friendly version of {@link #getRecursiveFiles(File, List, boolean)} as you do not  need to create
     * your own list
     * <p>
     * This method uses a {@link LinkedList} as its list as it provides constant time adding. If random access is needed
     * for the files, it is recommended to use the method {@link #getRecursiveFiles(File, List, boolean)} with an {@link
     * ArrayList}
     *
     * @param file
     *     The file to start at, if this is not a directory it will be the only element
     * @param excludeHyphenPrefix
     *     If any files with the hyphen (-) should be excluded from the list
     *
     * @return A list of all non-directory files in the given file.
     */
    public static List<File> getRecursiveFiles(File file, boolean excludeHyphenPrefix) {
        List<File> files = new LinkedList<>();
        getRecursiveFiles(file, files, excludeHyphenPrefix);
        return files;
    }

    public static List<File> getRecursiveFiles(boolean excludeHyphenPrefix, Plugin plugin, String... children) {
        return getRecursiveFiles(getDatafolderFile(plugin, children), excludeHyphenPrefix);
    }

    /**
     * @param file
     *     The file to start at, if this is not a directory it will be the only element
     * @param excludeHyphenPrefix
     *     If any files with the hyphen (-) should be excluded from the list
     */
    private static void getRecursiveFiles(File file, List<File> files, boolean excludeHyphenPrefix) {
        if (!file.canRead()) {
            logger.info("Did not read '{}', as we have no reading permission", file.getPath());
            return;
        }
        else if (excludeHyphenPrefix && file.getName().startsWith("-")) {
            return;
        }
        if (file.isDirectory()) {
            logger.trace("file is a directory '{}'", file.getPath());
            File[] subFiles = file.listFiles();
            if (subFiles == null) {
                logger.error("Failed to get a list of files in directory '{}'", file.getPath());
                return;
            }
            for (File subFile : subFiles) {
                getRecursiveFiles(subFile, files, excludeHyphenPrefix);
            }
        }
        else if (file.isFile()) {
            logger.trace("adding file '{}' to list", file.getPath());
            files.add(file);
        }
    }

    /**
     * @param internalPath
     *     The path to the file within the jar
     *
     * @return The file at {@code internalPath} or {@code null} if the file cannot be read or not found
     */
    public static String getInternalFileContent(final String internalPath) {
        Preconditions.checkArgument(internalPath != null, "The internal path cannot be null!");

        final InputStream is = getInternalFileStream(internalPath);
        logger.trace("is: " + is);
        String content;
        try {
            content = IOUtils.toString(is, StandardCharsets.UTF_8);
        } catch (final IOException | NullPointerException e) {
            logger.debug("Failed to get internal file due to a " + e.getClass().getSimpleName());
            if (logger.isTraceEnabled()) {
                e.printStackTrace();
            }
            content = null;
        }
        IOUtils.closeQuietly(is);
        return content;
    }

    /**
     * @param internalPath
     *     The path to the file within the jar
     *
     * @return The file at {@code internalPath} as an InputStream or {@code null} if the file is not found
     */
    public static InputStream getInternalFileStream(final String internalPath) {
        Preconditions.checkArgument(internalPath != null, "The internal path cannot be null!");

        //a '/' marks the path as absolute, must be present to search for files from the root of the jar
        final String prefix = internalPath.charAt(0) != '/' ? "/" : "";
        final String absIntPath = prefix + internalPath;

        logger.trace("absIntPath: " + absIntPath);

        return FileUtils.class.getResourceAsStream(absIntPath);
    }

    /**
     * Create a folder with in a plugin's data folder.
     *
     * @return If there were any errors when creating the folder(s)
     */
    public static boolean makeFolder(Plugin plugin, String... children) {
        File convFolder = FileUtils.getDatafolderFile(plugin, children);
        if (!convFolder.exists()) {
            return convFolder.mkdirs();
        }
        else if (!convFolder.isDirectory()) {
            logger.error("File at '{}' is not a directory! No conversations will be loaded.", convFolder.getPath());
            return false;
        }
        return true;
    }
}