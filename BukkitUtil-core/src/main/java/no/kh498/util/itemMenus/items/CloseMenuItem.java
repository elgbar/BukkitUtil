/*
 * This file is part of AmpMenus. Copyright (c) 2014 <http://github.com/ampayne2/AmpMenus/> AmpMenus
 * is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version. AmpMenus is distributed in the hope that it will
 * be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details. You
 * should have received a copy of the GNU Lesser General Public License along with AmpMenus. If not,
 * see <http://www.gnu.org/licenses/>.
 */
package no.kh498.util.itemMenus.items;

import no.kh498.util.itemMenus.api.ItemMenu;
import no.kh498.util.itemMenus.api.items.StaticMenuItem;
import no.kh498.util.itemMenus.events.ItemClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * A {@link StaticMenuItem} that closes the {@link ItemMenu}.
 * <p>
 * Note that this version works on 1.13+
 */
public class CloseMenuItem extends StaticMenuItem {

  public CloseMenuItem() {
    super(ChatColor.RED + "Close", new ItemStack(Material.BARRIER));
  }

  @Override
  public void onItemClick(@NotNull final ItemClickEvent event) {
    event.setWillClose(true);
  }
}
