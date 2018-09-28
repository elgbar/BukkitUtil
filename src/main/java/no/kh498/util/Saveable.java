package no.kh498.util;

import org.bukkit.plugin.Plugin;

/**
 * Save something somewhere
 */
public interface Saveable {

    /**
     * Called when a plugin wants to save and on {@link Plugin#onDisable()}
     *
     * @return If this object was saved successfully
     */
    boolean save();

    /**
     * Called when a plugin wants to read changes from disk and on {@link Plugin#onEnable()}
     *
     * @return If this object was loaded successfully
     */
    boolean load();

}
