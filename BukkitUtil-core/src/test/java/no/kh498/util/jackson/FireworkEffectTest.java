package no.kh498.util.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Elg
 */
@Ignore
public class FireworkEffectTest extends BukkitSerTestHelper {

    @Test
    public void BlockVectorSerializable() {
        String json;
        FireworkEffect.Builder feb = FireworkEffect.builder();
        feb.flicker(true);
        feb.trail(true);
        feb.withColor(Color.GREEN, Color.NAVY, Color.AQUA, Color.RED);
        feb.withFade(Color.ORANGE);

        FireworkEffect fe = feb.build();
        try {
            json = mapper.writeValueAsString(fe);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
            return;
        }
        System.out.println(fe.serialize());
        System.out.println(json);

        FireworkEffect is;
        try {
            is = mapper.readValue(json, FireworkEffect.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
            return;
        }

        assertEquals(fe, is);
    }
}
