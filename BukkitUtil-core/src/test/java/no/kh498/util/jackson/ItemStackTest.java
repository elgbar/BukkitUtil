package no.kh498.util.jackson;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.Repairable;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Elg
 */
public class ItemStackTest extends BukkitSerTestHelper {

    @Test
    public void itemStackNoMetaSerializable() {
        testSer(new ItemStack(Material.AIR, 10, (short) 2));
    }

    @Test
    public void ItemStackWithSimpleMetaSerializable() {
        ItemStack item = new ItemStack(Material.IRON_AXE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Perun's axe");
        meta.setLore(Arrays.asList("epic lore!", "wow much old memes"));
        ((Repairable) meta).setRepairCost(10);
        item.setItemMeta(meta);

        testSer(item);
    }

    @Test
    public void ItemStackWithUnspesificFullMetaSerializable() {
        ItemStack item = new ItemStack(Material.IRON_AXE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Perun's axe");
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.setLore(Arrays.asList("epic lore!", "wow much old memes"));
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        ((Repairable) meta).setRepairCost(10);
        item.setItemMeta(meta);

        testSer(item);
    }

    @Test
    public void LeatherMetaWithColorSerializable() {
        ItemStack wantedItem = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemMeta meta = wantedItem.getItemMeta();
        meta.setDisplayName("Moldy Tunic");
        ((LeatherArmorMeta) meta).setColor(Color.fromRGB(102, 127, 51));
        ((Repairable) meta).setRepairCost(1);
        wantedItem.setItemMeta(meta);

        testSer(wantedItem);
    }
}
