package no.kh498.util;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public interface ConfigurationSectionSavable {

    String ROOT_PATH = File.separator;

    /**
     * @return this instance as a ConfigurationSection or {@code null} if there is nothing to save
     */
    @Nullable
    ConfigurationSection toConfig();

    /**
     * @param conf
     *     The section to load from
     * @param root
     *     The root configuration of {@code conf}
     * @param absolutePath
     *     Path to {@code conf} from {@code root}, if at root the path should be displayed as {@link #ROOT_PATH}
     *
     * @return if the configuration was successfully loaded
     */
    boolean fromConfig(@NotNull ConfigurationSection conf, @NotNull Configuration root, @NotNull String absolutePath);
}
