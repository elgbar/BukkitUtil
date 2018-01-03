package no.kh498.util.itemMenus.api;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

/**
 * Allows you to set the {@link MenuItem} that created the Inventory as the Inventory's
 * holder.
 */
public class ItemMenuHolder implements InventoryHolder {

    private final ItemMenu menu;
    private final Inventory inventory;

    public ItemMenuHolder(final ItemMenu menu, final Inventory inventory) {
        this.menu = menu;
        this.inventory = inventory;
    }

    /**
     * Gets the {@link MenuItem} holding the Inventory.
     *
     * @return The {@link MenuItem} holding the Inventory.
     */
    public ItemMenu getMenu() {
        return this.menu;
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
}
