package no.kh498.util;

import org.bukkit.configuration.ConfigurationSection;

public interface ConfigurationSectionSavable {


    ConfigurationSection toYaml();

    void fromYaml(ConfigurationSection conf);
}
