package no.kh498.util.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.bukkit.Location;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Elg
 */
public class LocationTest extends BukkitSerTestHelper {

    @Test
    public void serializeLocation() {

        String json;
        Location loc = new Location(world, 1, 2, -1, 2.5f, 3.3f);
        try {
            json = mapper.writeValueAsString(loc);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
            return;
        }
        System.out.println(loc.serialize());
        System.out.println(json);

        Location read;
        try {
            read = mapper.readValue(json, Location.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
            return;
        }

        assertEquals(loc, read);
    }
}
