package no.kh498.util.itemMenus.items;

import no.kh498.util.itemMenus.api.ItemClickHandler;
import no.kh498.util.itemMenus.api.ItemMenu;
import no.kh498.util.itemMenus.api.items.SubMenuItem;
import no.kh498.util.itemMenus.events.ItemClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * Note that this version works on 1.13+
 *
 * @author Elg
 */
public class ComboMenuItem extends SubMenuItem {

  private final ItemClickHandler handler;

  public ComboMenuItem(Plugin plugin, final String displayName, final ItemMenu menu, final ItemClickHandler handler,
                       final ItemStack icon, final String... lore) {
    super(plugin, displayName, menu, icon, lore);
    this.handler = handler;
  }

  @Override
  public void onItemClick(@NotNull final ItemClickEvent event) {
    if (handler != null) {
      handler.onItemClick(event);
    }
    super.onItemClick(event);
  }
}
