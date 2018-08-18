package no.kh498.util;

import org.bukkit.configuration.MemorySection;

public interface ConfigurationSectionSavable {


    public MemorySection toYaml();

    void fromYaml(MemorySection conf);
}
