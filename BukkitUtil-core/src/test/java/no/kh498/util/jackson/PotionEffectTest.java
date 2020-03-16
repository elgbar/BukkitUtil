package no.kh498.util.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.bukkit.Color;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Elg
 */
@Ignore
public class PotionEffectTest extends BukkitSerTestHelper {

    @Test
    public void serializeLocation() {

        String json;
        PotionEffect pot = new PotionEffect(PotionEffectType.CONFUSION, Integer.MAX_VALUE, 10, true, true, Color.RED);
        try {
            json = mapper.writeValueAsString(pot);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
            return;
        }
        System.out.println(pot.serialize());
        System.out.println(json);

        PotionEffect read;
        try {
            read = mapper.readValue(json, PotionEffect.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
            return;
        }

        assertEquals(pot, read);
    }
}
