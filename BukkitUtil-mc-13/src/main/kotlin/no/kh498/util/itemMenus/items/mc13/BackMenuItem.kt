package no.kh498.util.itemMenus.items.mc13

import no.kh498.util.itemMenus.api.items.StaticMenuItem
import no.kh498.util.itemMenus.events.ItemClickEvent
import org.bukkit.ChatColor.RED
import org.bukkit.Material.OAK_FENCE_GATE
import org.bukkit.inventory.ItemStack

/**
 * A [StaticMenuItem] that opens the [ItemMenu]'s parent menu if it exists.
 *
 *
 * Note that this is a 1.13+ item
 *
 * @see no.kh498.util.itemMenus.items.BackMenuItem
 */
class BackMenuItem : StaticMenuItem("${RED}Back", ItemStack(OAK_FENCE_GATE)) {
  override fun onItemClick(event: ItemClickEvent) {
    event.setWillGoBack(true)
  }
}
