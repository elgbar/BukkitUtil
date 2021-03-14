package no.kh498.util.itemMenus.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Passes inventory correctNPCRightClick events to their menus for handling.
 */
public class ItemMenuListener implements Listener {

    private static final ItemMenuListener INSTANCE = new ItemMenuListener();
    @Nullable
    private Plugin plugin;

    private ItemMenuListener() {}

    /**
     * Gets the ItemMenuListener instance.
     *
     * @return The ItemMenuListener instance.
     */
    @NotNull
    public static ItemMenuListener getInstance() {
        return INSTANCE;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public static void onInventoryClick(final InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player && event.getInventory().getHolder() != null && event.getInventory().getHolder() instanceof ItemMenuHolder) {
            event.setCancelled(true);
            ((ItemMenuHolder) event.getInventory().getHolder()).getMenu().onInventoryClick(event);
        }
    }

    /**
     * Registers the events of the ItemMenuListener to a plugin.
     *
     * @param plugin
     *     The plugin used to register the events.
     */
    public void register(@NotNull final Plugin plugin) {
        if (!isRegistered(plugin)) {
            plugin.getServer().getPluginManager().registerEvents(INSTANCE, plugin);
            this.plugin = plugin;
        }
    }

    /**
     * Checks if the ItemMenuListener is registered to a plugin.
     *
     * @param plugin
     *     The plugin.
     *
     * @return True if the ItemMenuListener is registered to the plugin, else false.
     */
    public boolean isRegistered(@NotNull final Plugin plugin) {
        if (plugin.equals(this.plugin)) {
            for (final RegisteredListener listener : HandlerList.getRegisteredListeners(plugin)) {
                if (listener.getListener().equals(INSTANCE)) {
                    return true;
                }
            }
        }
        return false;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPluginDisable(@NotNull final PluginDisableEvent event) {
        if (event.getPlugin().equals(plugin)) {
            closeOpenMenus();
            plugin = null;
        }
    }

    /**
     * Closes all {@link ItemMenu}s currently open.
     */
    public static void closeOpenMenus() {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (player.getOpenInventory() != null) {
                final Inventory inventory = player.getOpenInventory().getTopInventory();
                if (inventory.getHolder() instanceof ItemMenuHolder) {
                    player.closeInventory();
                }
            }
        }
    }
}
