package no.kh498.util.itemMenus.api.items;

import no.kh498.util.itemMenus.api.ItemClickHandler;
import no.kh498.util.itemMenus.api.MenuItem;
import no.kh498.util.itemMenus.events.ItemClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * @author Elg
 */
public class ActionMenuItem extends MenuItem {

    private ItemClickHandler handler;

    public ActionMenuItem(final String displayName, final ItemClickHandler handler, final ItemStack icon,
                          final String... lore) {
        super(displayName, icon, lore);
        this.handler = handler;
    }

    public ActionMenuItem(final String displayName, final ItemClickHandler handler, final ItemStack icon,
                          final List<String> lore) {
        super(displayName, icon, lore);
        this.handler = handler;
    }

    @Override
    public void onItemClick(final ItemClickEvent event) {
        handler.onItemClick(event);
    }

    public ItemClickHandler getHandler() {
        return handler;
    }

    public void setHandler(final ItemClickHandler handler) {
        this.handler = handler;
    }
}
