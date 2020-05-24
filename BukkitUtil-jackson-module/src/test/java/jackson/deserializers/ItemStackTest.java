package jackson.deserializers;

import com.fasterxml.jackson.core.JsonProcessingException;
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

import static org.junit.Assert.*;

/**
 * @author Elg
 */
public class ItemStackTest extends BukkitSerTestHelper {

    @Test
    public void itemStackNoMetaSerializable() {
        testSer(new ItemStack(Material.AIR, 10, (short) 2));
    }

    @Test
    public void unbreakableItemStack() {
        ItemStack item = new ItemStack(Material.IRON_AXE);
        ItemMeta meta = item.getItemMeta();
        assertNotNull(meta);
        meta.spigot().setUnbreakable(true);
        item.setItemMeta(meta);

        testSer(item);
    }

    @Test
    public void ItemStackWithSimpleMetaSerializable() {
        ItemStack item = new ItemStack(Material.IRON_AXE);
        ItemMeta meta = item.getItemMeta();
        assertNotNull(meta);
        meta.setDisplayName("Perun's axe");
        meta.setLore(Arrays.asList("epic lore!", "wow much old memes"));
        ((Repairable) meta).setRepairCost(10);
        item.setItemMeta(meta);

        testSer(item);
    }

    @Test
    public void ItemStackFromHOT() throws JsonProcessingException {
        String json = "{\"==\":\"ItemStack\",\"damage\":0,\"amount\":1,\"meta\":{\"meta-type\":\"UNSPECIFIC\"," +
                      "\"enchants\":null,\"Unbreakable\":true,\"display-name\":null,\"ItemFlags\":null," +
                      "\"repair-cost\":0},\"type\":\"IRON_AXE\"}";
        ItemStack item = mapper.readValue(json, ItemStack.class);
        assertNotNull("Item is null", item);
        assertEquals(item.getType(), Material.IRON_AXE);
        assertNotNull("item meta is null", item.getItemMeta());
        assertTrue("Item not unbreakable", item.getItemMeta().spigot().isUnbreakable());
    }

    @Test
    public void ItemStackFromHOTMinimal() throws JsonProcessingException {
        String json = "{\"==\":\"ItemStack\",\"meta\":{\"meta-type\":\"UNSPECIFIC\",\"Unbreakable\":true}," +
                      "\"type\":\"IRON_AXE\"}";
        ItemStack item = mapper.readValue(json, ItemStack.class);
        assertNotNull("Item is null", item);
        assertEquals(item.getType(), Material.IRON_AXE);
        assertNotNull("item meta is null", item.getItemMeta());
        assertTrue("Item not unbreakable", item.getItemMeta().spigot().isUnbreakable());
    }

    @Test
    public void ItemStackWithUnspecificFullMetaSerializable() {
        ItemStack item = new ItemStack(Material.IRON_AXE);
        ItemMeta meta = item.getItemMeta();
        assertNotNull(meta);
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
        assertNotNull(meta);
        meta.setDisplayName("Moldy Tunic");
        ((LeatherArmorMeta) meta).setColor(Color.fromRGB(102, 127, 51));
        ((Repairable) meta).setRepairCost(1);
        wantedItem.setItemMeta(meta);

        testSer(wantedItem);
    }
}
