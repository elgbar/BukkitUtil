package no.kh498.util.itemMenus.items.mc13

import no.kh498.util.itemMenus.api.ItemClickHandler
import no.kh498.util.itemMenus.api.items.ActionMenuItem
import no.kh498.util.itemMenus.util.DyeColorUtils.dyeColorToPaneMaterial
import org.bukkit.DyeColor
import org.bukkit.inventory.ItemStack

/**
 * Action version of [no.kh498.util.itemMenus.items.ColoredPaneItem] for minecraft 1.13+
 *
 * @author Elg
 * @see no.kh498.util.itemMenus.items.ColoredPaneItem
 *
 * @since 4.1.1
 */
class ColoredPaneItem
/**
 * @param name
 * Name of the item
 * @param handler
 * What the items does when you click on it
 * @param color
 * Color of the item
 * @param lore
 * Lore of the item
 */
(name: String, color: DyeColor?, handler: ItemClickHandler?, vararg lore: String) :
    ActionMenuItem(name, handler, ItemStack(dyeColorToPaneMaterial(color)), *lore) {
  /**
   * Create a filler colored pane that has only a space as name and no lore
   *
   * @param color
   * Color of the item
   */
  constructor(color: DyeColor, handler: ItemClickHandler?) : this(" ", color, handler)
}
