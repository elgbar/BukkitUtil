package no.kh498.util;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

public interface ConfigurationSectionLoadable {

    /**
     * @param conf
     *     The section to load from
     * @param root
     *     The root configuration of {@code conf}
     * @param absolutePath
     *     Path to {@code conf} from {@code root}, if at root the path should be an empty string
     *
     * @return if the configuration was successfully loaded
     */
    boolean fromConfig(@NotNull ConfigurationSection conf, @NotNull Configuration root, @NotNull String absolutePath);

    /**
     * @param root
     *     The root configuration
     *
     * @return The same as {@code fromConfig(root, root, "")}
     */
    default boolean fromConfig(@NotNull Configuration root) {
        return fromConfig(root, root, "");
    }
}
