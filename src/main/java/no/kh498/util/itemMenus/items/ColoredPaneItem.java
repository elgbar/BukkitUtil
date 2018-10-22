package no.kh498.util.itemMenus.items;

import no.kh498.util.itemMenus.api.ItemClickHandler;
import no.kh498.util.itemMenus.api.items.ActionMenuItem;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Elg
 * @since 0.1.0
 */
public final class ColoredPaneItem extends ActionMenuItem {

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
    public ColoredPaneItem(final String name, final DyeColor color, final ItemClickHandler handler,
                           final String... lore) {
        //noinspection deprecation
        super(name, handler, new ItemStack(Material.STAINED_GLASS_PANE, 1, color.getDyeData()), lore);
    }

    /**
     * Get a colored pane MenuItem
     *
     * @param name
     *     Name of the item
     * @param color
     *     Color of the item
     * @param lore
     *     Lore of the item
     */
    public ColoredPaneItem(final String name, final DyeColor color, final String... lore) {
        this(name, color, event -> {}, lore);
    }

    /**
     * Get a colored pane MenuItem
     *
     * @param name
     *     Name of the item
     * @param color
     *     Color of the item
     */
    public ColoredPaneItem(final String name, final DyeColor color) {
        this(name, color, event -> {});
    }

    /**
     * Create a filler colored pane that has only a space as name and no lore
     *
     * @param color
     *     Color of the item
     */
    public ColoredPaneItem(final DyeColor color) {
        this(" ", color);
    }
}
