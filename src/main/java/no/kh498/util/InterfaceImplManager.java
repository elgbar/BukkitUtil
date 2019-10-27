package no.kh498.util;

import com.google.common.base.Preconditions;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * This utility class is responsible for holding references to different implementations of an interface. It is
 * intended to hold all known implementation of an interface. It is useful when you want to write implementations where
 * there might be dependencies that add their own implementation. For example a kit plugin can have multiple kits, if an
 * independent wanted to add a kit they could use either {@link #registerClass(Class)} or {@link
 * #registerFromPackage(Plugin, String)} to let the plugin know of its implementation.
 *
 * <h2>Usage</h2>
 *
 * <ol>
 * <li>The constructor takes an interface as its argument (it <b>MUST</b> be an interface)</li>
 * <li>Let the instance know of different implementation either directly with {@link #registerClass(Class)} or by
 * scanning a package
 * with{@link
 * #registerFromPackage(Plugin, String)} </li>
 * <li>
 * To use the registered classes you can use
 * <ol>
 *
 * <li>Get a list of all know implementations with {@link #getImplementations()}</li>
 * <li>Get an instance of one of the implementations with {@link #getInstance(String)}</li>
 * </ol>
 * </li>
 * </ol>
 *
 *
 * <p>
 * To register more classes you can do so either directly with {@link #registerClass(Class)} or by scanning a package
 * with {@link #registerFromPackage(Plugin, String)}
 *
 * @param <I>
 *     The interface that this class will hold a reference to
 */
public class InterfaceImplManager<I> {

    public static Logger logger = LoggerFactory.getLogger(InterfaceImplManager.class);
    @NotNull
    private final Class<I> interfaceClass;

    private Map<String, Class<? extends I>> parts;


    /**
     * @throws IllegalArgumentException
     *     If the {@code clazz} is not an interface
     */
    public InterfaceImplManager(@NotNull Class<I> clazz) {
        Preconditions.checkArgument(Modifier.isInterface(clazz.getModifiers()), "The type class must be an interface");
        interfaceClass = clazz;
        parts = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    /**
     * Notify the existence of all classes at the {@code plugin}s {@code package path}
     * <p>
     * Scan classes in this path and look for classes implementing {@link I}
     */
    public void registerFromPackage(@NotNull Plugin plugin, String packagePath) {
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
            logger.debug("Registering {} out of {} {} subclasses from package {}", registered, subTypes.size(),
                         interfaceClass.getSimpleName(), packagePath);
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
    public boolean registerClass(@Nullable Class<? extends I> clazz) {
        if (clazz == null) {
            logger.error("Cannot register a null class");
            return false;
        }
        else if (parts.containsValue(clazz)) {
            logger.warn("Tried to register the class " + clazz.getSimpleName() + " twice");
            return false;
        }
        else if (clazz.getAnnotations().length > 0 && Arrays.stream(clazz.getAnnotations()).anyMatch(
            annotation -> annotation.getClass() == IgnoreImplementation.class)) {
            logger
                .debug("Did not register " + clazz.getSimpleName() + " as IgnoreImplementation annotation is present");
            return false;
        }
        else if (Modifier.isAbstract(clazz.getModifiers())) {
            logger.debug("Did not register " + clazz.getSimpleName() + " as it is abstract");
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
    @Nullable
    public I getInstance(@Nullable String simpleName) {
        if (simpleName == null || !parts.containsKey(simpleName)) {
            return null;
        }

        try {
            return parts.get(simpleName).getConstructor().newInstance();
        } catch (@NotNull InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Failed to create a new instance of '" + simpleName +
                                       "'. The constructor must not contain any arguments and must be public");
        }
    }

    /**
     * @return A copy of the internal map
     */
    @NotNull
    public Map<String, Class<? extends I>> getImplementations() {
        return Collections.unmodifiableMap(parts);
    }

    @NotNull
    public Class<I> getInterfaceClass() {
        return interfaceClass;
    }
}
