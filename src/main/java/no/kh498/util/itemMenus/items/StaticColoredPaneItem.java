package no.kh498.util.itemMenus.items;

import no.kh498.util.itemMenus.api.items.StaticMenuItem;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Static version of {@link ColoredPaneItem}
 *
 * @author Elg
 * @since 0.1.0
 */
public final class StaticColoredPaneItem extends StaticMenuItem {

    /**
     * Create a filler colored pane that has only a space as name and no lore
     *
     * @param color
     *     Color of the item
     */
    public StaticColoredPaneItem(final DyeColor color) {
        this(" ", color);
    }

    /**
     * @param name
     *     Name of the item
     * @param handler
     *     What the items does when you click on it
     * @param color
     *     Color of the item
     * @param lore
     *     Lore of the item
     */
    public StaticColoredPaneItem(final String name, final DyeColor color, final String... lore) {
        super(name, new ItemStack(Material.STAINED_GLASS_PANE, 1, color.getWoolData()), lore);
    }
}
