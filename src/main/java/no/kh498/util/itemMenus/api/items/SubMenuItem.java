package no.kh498.util.itemMenus.api.items;

import no.kh498.util.itemMenus.api.ItemMenu;
import no.kh498.util.itemMenus.api.MenuItem;
import no.kh498.util.itemMenus.events.ItemClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

/**
 * A {@link ItemMenu} that opens a sub {@link ItemMenu}.
 */
public class SubMenuItem extends MenuItem {

    private final Plugin plugin;
    private final ItemMenu menu;

    public SubMenuItem(Plugin plugin, final String displayName, final ItemMenu menu, final ItemStack icon,
                       final String... lore) {
        super(displayName, icon, lore);
        this.plugin = plugin;
        this.menu = menu;
    }

    @Override
    public void onItemClick(final ItemClickEvent event) {
        event.setWillClose(true);
        final UUID ID = event.getPlayer().getUniqueId();
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            final Player p = Bukkit.getPlayer(ID);
            if (p != null && menu != null) {
                menu.open(p);
            }
        }, 2L);
    }
}
