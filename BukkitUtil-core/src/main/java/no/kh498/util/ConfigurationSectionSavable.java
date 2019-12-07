package no.kh498.util;

import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

public interface ConfigurationSectionSavable {

    /**
     * @return this instance as a ConfigurationSection or {@code null} if there is nothing to save
     */
    @Nullable ConfigurationSection toConfig();
}
