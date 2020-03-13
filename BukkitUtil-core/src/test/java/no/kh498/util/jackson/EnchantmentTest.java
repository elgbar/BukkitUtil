package no.kh498.util.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.bukkit.enchantments.Enchantment;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Elg
 */
public class EnchantmentTest extends BukkitSerTestHelper {

    @Test
    public void serializeEnchantment() {
        String json;
        Enchantment enchantment = Enchantment.DURABILITY;
        try {
            json = mapper.writeValueAsString(enchantment);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
            return;
        }

        System.out.println(json);
        Enchantment is;
        try {
            is = mapper.readValue(json, Enchantment.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
            return;
        }

        assertEquals(enchantment, is);
    }

    @Test
    public void serializeMapEnchantment() {
        String json;
        Map<Enchantment, String> emap = new HashMap<>();
        emap.put(Enchantment.ARROW_FIRE, "on fire!");
        emap.put(Enchantment.DIG_SPEED, "Digging it!");
        try {
            json = mapper.writeValueAsString(emap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
            return;
        }

        TypeReference<HashMap<Enchantment, String>> typeRef = new TypeReference<HashMap<Enchantment, String>>() { };

        System.out.println(json);
        Map<Enchantment, String> is;
        try {
            is = mapper.readValue(json, typeRef);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
            return;
        }

        for (Map.Entry<Enchantment, String> entry : is.entrySet()) {
            Enchantment readE = entry.getKey();


            assertEquals(emap.get(entry.getKey()), entry.getValue());
        }
    }

}
