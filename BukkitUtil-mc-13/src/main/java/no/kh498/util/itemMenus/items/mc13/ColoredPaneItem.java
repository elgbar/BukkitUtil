package no.kh498.util.itemMenus.items.mc13;

import no.kh498.util.itemMenus.api.ItemClickHandler;
import no.kh498.util.itemMenus.api.items.ActionMenuItem;
import no.kh498.util.itemMenus.util.DyeColorUtils;
import org.bukkit.DyeColor;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Action version of {@link no.kh498.util.itemMenus.items.ColoredPaneItem} for minecraft 1.13+
 *
 * @author Elg
 * @see no.kh498.util.itemMenus.items.ColoredPaneItem
 * @since 4.1.1
 */
public class ColoredPaneItem extends ActionMenuItem {

  /**
   * @param name
   *   Name of the item
   * @param handler
   *   What the items does when you click on it
   * @param color
   *   Color of the item
   * @param lore
   *   Lore of the item
   */
  public ColoredPaneItem(final String name, final DyeColor color, final ItemClickHandler handler,
                         final String... lore) {
    super(name, handler, new ItemStack(DyeColorUtils.dyeColorToPaneMaterial(color)), lore);
  }

  /**
   * Create a filler colored pane that has only a space as name and no lore
   *
   * @param color
   *   Color of the item
   */
  public ColoredPaneItem(@NotNull final DyeColor color, final ItemClickHandler handler) {
    this(" ", color, handler);
  }
}
