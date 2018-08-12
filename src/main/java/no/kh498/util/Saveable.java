package no.kh498.util;

import org.bukkit.plugin.Plugin;

/**
 * Save something somewhere
 */
public interface Saveable {

    /**
     * Called when a plugin wants to save and on {@link Plugin#onDisable()}
     */
    void save();

    /**
     * Called when a plugin wants to read changes from disk and on {@link Plugin#onEnable()}
     */
    void load();
}
