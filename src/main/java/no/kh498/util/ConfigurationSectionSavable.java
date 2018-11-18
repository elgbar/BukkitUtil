package no.kh498.util;

import org.bukkit.configuration.ConfigurationSection;

public interface ConfigurationSectionSavable {


    /**
     * @return this instance as a ConfigurationSection
     */
    ConfigurationSection toConfig();

    /**
     * @param conf
     *     The section to load from
     *
     * @return if the configuration was successfully loaded
     */
    //TODO make this return a List<String> of keys NOT used
    boolean fromConfig(ConfigurationSection conf);
}
