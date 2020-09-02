package no.kh498.util

import no.kh498.util.itemMenus.api.ItemMenu
import no.kh498.util.itemMenus.api.ItemMenuList
import no.kh498.util.itemMenus.items.mc13.StaticColoredPaneItem
import org.bukkit.DyeColor.BLACK
import org.bukkit.DyeColor.GRAY

/**
 * @author Elg
 */
object BukkitUtil {
  /**
   * Fix all known breaking changes for BukkitUtil when targeting version 1.13 or above
   */
  @JvmStatic
  fun fixBukkitUtil() {
    fixDefaultMenuItems()
  }

  /**
   * Fix default [no.kh498.util.itemMenus.api.MenuItem] being `null` instead of the pre 1.13 materials
   *
   * @see ItemMenu.EMPTY_SLOT_ITEM
   *
   * @see ItemMenuList.DEFAULT_BOTTOM_PANE
   */
  @JvmStatic
  fun fixDefaultMenuItems() {
    ItemMenuList.DEFAULT_BOTTOM_PANE = StaticColoredPaneItem(BLACK)
    ItemMenu.EMPTY_SLOT_ITEM = StaticColoredPaneItem(GRAY)
  }
}
