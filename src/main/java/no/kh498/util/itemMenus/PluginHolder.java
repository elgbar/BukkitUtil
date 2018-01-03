package no.kh498.util.itemMenus;

import org.bukkit.plugin.Plugin;

/**
 * Holds a plugin instance, when using item menus a {@link #setPlugin(Plugin)} must be called with a non-null
 * variable for the library to work as expected.
 *
 * @author karl henrik
 */
public class PluginHolder {

    private static Plugin plugin;

    /**
     * @return The plugin held by PluginHolder
     *
     * @throws IllegalStateException
     *     If {@link #setPlugin(Plugin)} has not been called before this method
     */
    public static Plugin getPlugin() {
        if (plugin == null) {
            throw new IllegalStateException("Plugin needs to be set to use this library.");
        }
        return plugin;
    }

    /**
     * Initiate the plugin holder, this can only be done once.
     *
     * @param plugin
     *     The plugin instance to be saved
     *
     * @throws IllegalStateException
     *     If the plugin has already been set
     * @throws IllegalArgumentException
     *     If the argument is null
     */
    public static void setPlugin(final Plugin plugin) {
        if (PluginHolder.plugin != null) {
            throw new IllegalStateException("Plugin has already been set and cannot be changed.");
        }
        else if (plugin == null) {
            throw new IllegalArgumentException("The plugin cannot be null");
        }
        PluginHolder.plugin = plugin;
    }
}
