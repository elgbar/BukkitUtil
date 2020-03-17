package no.kh498.util.itemMenus.items;

import no.kh498.util.itemMenus.api.items.StaticMenuItem;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Static version of {@link ColoredPaneItem}
 *
 * @author Elg
 * @since 0.1.0
 */
public class StaticColoredPaneItem extends StaticMenuItem {

    /**
     * Create a filler colored pane that has only a space as name and no lore
     *
     * @param color
     *     Color of the item
     */
    public StaticColoredPaneItem(@NotNull final DyeColor color) {
        this(" ", color);
    }

    /**
     * @param name
     *     Name of the item
     * @param color
     *     Color of the item
     * @param lore
     *     Lore of the item
     */
    public StaticColoredPaneItem(final String name, final DyeColor color, final String... lore) {
        super(name, new ItemStack(Material.STAINED_GLASS_PANE, 1, color.getWoolData()), lore);
    }
}
