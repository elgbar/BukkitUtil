package no.kh498.util.itemMenus.items;

import no.kh498.util.itemMenus.api.ItemMenu;
import no.kh498.util.itemMenus.api.items.StaticMenuItem;
import no.kh498.util.itemMenus.events.ItemClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * A {@link StaticMenuItem} that opens the {@link ItemMenu}'s
 * parent menu if it exists.
 */
public class BackMenuItem extends StaticMenuItem {

    public BackMenuItem() {
        super(ChatColor.RED + "Back", new ItemStack(Material.FENCE_GATE));
    }

    @Override
    public void onItemClick(final ItemClickEvent event) {
        event.setWillGoBack(true);
    }
}
