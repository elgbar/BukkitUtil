package no.kh498.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
   *   The plugin to save from
   * @param resources
   *   The absolute path in plugin jar
   *
   * @throws IOException
   *   If {@link org.apache.commons.io.FileUtils#copyInputStreamToFile(InputStream, File) apache's
   *   copyInputStreamToFile} does
   * @throws IOException
   *   if {@link FileUtils#createDatafolderFile(Plugin, String...)} does
   */
  public static void saveDefaultResources(@NotNull Plugin plugin, @NotNull String... resources) throws IOException {
    saveDefaultResources(plugin, true, resources);
  }

  /**
   * Load all default files from the plugin's jar and place them in the datafolder.
   *
   * @param plugin
   *   The plugin to save from
   * @param create
   *   If the resource does not exist in the jar an empty file should be created
   * @param resources
   *   The absolute path in plugin jar
   *
   * @throws FileNotFoundException
   *   If the file is not found (and create is false)
   * @throws IOException
   *   If {@link org.apache.commons.io.FileUtils#copyInputStreamToFile(InputStream, File) apache's
   *   copyInputStreamToFile} does
   * @throws IOException
   *   if {@link FileUtils#createDatafolderFile(Plugin, String...)} does
   */
  public static void saveDefaultResources(@NotNull Plugin plugin, boolean create, @NotNull String... resources) throws IOException {
    saveDefaultResources(plugin, plugin.getClass(), create, resources);
  }

  /**
   * Load all default files from the plugin's jar and place them in the datafolder.
   *
   * @param plugin
   *   The plugin to save from
   * @param resources
   *   The absolute path in plugin jar
   * @param create
   *   If the resource does not exist in the jar an empty file should be created
   *
   * @throws FileNotFoundException
   *   If the file is not found (and create is false)
   * @throws IOException
   *   If {@link org.apache.commons.io.FileUtils#copyInputStreamToFile(InputStream, File) apache's
   *   copyInputStreamToFile} does
   * @throws IOException
   *   if {@link FileUtils#createDatafolderFile(Plugin, String...)} does
   */
  public static void saveDefaultResources(@NotNull Plugin plugin, @NotNull Class<?> resourceJar, boolean create, @NotNull String... resources)
  throws IOException {
    for (String resource : resources) {
      if (resource == null) {
        logger.error("One of the resource given was null!");
        continue;
      }
      else if (resource.isEmpty()) {
        logger.warn("Cannot save a resource with an empty path");
        continue;
      }
      //split the given string by '/' and '\'
      String[] path = resource.split("[/\\\\]");
      if (FileUtils.getDatafolderFile(plugin, path).exists()) {
        //file already exists so it shouldn't be overwritten
        continue;
      }

      InputStream is = FileUtils.getInternalFileStream(resourceJar, path);

      if (create || is != null) {
        File outFile = FileUtils.createDatafolderFile(plugin, path);
        if (is != null) {
          org.apache.commons.io.FileUtils.copyInputStreamToFile(is, outFile);
        }
      }
      else {
        throw new FileNotFoundException("Failed to find the file '" + resource + "' in plugin " + plugin.getName());
      }
    }
  }


  /**
   * A warning will be printed if the given YAML is invalid.
   *
   * @return A FileConfiguration from the given file or {@code null} if invalid yaml or no file found found
   */
  @Nullable
  public static FileConfiguration getYaml(@NotNull Plugin plugin, String... filename) {
    return getYaml(FileUtils.getDatafolderFile(plugin, filename));
  }

  /**
   * A warning will be printed if the given YAML is invalid.
   *
   * @return A FileConfiguration from the given file or {@code null} if invalid yaml or no file found found
   */
  @Nullable
  public static FileConfiguration getYaml(@NotNull File file) {
    try {
      YamlConfiguration conf = new YamlConfiguration();
      conf.load(file);
      return conf;
    } catch (InvalidConfigurationException e) {
      logger.warn("YAML in file '{}' is invalid.\n{}", file, e.getMessage());
    } catch (FileNotFoundException e) {
      logger.debug("Failed to find given file '{}'", file.getPath());
    } catch (IOException e) {
      logger.debug("An IO exception occurred when trying to load file '{}'", file.getPath());
    }
    return null;
  }

  /**
   * A warning will be printed if the given YAML is invalid.
   *
   * @return A FileConfiguration of the given file or the default argument if the YAML in the file is invalid or an
   * {@link IOException} occurred
   */
  @NotNull
  public static FileConfiguration getYamlOrDefault(@NotNull Plugin plugin, @NotNull FileConfiguration def, String... filename) {
    return getYamlOrDefault(FileUtils.getDatafolderFile(plugin, filename), def);
  }

  /**
   * A warning will be printed if the given YAML is invalid.
   *
   * @return A FileConfiguration of the given file or the default argument if the YAML in the file is invalid or an
   * {@link IOException} occurred
   */
  @NotNull
  public static FileConfiguration getYamlOrDefault(@NotNull File file, @NotNull FileConfiguration def) {
    FileConfiguration conf = getYaml(file);
    return conf == null ? def : conf;
  }


  /**
   * Save a FileConfiguration to the datafolder of a plugin
   *
   * @return If the config was saved successfully
   */
  public static boolean saveYaml(@NotNull Plugin plugin, FileConfiguration conf, String... savePath) {
    return saveYaml(conf, FileUtils.getDatafolderFile(plugin, savePath));
  }

  /**
   * @return If the config was saved successfully
   */
  public static boolean saveYaml(@Nullable ConfigurationSection conf, @Nullable File file) {
    if (file == null || conf == null) {
      logger.error("Failed to save Yaml. Got invalid parameters: conf = '{}' file = = '{}'", conf, file);
      return false;
    }
    try {
      toFileConf(conf).save(file);
    } catch (IOException e) {
      logger.error("Failed to save file '{}' to '{}'", file.getName(), file.getPath(), e);
      return false;
    }
    return true;
  }

  /**
   * @param conf
   *   The config to load the values from
   * @param path
   *   The path to section to get
   *
   * @return A map of all nodes at the given path, if an error occurred an empty map will be returned
   */
  @NotNull
  public static Map<String, Object> getMapSection(@NotNull ConfigurationSection conf, @NotNull String path) {
    return getMapSection(get(conf, path));
  }

  /**
   * @param obj
   *   The object to get the map from. NOTE: this must be a {@link ConfigurationSection} or a
   *   {@link Map}{@code <String, Object>} in order to return something else than an empty map
   *
   * @return A map of all nodes at the given path, if an error occurred an empty map will be returned
   */
  @NotNull
  public static Map<String, Object> getMapSection(@Nullable Object obj) {
    if (obj == null) { return new HashMap<>(); }
    try {
      ConfigurationSection section = (ConfigurationSection) obj;
      return section.getValues(true);
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

  /**
   * @param conf
   *   to check
   *
   * @return If the given conf is {@code null} or has no keys
   */
  public static boolean isEmpty(@Nullable ConfigurationSection conf) {
    return conf == null || conf.getKeys(false).isEmpty();
  }

  /**
   * @param conf
   *   The conf to convert
   *
   * @return A YamlConfiguration version of the given conf
   */
  @NotNull
  public static FileConfiguration toFileConf(@NotNull ConfigurationSection conf) {
    //it's already a file config so a simple cast will do it
    if (conf instanceof FileConfiguration) return (FileConfiguration) conf;

    FileConfiguration fileConf = new YamlConfiguration();
    for (Map.Entry<String, Object> entry : getMapSection(conf, "").entrySet()) {
      fileConf.set(entry.getKey(), entry.getValue());
    }
    return fileConf;
  }

  /**
   * @param conf
   *   The config to convert
   *
   * @return YAML string version of the given config
   */
  public static String saveToString(@NotNull ConfigurationSection conf) {
    return toFileConf(conf).saveToString();
  }

  /**
   * Sugar syntax for
   *
   * <pre>
   * {@code
   * YamlConfiguration conf = new YamlConfiguration();
   * conf.loadFromString(yaml);}
   * </pre>
   *
   * @return A {@link YamlConfiguration} presentation of the given string
   */
  @NotNull
  public static YamlConfiguration loadFromString(@NotNull String yaml) throws InvalidConfigurationException {
    YamlConfiguration conf = new YamlConfiguration();
    conf.loadFromString(yaml);
    return conf;
  }

  /**
   * @return A {@link YamlConfiguration} presentation of the given string, or {@code null} if it failed to load
   */
  @Nullable
  public static YamlConfiguration loadFromStringOrNull(@NotNull String yaml) {
    try {
      return loadFromString(yaml);
    } catch (InvalidConfigurationException e) {
      logger.warn("Given YAML is invalid.\n{}", e.getMessage());
    }
    return null;
  }

  /**
   * The configuration
   * <pre>
   * {@code
   * parent:
   *   key: true
   *   child:
   *     value: 1
   *     sibling: 2
   *   list:
   *     - hello
   *     - world
   *     - another_list:
   *       - wow: true
   *   emptyList: []}
   * </pre>
   * <p>
   * Will result in the following set
   * {@code { "parent.key", "parent.child.value", "parent.child.sibling", "parent.list.0", "parent.list.1",
   * "parent.list.2.another_list.0.wow", "parent.empty_list" }}
   * <p>
   * <p>
   * Note that default implementation does not support list index keys.
   *
   * @return A set of all existing paths within the given configuration and any of its decedents that is not a
   * ConfigurationSection
   */
  public static Set<String> flatKeys(@NotNull ConfigurationSection conf) {
    HashSet<String> paths = new HashSet<>();

    for (String key : conf.getKeys(false)) {
      //ok to use normal get
      flatKeys(conf.get(key), key, paths);
    }

    return paths;
  }

  private static void flatKeys(@NotNull Object object, String currentPath, Set<String> paths) {
    if (object instanceof ConfigurationSection) {
      //Parse the config as map then to reduce duplicate code
      flatKeys(getMapSection((ConfigurationSection) object, ""), currentPath, paths);
    }
    else if (object instanceof Map) {
      //noinspection unchecked Just gotta assume
      for (Map.Entry<String, Object> entry : ((Map<String, Object>) object).entrySet()) {
        flatKeys(entry.getValue(), currentPath + "." + entry.getKey(), paths);
      }
    }
    else if (object instanceof Iterable) {
      Iterator<?> iterator = ((Iterable<?>) object).iterator();

      if (!iterator.hasNext()) {
        //there are no elements in this iterable, so we add it as a path
        paths.add(currentPath);
        return;
      }

      int index = 0;
      while (iterator.hasNext()) {
        Object any = iterator.next();
        flatKeys(any, currentPath + "." + index, paths);

        //update index last
        index++;
      }
    }
    else {
      paths.add(currentPath);
    }
  }

  /**
   * An improved {@link ConfigurationSection#get(String)} that includes support for generic types and list
   * indices. Note that any maps will be returned as {@link ConfigurationSection}.
   *
   * @param conf
   *   The config to get the value from
   * @param path
   *   The path to the value
   * @param <T>
   *   Type of value to get
   *
   * @return The value stored at {@code path} in {@code conf}, or {@code null} if not found or wrong type
   *
   * @deprecated Throws ClassCastException when wrong type. Use {@link #get(ConfigurationSection, String, Class)} instead
   */
  @Nullable
  @Deprecated
  public static <T> T get(@NotNull ConfigurationSection conf, @NotNull String path) {
    return get(conf, path, (T) null);
  }


  /**
   * An improved {@link ConfigurationSection#get(String, Object)} that includes support for generic types and list
   * indices. Note that any maps will be returned as {@link ConfigurationSection}.
   *
   * @param conf
   *   The config to get the value from
   * @param path
   *   The path to the value
   * @param <T>
   *   Type of value to get
   *
   * @return The value stored at {@code path} in {@code conf}, or {@code fallback} if not found or wrong type
   */
  @Nullable
  public static <T> T get(@NotNull ConfigurationSection conf, @NotNull String path, @NotNull Class<T> tClass) {
    return get(conf, path, (T) null, tClass);
  }


  /**
   * An improved {@link ConfigurationSection#get(String, Object)} that includes support for generic types and list
   * indices. Note that any maps will be returned as {@link ConfigurationSection}.
   *
   * @param conf
   *   The config to get the value from
   * @param path
   *   The path to the value
   * @param fallback
   *   The fallback value if either a value is not found or it is of the wrong type
   * @param <T>
   *   Type of value to get
   *
   * @return The value stored at {@code path} in {@code conf}, or {@code fallback} if not found or wrong type
   *
   * @deprecated Throws ClassCastException when wrong type. Use {@link #getWithFallback(ConfigurationSection, String, Object)} or {@link
   * #get(ConfigurationSection, String, Object, Class)} instead
   */
  @Nullable
  @Contract("_,_,!null->!null")
  @Deprecated
  public static <T> T get(@NotNull ConfigurationSection conf, @NotNull String path, @Nullable T fallback) {
    return get0(conf, path, fallback, conf.getRoot().options().pathSeparator(), null);
  }


  /**
   * An improved {@link ConfigurationSection#get(String, Object)} that includes support for generic types and list
   * indices. Note that any maps will be returned as {@link ConfigurationSection}.
   *
   * @param conf
   *   The config to get the value from
   * @param path
   *   The path to the value
   * @param fallback
   *   The fallback value if either a value is not found or it is of the wrong type
   * @param <T>
   *   Type of value to get
   *
   * @return The value stored at {@code path} in {@code conf}, or {@code fallback} if not found or wrong type
   */
  @NotNull
  public static <T> T getWithFallback(@NotNull ConfigurationSection conf, @NotNull String path, @NotNull T fallback) {
    return get0(conf, path, fallback, conf.getRoot().options().pathSeparator(), null);
  }


  /**
   * An improved {@link ConfigurationSection#get(String, Object)} that includes support for generic types and list
   * indices. Note that any maps will be returned as {@link ConfigurationSection}.
   *
   * @param conf
   *   The config to get the value from
   * @param path
   *   The path to the value
   * @param fallback
   *   The fallback value if either a value is not found or it is of the wrong type
   * @param <T>
   *   Type of value to get
   *
   * @return The value stored at {@code path} in {@code conf}, or {@code fallback} if not found or wrong type
   */
  @Nullable
  @Contract("_,_,!null,_->!null")
  public static <T> T get(@NotNull ConfigurationSection conf, @NotNull String path, @Nullable T fallback, @NotNull Class<T> tClass) {
    return get0(conf, path, fallback, conf.getRoot().options().pathSeparator(), tClass);
  }


  @Nullable
  @Contract("_,_,!null,_,_->!null")
  private static <T> T get0(@NotNull Object object, @NotNull String path, @Nullable T fallback, char separator, @Nullable Class<T> tClass) {
    if (path.isEmpty()) {

      //We want a config section, but the returned object might be a map
      if ((tClass != null && ConfigurationSection.class.isAssignableFrom(tClass) || fallback instanceof ConfigurationSection) && object instanceof Map) {
        //noinspection unchecked
        return (T) getSection(object);
      }
      return checkedCorrectClass(object, fallback, tClass);
    }
    if (object instanceof ConfigurationSection) {
      ConfigurationSection conf = ((ConfigurationSection) object);
      int dotIndex = path.indexOf(separator);
      if (dotIndex == -1) {
        //ok to use normal get as we are in a config section
        Object newObject = conf.get(path, fallback);
        return checkedCorrectClass(newObject, fallback, tClass);
      }
      String key = path.substring(0, dotIndex);
      String restKey = path.substring(dotIndex + 1);
      Object any = conf.get(key);
      return get0(any, restKey, fallback, separator, tClass);
    }
    else if (object instanceof Map) {
      return get0(getSection(object), path, fallback, separator, tClass);
    }
    else if (object instanceof List) {
      int dotIndex = path.indexOf(separator);

      String key = (dotIndex == -1) ? path : path.substring(0, dotIndex);

      int index;
      try {
        index = Integer.parseInt(key);
      } catch (NumberFormatException e) {
        return fallback;
      }
      Object any = ((List<?>) object).get(index);
      String restKey = path.substring(dotIndex + 1);
      return get0(any, (dotIndex == -1) ? "" : restKey, fallback, separator, tClass);
    }
    else {
      return get0(object, "", fallback, separator, tClass);
    }
  }

  private static <T> T checkedCorrectClass(@Nullable Object object, @Nullable T fallback, @Nullable Class<T> tClass) {
    if (object == null) return null;
    if (tClass == null) {
      if (fallback != null && !fallback.getClass().isInstance(object) && !object.getClass().isInstance(fallback)) {
        return fallback;
      }
    }
    else if (!tClass.isAssignableFrom(object.getClass())) {
      //given object is not an instance of T class
      return fallback;
    }

    //noinspection unchecked We cannot check if this value is correct
    return (T) object;
  }

  /**
   * Set difference between the two configurations. If a path exists in both configurations it will not exist in the
   * returned set.
   * <p>
   * If the config contains a list, it will be replaced with a {@link ConfigurationSection} where the keys are the
   * indices of the value.
   *
   * <p>
   * <b>Example</b>
   * <p>
   * Given {@code conf A}:
   * <pre>
   * {@code
   * parent:
   *   key: true
   *   child:
   *    - common: 2
   *    - value: 1}
   * </pre>
   * <p>
   * and {@code conf B}:
   * <pre>
   * {@code
   * parent:
   *    key: false
   *    child:
   *     - common: 2
   *     - sibling: 2}
   * </pre>
   * <p>
   * will return
   *
   * <pre>
   * {@code
   * parent:
   *    child:
   *     1:
   *       sibling: 2
   *       value: 1}
   * </pre>
   *
   * @return Difference between the first and second config
   */
  @NotNull
  public static ConfigurationSection diff(@NotNull ConfigurationSection confA, @NotNull ConfigurationSection confB) {
    Set<String> pathsA = flatKeys(confA);
    Set<String> pathsB = flatKeys(confB);

    Set<String> diffA = new HashSet<>(flatKeys(confA));
    diffA.removeAll(pathsB);

    Set<String> diffB = new HashSet<>(flatKeys(confB));
    diffB.removeAll(pathsA);

    ConfigurationSection conf = new YamlConfiguration();
    for (String path : diffA) {
      conf.set(path, ConfigUtil.get(confA, path));
    }

    for (String path : diffB) {
      conf.set(path, ConfigUtil.get(confB, path));
    }

    return conf;
  }
}
