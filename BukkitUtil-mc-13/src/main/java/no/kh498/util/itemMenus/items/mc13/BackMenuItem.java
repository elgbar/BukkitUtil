package no.kh498.util.itemMenus.items.mc13;

import no.kh498.util.itemMenus.api.ItemMenu;
import no.kh498.util.itemMenus.api.items.StaticMenuItem;
import no.kh498.util.itemMenus.events.ItemClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * A {@link StaticMenuItem} that opens the {@link ItemMenu}'s parent menu if it exists.
 * <p>
 * Note that this is a 1.13+ item
 *
 * @see no.kh498.util.itemMenus.items.BackMenuItem
 */
public class BackMenuItem extends StaticMenuItem {

  public BackMenuItem() {
    super(ChatColor.RED + "Back", new ItemStack(Material.OAK_FENCE_GATE));
  }

  @Override
  public void onItemClick(@NotNull final ItemClickEvent event) {
    event.setWillGoBack(true);
  }
}
