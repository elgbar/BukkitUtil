package no.kh498.util;

import com.google.common.base.Preconditions;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class is a utility class in the valentine realms quest plugin. It is responsible for holding references to
 * different parts of a quest.
 * <p>
 * When a quest references an objective, reward, precondition or post-cleanup it must also somehow have a reference to
 * it's class. This is the utility that converts a string to the given class.
 * <p>
 * To register more classes you can do so either directly with {@link #registerClass(Class)} or by scanning a package
 * with {@link #registerFromPackage(Plugin, String)}
 *
 * @param <I>
 *     The interface that this class will hold a static reference to
 */
public class InterfaceMap<I> {


    private static final Logger logger = LoggerFactory.getLogger(InterfaceMap.class);
    private final Class<I> interfaceClass;

    private Map<String, Class<? extends I>> parts;


    /**
     * @throws IllegalArgumentException
     *     If the {@code clazz} is not an interface
     */
    public InterfaceMap(Class<I> clazz) {
        Preconditions.checkArgument(Modifier.isInterface(clazz.getModifiers()), "The type class must be an interface");
        interfaceClass = clazz;
        parts = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    /**
     * Scan classes in this path and look for classes implementing {@link I}
     */
    public void registerFromPackage(Plugin plugin, String packagePath) {
        try (ScanResult scanResult = new ClassGraph().addClassLoader(plugin.getClass().getClassLoader())
                                                     .whitelistPackages(packagePath).scan()) {
            List<Class<I>> subTypes =
                scanResult.getClassesImplementing(interfaceClass.getCanonicalName()).loadClasses(interfaceClass, true);
            if (subTypes.isEmpty()) {
                logger.error(
                    "Failed to find any classes that are implementing '{}' in the package '{}'. If you are extending " +
                    "a class that implements {}, you might want to implement the interface directly.",
                    interfaceClass.getSimpleName(), packagePath, interfaceClass.getSimpleName());
                return;
            }

            int registered = 0;
            for (Class<I> type : subTypes) {
                boolean success = registerClass(type);
                if (success) { registered++; }
            }
            logger.debug("Registering {} out of {} {} subclasses", registered, subTypes.size(),
                         interfaceClass.getSimpleName());
        }
    }

    /**
     * Notify the existence of {@code clazz}.
     *
     * @param clazz
     *     The class to register
     *
     * @return if the class was registered or not
     */
    public boolean registerClass(Class<? extends I> clazz) {
        if (clazz == null) {
            logger.error("Cannot register a null class");
            return false;
        }
        else if (Modifier.isAbstract(clazz.getModifiers())) {
            logger.debug("Did not register " + clazz.getSimpleName() + " as it is abstract");
            return false;
        }
        else if (parts.containsValue(clazz)) {
            logger.warn("Tried to register the class " + clazz.getSimpleName() + " twice");
            return false;
        }

        parts.put(clazz.getSimpleName(), clazz);
        logger.trace("Loaded " + interfaceClass.getSimpleName() + " " + clazz.getSimpleName());
        return true;
    }

    /**
     * @param simpleName
     *     The {@link Class#getSimpleName()} to get the class of off
     *
     * @return The class corresponding to the given {@code clazzName}
     */
    public I getInstance(String simpleName) {
        if (simpleName == null || !parts.containsKey(simpleName)) {
            return null;
        }

        try {
            return parts.get(simpleName).getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException
            e) {
            throw new RuntimeException("Failed to create a new instance of '" + simpleName +
                                       "'. The constructor must not contain any arguments and must be public");
        }
    }

    /**
     * @return A copy of the internal map
     */
    public Map<String, Class<? extends I>> getParts() {
        return Collections.unmodifiableMap(parts);
    }

    public Class<I> getInterfaceClass() {
        return interfaceClass;
    }
}
