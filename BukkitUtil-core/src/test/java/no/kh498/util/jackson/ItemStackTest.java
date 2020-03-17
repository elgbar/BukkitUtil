package no.kh498.util.jackson;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.Repairable;
import org.junit.Test;

/**
 * @author Elg
 */
public class ItemStackTest extends BukkitSerTestHelper {

    @Test
    public void defaultAirItemStackSerializable() {
        testSer(new ItemStack(Material.AIR));
    }

    @Test
    public void ItemStackWithNameSerializable() {
        ItemStack item = new ItemStack(Material.IRON_AXE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Perun's axe");
        item.setItemMeta(meta);

        testSer(item);
    }

    @Test
    public void moldy_tunic() {
        ItemStack wantedItem = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemMeta meta = wantedItem.getItemMeta();
        meta.setDisplayName("Moldy Tunic");
        ((LeatherArmorMeta) meta).setColor(Color.fromRGB(102, 127, 51));
        ((Repairable) meta).setRepairCost(1);
        wantedItem.setItemMeta(meta);

        testSer(wantedItem);
    }
}
