package no.kh498.util;

import org.apache.commons.io.IOUtils;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Simplify the writing of IO with this utility. If you are using {@link Configuration} then you might want to take a
 * look at {@link ConfigUtil}
 *
 * @author Elg
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public final class FileUtils {

    // For a bukkit implementation you can use https://github.com/rjenkinsjr/slf4bukkit
    public static Logger logger = LoggerFactory.getLogger(FileUtils.class);


    /////////////
    // Writing //
    /////////////

    /**
     * @param file
     *     The child of the parent dirs to create
     *
     * @return {@code true} if an error occurred when creating the folder
     */
    public static boolean createParentFolders(@NotNull File file) {
        if (file.exists()) { return false; }

        if (file.getParentFile().isDirectory() || file.getParentFile().mkdirs()) {return false;}
        logger.error("Failed to create the parent folder '{}'", file.getParentFile().getPath());
        return true;
    }


    /**
     * If the file does exists, the file will not be overwritten
     *
     * @param plugin
     *     The plugin to use
     * @param children
     *     The relative path to the file
     *
     * @return A file, that exists, at the given path.
     */
    @NotNull
    public static File createDatafolderFile(@NotNull Plugin plugin, @NotNull String... children) throws IOException {
        File file = getDatafolderFile(plugin, children);
        createFileSafely(file);
        return file;
    }

    /**
     * If the file does exists, the file will not be overwritten
     *
     * @param file
     *     the file to create
     */
    public static void createFileSafely(@NotNull File file) throws IOException {
        if (file.exists()) { return; }
        createParentFolders(file);
        //noinspection ResultOfMethodCallIgnored
        file.createNewFile();
    }


    /**
     * Create the file at the given path as a folder (including all its parent folders)
     *
     * @return {@code true} if the folders were successfully created
     */
    public static boolean createFolder(@NotNull Plugin plugin, @NotNull String... children) {
        return createFolderSafely(FileUtils.getDatafolderFile(plugin, children));
    }

    /**
     * @param folder
     *     the folder to create
     *
     * @return {@code true} if the folders were successfully created
     */
    public static boolean createFolderSafely(@NotNull File folder) {
        if (!folder.exists()) {
            return folder.mkdirs();
        }
        else if (!folder.isDirectory()) {
            logger.error("File at '{}' is not a folder.", folder.getPath());
            return false;
        }
        return true;
    }


    /**
     * Write a UTF-8 String to a file in a plugin's datafolder. If the file does not exists, it will be created a long
     * with any of the directories not existing. If it does exists it will be overwritten.
     *
     * @param string
     *     The string to write to the given file
     * @param plugin
     *     The plugin that writes the json
     * @param path
     *     Path from /plugins/{$plugin_name}/
     *
     * @return {@code true} if the content was successfully written to file.
     */
    public static boolean write(@NotNull String string, @NotNull Plugin plugin, @NotNull String... path)
    throws IOException {
        return write(string, createDatafolderFile(plugin, path));

    }

    /**
     * Write a UTF-8 String to a file in a plugin's datafolder. If the file does not exists, it will be created a long
     * with any of the directories not existing. If it does exists it will be overwritten.
     *
     * @param string
     *     The string to write to the given file
     * @param file
     *     The file to write to
     *
     * @return {@code true} if the content was successfully written to file.
     */
    public static boolean write(@NotNull String string, @NotNull File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                logger.error("The given file is a folder '{}'", file.getPath());
                return false;
            }

            if (!file.canWrite()) {
                logger.error("Cannot write the file as the current user does not have reading permission '{}'",
                             file.getPath());
                return false;
            }
        }

        try (BufferedWriter wtr = new BufferedWriter(
            new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            wtr.write(string);
            wtr.flush();
        }
        return true;
    }


    /**
     * Saves the contents of an InputStream in a file.
     *
     * @param in
     *     The InputStream to read from. This stream will not be closed when this method returns.
     * @param plugin
     *     The plugin that saves the InputStream
     * @param path
     *     The relative (to the plugins datafolder) path of the file to save to. It will be replaced if it exists, or
     *     created if it does not
     */
    public static boolean save(@NotNull InputStream in, @NotNull Plugin plugin, @NotNull String... path)
    throws IOException {
        return save(in, createDatafolderFile(plugin, path));
    }

    /**
     * Saves the contents of an InputStream in a file.
     *
     * @param in
     *     The InputStream to read from. This stream will not be closed when this method returns.
     */
    public static boolean save(@NotNull InputStream in, @NotNull File file) {

        try (final FileOutputStream out = new FileOutputStream(file)) {
            final byte[] buffer = new byte[16 * 1024];
            int read;
            while ((read = in.read(buffer)) > 0) {
                out.write(buffer, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    //////////////////////
    // Reading of files //
    //////////////////////


    /**
     * @return A file in a plugin's data folder. If any of the child folder names are
     * null {@code null} is returned
     *
     * @throws NullPointerException
     *     if one of the children is null
     */
    @NotNull
    public static File getDatafolderFile(@NotNull Plugin plugin, @NotNull String... children) {
        try {
            return Paths.get(plugin.getDataFolder().getAbsolutePath(), children).toFile();
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(
                "One of the children given is null. children: " + String.join(File.separator, children));
        }
    }


    /**
     * @param plugin
     *     The plugin that reads the file
     * @param path
     *     Path from /plugins/{$plugin_name}/
     *
     * @return The content of the file read as UTF-8 text or {@code null} if the file does not exist or cannot be read
     */
    @Nullable
    public static String read(@NotNull Plugin plugin, @NotNull String... path) throws IOException {
        return read(getDatafolderFile(plugin, path));
    }

    @Nullable
    public static String read(@NotNull File file) throws IOException {
        if (!file.exists()) {
            logger.error("Failed to find a file at '{}'", file.getPath());
            return null;
        }

        if (file.isDirectory()) {
            logger.error("The given file is a folder '{}'", file.getPath());
            return null;
        }

        if (!file.canRead()) {
            logger.error("Cannot read the file as the current user does not have reading permission for the file '{}'",
                         file.getPath());
            return null;
        }
        return org.apache.commons.io.FileUtils.readFileToString(file, StandardCharsets.UTF_8);
    }


    /**
     * @param plugin
     *     The plugin that has the files
     * @param subPath
     *     Path from /plugins/{$plugin_name}/
     *
     * @return A list of all the files (and folders) in the {@code subPath}, or {@code null} if given subpath does not
     * exists or is a file
     */
    @Nullable
    public static List<File> getFiles(@NotNull Plugin plugin, @NotNull String... subPath) {
        return getFiles(getDatafolderFile(plugin, subPath));
    }

    @Nullable
    public static List<File> getFiles(@NotNull File folder) {
        if (!folder.exists()) {
            logger.warn("There is nothing at the given path '{}'", folder.getPath());
            return null;
        }
        if (!folder.isDirectory()) {
            logger.warn("Given path does not resolve into a folder '{}'", folder.getPath());
            return null;
        }

        //noinspection ConstantConditions folder is checked
        return Arrays.asList(folder.listFiles());
    }

    /**
     * @param plugin
     *     The plugin that has the files
     * @param subPath
     *     Path from /plugins/{$plugin_name}/
     *
     * @return Get a list of the names of all the files in the {@code subPath}, or {@code null} if given subpath does
     * not exists or is a file
     */
    @Nullable
    public static List<String> getFileNames(@NotNull Plugin plugin, @NotNull String... subPath) {

        List<File> files = getFiles(plugin, subPath);
        if (files == null) {
            return null;
        }
        return getFileNames(files);
    }


    /**
     * @return Get a list of the names of all the files in the {@code subPath}, or {@code null} if given subpath does
     * not exists or is a file
     */
    @NotNull
    public static List<String> getFileNames(@NotNull List<File> files) {
        return files.stream().filter(File::isFile).map(File::getName).collect(Collectors.toList());
    }


    /**
     * @param excludeHyphenPrefix
     *     If any files with the hyphen (-) should be excluded from the list
     * @param plugin
     *     The plugin's datafolder to get the file from
     * @param children
     *     The path to the file
     *
     * @return null if any of the children are null, else a list of all non-folder files in the given file.
     */
    @NotNull
    public static List<File> getRecursiveFiles(boolean excludeHyphenPrefix, @NotNull Plugin plugin,
                                               @NotNull String... children) {
        return getRecursiveFiles(excludeHyphenPrefix, getDatafolderFile(plugin, children));
    }

    /**
     * A more user friendly version of {@link #getRecursiveFiles(List, boolean, File)} as you do not  need to create
     * your own list
     * <p>
     * This method uses a {@link LinkedList} as its list as it provides constant time adding. If random access is needed
     * for the files, it is recommended to use the method {@link #getRecursiveFiles(List, boolean, File)} with an {@link
     * ArrayList}
     *
     * @param excludeHyphenPrefix
     *     If any files with the hyphen (-) should be excluded from the list
     * @param file
     *     The file to start at, if this is not a folder it will be the only element
     *
     * @return A list of all non-folder files in the given file.
     */
    @NotNull
    public static List<File> getRecursiveFiles(boolean excludeHyphenPrefix, @NotNull File file) {
        List<File> files = new LinkedList<>();
        getRecursiveFiles(files, excludeHyphenPrefix, file);
        return files;
    }

    /**
     * @param excludeHyphenPrefix
     *     If any files with the hyphen (-) should be excluded from the list
     * @param file
     *     The file to start at, if this is not a folder it will be the only element
     */
    private static void getRecursiveFiles(@NotNull List<File> files, boolean excludeHyphenPrefix, @NotNull File file) {
        if (!file.canRead()) {
            logger.warn("Require reading permissions to read '{}'", file.getPath());
            return;
        }
        else if (excludeHyphenPrefix && file.getName().startsWith("-")) {
            return;
        }
        if (file.isDirectory()) {
            logger.trace("file is a folder '{}'", file.getPath());
            File[] subFiles = file.listFiles();
            if (subFiles == null) {
                logger.error("Failed to get a list of files in folder '{}'", file.getPath());
                return;
            }
            for (File subFile : subFiles) {
                getRecursiveFiles(files, excludeHyphenPrefix, subFile);
            }
        }
        else if (file.isFile()) {
            logger.trace("adding file '{}' to list", file.getPath());
            files.add(file);
        }
    }


    /**
     * Get a file from within the jar.
     * <p>
     * NOTE: If the file cannot be found (ie this returns null) but you're 100% sure the path is correct (see logged
     * path) then you need to shade this class into your jar. See
     * <a href="https://github.com/kh498/BukkitUtil#install">github
     * repo</a> for more information
     *
     * @param absPath
     *     The absolute path to the file within the jar
     *
     * @return The file at {@code absPath} as an InputStream or {@code null} if the file is not found
     */
    @Nullable
    public static InputStream getInternalFileStream(@NotNull String... absPath) {
        return getInternalFileStream(FileUtils.class, absPath);
    }

    /**
     * Get a file from within the jar.
     * <p>
     * NOTE: If the file cannot be found (ie this returns null) but you're 100% sure the path is correct (see logged
     * path) then you need to shade this class into your jar. See
     * <a href="https://github.com/kh498/BukkitUtil#install">github
     * repo</a> for more information
     *
     * @param absPath
     *     The absolute path to the file within the jar
     * @param resourceClazz
     *     The class to load the resource from
     *
     * @return The file at {@code absPath} as an InputStream or {@code null} if the file is not found
     */
    @Nullable
    public static InputStream getInternalFileStream(@NotNull Class<?> resourceClazz, @NotNull String... absPath) {
        String path = "/" + String.join("/", absPath);
        if (logger.isDebugEnabled()) {
            String jar = resourceClazz.
                                          getProtectionDomain().
                                          getCodeSource().
                                          getLocation().
                                          toExternalForm();
            logger.debug("path '{}' jar '{}'", path, jar);
        }
        return resourceClazz.getResourceAsStream(path);
    }


    /**
     * Read a file within the jar to a string
     *
     * @param absPath
     *     The absolute path to the file within the jar
     *
     * @return The UTF-8 String content of the file at {@code absPath} or {@code null} if the file cannot be read or
     * not found
     */
    @Nullable
    public static String readInternalFile(@NotNull String... absPath) {

        InputStream is = getInternalFileStream(absPath);
        if (is == null) {
            logger.debug("Failed to find the internal file");
            return null;
        }
        String content = null;
        try {
            content = IOUtils.toString(is, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        IOUtils.closeQuietly(is);
        return content;
    }

}
