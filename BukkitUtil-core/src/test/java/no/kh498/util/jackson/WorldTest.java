package no.kh498.util.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.bukkit.World;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Elg
 */
public class WorldTest extends BukkitSerTestHelper {

    @Test
    public void serialize() {

        String json;
        try {
            json = mapper.writeValueAsString(world);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
            return;
        }
//        System.out.println(world.serialize());
        System.out.println(json);

        World read;
        try {
            read = mapper.readValue(json, World.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
            return;
        }

        assertEquals(world, read);
    }
}
