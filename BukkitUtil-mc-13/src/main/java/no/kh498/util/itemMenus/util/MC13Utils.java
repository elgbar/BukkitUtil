package no.kh498.util.itemMenus.util;

import no.kh498.util.itemMenus.api.ItemMenu;
import no.kh498.util.itemMenus.api.ItemMenuList;
import org.bukkit.DyeColor;

/**
 * @author Elg
 */
public class MC13Utils {

  private MC13Utils() {}

  public static void setDefaultMenuItems() {
    ItemMenuList.DEFAULT_BOTTOM_PANE = new no.kh498.util.itemMenus.items.mc13.StaticColoredPaneItem(DyeColor.BLACK);
    ItemMenu.EMPTY_SLOT_ITEM = new no.kh498.util.itemMenus.items.mc13.StaticColoredPaneItem(DyeColor.GRAY);
  }
}
