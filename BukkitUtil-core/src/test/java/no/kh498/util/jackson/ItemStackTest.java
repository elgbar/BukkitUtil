package no.kh498.util.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Elg
 */
public class ItemStackTest extends BukkitSerTestHelper {


    @Test
    public void defaultAirItemStackSerializable() {
        String json;
        ItemStack item = new ItemStack(Material.AIR);
//        System.out.println(item.serialize());
        try {
            json = mapper.writeValueAsString(item);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
            return;
        }

        System.out.println(json);
        ItemStack is;
        try {
            is = mapper.readValue(json, ItemStack.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
            return;
        }

        assertEquals(item, is);
    }


    @Test
    public void ItemStackWithNameSerializable() {
        String json;
        ItemStack item = new ItemStack(Material.IRON_AXE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Perun's axe");
        item.setItemMeta(meta);

        System.out.println(item.serialize());

        try {
            json = mapper.writeValueAsString(item);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
            return;
        }

        System.out.println(json);
        ItemStack is;
        try {
            is = mapper.readValue(json, ItemStack.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
            return;
        }

        assertEquals(item, is);
    }


    @Test
    public void ItemStackWithEnchantmentSerializable() {
        String json;
        ItemStack item = new ItemStack(Material.IRON_AXE);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, 15, true);
        item.setItemMeta(meta);

        System.out.println(item.serialize());

        try {
            json = mapper.writeValueAsString(item);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
            return;
        }

        System.out.println(json);
        ItemStack is;
        try {
            is = mapper.readValue(json, ItemStack.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
            return;
        }

        assertEquals(item, is);
    }

    @Test
    public void ItemStackWithSpecificMetadataSerializable() {
        fail("TODO use wool with color or something");
    }
}
