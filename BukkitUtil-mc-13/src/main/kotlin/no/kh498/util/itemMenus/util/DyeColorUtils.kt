package no.kh498.util.itemMenus.util

import org.bukkit.DyeColor
import org.bukkit.DyeColor.BLACK
import org.bukkit.DyeColor.BLUE
import org.bukkit.DyeColor.BROWN
import org.bukkit.DyeColor.CYAN
import org.bukkit.DyeColor.GRAY
import org.bukkit.DyeColor.GREEN
import org.bukkit.DyeColor.LIGHT_BLUE
import org.bukkit.DyeColor.LIGHT_GRAY
import org.bukkit.DyeColor.LIME
import org.bukkit.DyeColor.MAGENTA
import org.bukkit.DyeColor.ORANGE
import org.bukkit.DyeColor.PINK
import org.bukkit.DyeColor.PURPLE
import org.bukkit.DyeColor.RED
import org.bukkit.DyeColor.WHITE
import org.bukkit.DyeColor.YELLOW
import org.bukkit.Material
import org.bukkit.Material.BLACK_STAINED_GLASS_PANE
import org.bukkit.Material.BLUE_STAINED_GLASS_PANE
import org.bukkit.Material.BROWN_STAINED_GLASS_PANE
import org.bukkit.Material.CYAN_STAINED_GLASS_PANE
import org.bukkit.Material.GLASS_PANE
import org.bukkit.Material.GRAY_STAINED_GLASS_PANE
import org.bukkit.Material.GREEN_STAINED_GLASS_PANE
import org.bukkit.Material.LIGHT_BLUE_STAINED_GLASS_PANE
import org.bukkit.Material.LIGHT_GRAY_STAINED_GLASS_PANE
import org.bukkit.Material.LIME_STAINED_GLASS_PANE
import org.bukkit.Material.MAGENTA_STAINED_GLASS_PANE
import org.bukkit.Material.ORANGE_STAINED_GLASS_PANE
import org.bukkit.Material.PINK_STAINED_GLASS_PANE
import org.bukkit.Material.PURPLE_STAINED_GLASS_PANE
import org.bukkit.Material.RED_STAINED_GLASS_PANE
import org.bukkit.Material.WHITE_STAINED_GLASS_PANE
import org.bukkit.Material.YELLOW_STAINED_GLASS_PANE

/**
 * @author Elg
 */
object DyeColorUtils {
  /**
   * Convert cye color into stained glass pane material of the same color. If `color` is `null`, then
   * unstained glass pane is returned.
   */
  @JvmStatic
  fun dyeColorToPaneMaterial(color: DyeColor?): Material {
    return when (color) {
      WHITE -> WHITE_STAINED_GLASS_PANE
      ORANGE -> ORANGE_STAINED_GLASS_PANE
      MAGENTA -> MAGENTA_STAINED_GLASS_PANE
      LIGHT_BLUE -> LIGHT_BLUE_STAINED_GLASS_PANE
      YELLOW -> YELLOW_STAINED_GLASS_PANE
      LIME -> LIME_STAINED_GLASS_PANE
      PINK -> PINK_STAINED_GLASS_PANE
      GRAY -> GRAY_STAINED_GLASS_PANE
      LIGHT_GRAY -> LIGHT_GRAY_STAINED_GLASS_PANE
      CYAN -> CYAN_STAINED_GLASS_PANE
      PURPLE -> PURPLE_STAINED_GLASS_PANE
      BLUE -> BLUE_STAINED_GLASS_PANE
      BROWN -> BROWN_STAINED_GLASS_PANE
      GREEN -> GREEN_STAINED_GLASS_PANE
      RED -> RED_STAINED_GLASS_PANE
      BLACK -> BLACK_STAINED_GLASS_PANE
      else -> GLASS_PANE
    }
  }
}
