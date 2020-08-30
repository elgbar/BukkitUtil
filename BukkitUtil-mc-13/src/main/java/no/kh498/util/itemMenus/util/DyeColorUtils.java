package no.kh498.util.itemMenus.util;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Elg
 */
public class DyeColorUtils {

  private DyeColorUtils() {}

  /**
   * Convert cye color into stained glass pane material of the same color. If {@code color} is {@code null}, then
   * unstained glass pane is returned.
   */
  @NotNull
  public static Material dyeColorToPaneMaterial(@Nullable final DyeColor color) {
    if (color == null) return Material.GLASS_PANE;
    switch (color) {
      case WHITE:
        return Material.WHITE_STAINED_GLASS_PANE;
      case ORANGE:
        return Material.ORANGE_STAINED_GLASS_PANE;
      case MAGENTA:
        return Material.MAGENTA_STAINED_GLASS_PANE;
      case LIGHT_BLUE:
        return Material.LIGHT_BLUE_STAINED_GLASS_PANE;
      case YELLOW:
        return Material.YELLOW_STAINED_GLASS_PANE;
      case LIME:
        return Material.LIME_STAINED_GLASS_PANE;
      case PINK:
        return Material.PINK_STAINED_GLASS_PANE;
      case GRAY:
        return Material.GRAY_STAINED_GLASS_PANE;
      case LIGHT_GRAY:
        return Material.LIGHT_GRAY_STAINED_GLASS_PANE;
      case CYAN:
        return Material.CYAN_STAINED_GLASS_PANE;
      case PURPLE:
        return Material.PURPLE_STAINED_GLASS_PANE;
      case BLUE:
        return Material.BLUE_STAINED_GLASS_PANE;
      case BROWN:
        return Material.BROWN_STAINED_GLASS_PANE;
      case GREEN:
        return Material.GREEN_STAINED_GLASS_PANE;
      case RED:
        return Material.RED_STAINED_GLASS_PANE;
      case BLACK:
        return Material.BLACK_STAINED_GLASS_PANE;
      default:
        return Material.GLASS_PANE;
    }

  }

}
