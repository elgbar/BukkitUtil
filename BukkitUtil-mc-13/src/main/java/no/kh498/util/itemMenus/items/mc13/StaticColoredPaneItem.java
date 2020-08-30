package no.kh498.util.itemMenus.items.mc13;

import no.kh498.util.itemMenus.api.items.StaticMenuItem;
import no.kh498.util.itemMenus.items.ColoredPaneItem;
import no.kh498.util.itemMenus.util.DyeColorUtils;
import org.bukkit.DyeColor;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Static version of {@link ColoredPaneItem} for minecraft 1.13+
 *
 * @author Elg
 * @see no.kh498.util.itemMenus.items.ColoredPaneItem
 * @since 4.1.1
 */
public class StaticColoredPaneItem extends StaticMenuItem {

  /**
   * Create a filler colored pane that has only a space as name and no lore
   *
   * @param color
   *   Color of the item
   */
  public StaticColoredPaneItem(@Nullable final DyeColor color) {
    this(" ", color);
  }

  /**
   * @param name
   *   Name of the item
   * @param color
   *   Color of the item
   * @param lore
   *   Lore of the item
   */
  public StaticColoredPaneItem(@NotNull final String name, @Nullable final DyeColor color, final String... lore) {
    super(name, new ItemStack(DyeColorUtils.dyeColorToPaneMaterial(color)), lore);
  }
}
