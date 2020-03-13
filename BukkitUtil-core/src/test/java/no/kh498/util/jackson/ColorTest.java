package no.kh498.util.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.bukkit.Color;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Elg
 */
public class ColorTest extends BukkitSerTestHelper {

    @Test
    public void colorSerializable() {
        String json;
        Color color = Color.GREEN;
        try {
            json = mapper.writeValueAsString(color);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
            return;
        }

        System.out.println(color.serialize());

        System.out.println(json);

        assertEquals("{\"==\":\"Color\",\"RED\":0,\"BLUE\":0,\"GREEN\":128}", json);

        Color is;
        try {
            is = mapper.readValue(json, Color.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
            return;
        }

        assertEquals(Color.GREEN, is);
    }
}
