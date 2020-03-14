package no.kh498.util.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import no.kh498.util.jackson.serializers.bean.ConfigurationSerializableSerializer;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.Repairable;
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
    public void moldy_tunic() {
        String itemString = "\"" + ConfigurationSerializableSerializer.ROOT_PATH + ": \\n" + //
                            "  ==: org.bukkit.inventory.ItemStack \\n" + //
                            "  type: LEATHER_CHESTPLATE \\n" + //
                            "  meta: \\n" + //
                            "    ==: ItemMeta \\n" + //
                            "    meta-type: LEATHER_ARMOR \\n" + //
                            "    display-name: Moldy Tunic \\n" + //
                            "    repair-cost: 1 \\n" + //
                            "    color: \\n" + //
                            "      ==: Color \\n" + //
                            "      RED: 102 \\n" + //
                            "      BLUE: 51 \\n" + //
                            "      GREEN: 127\"";
        ItemStack is;
        try {
            is = mapper.readValue(itemString, ItemStack.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
            return;
        }

        ItemStack wantedItem = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemMeta meta = wantedItem.getItemMeta();
        meta.setDisplayName("Moldy Tunic");
        ((LeatherArmorMeta) meta).setColor(Color.fromRGB(102, 127, 51));
        ((Repairable) meta).setRepairCost(1);
        wantedItem.setItemMeta(meta);

        assertEquals(wantedItem, is);

    }
}
