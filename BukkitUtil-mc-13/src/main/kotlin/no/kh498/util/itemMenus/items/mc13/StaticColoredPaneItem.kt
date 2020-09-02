package no.kh498.util.itemMenus.items.mc13

import no.kh498.util.itemMenus.api.items.StaticMenuItem
import no.kh498.util.itemMenus.util.DyeColorUtils.dyeColorToPaneMaterial
import org.bukkit.DyeColor
import org.bukkit.inventory.ItemStack

/**
 * Static version of [ColoredPaneItem] for minecraft 1.13+
 *
 * @author Elg
 * @see no.kh498.util.itemMenus.items.ColoredPaneItem
 *
 * @since 4.1.1
 */
class StaticColoredPaneItem(name: String, color: DyeColor?, vararg lore: String) : StaticMenuItem(name, ItemStack(dyeColorToPaneMaterial(color)), *lore) {
  /**
   * Create a filler colored pane that has only a space as name and no lore
   *
   * @param color
   * Color of the item
   */
  constructor(color: DyeColor?) : this(" ", color) {}
}
