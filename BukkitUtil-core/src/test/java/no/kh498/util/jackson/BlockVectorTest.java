package no.kh498.util.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.bukkit.util.BlockVector;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Elg
 */
public class BlockVectorTest extends BukkitSerTestHelper {

    @Test
    public void BlockVectorSerializable() {
        String json;
        BlockVector item = new BlockVector(2f, 1f, -1.5f);
        try {
            json = mapper.writeValueAsString(item);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
            return;
        }
        System.out.println(item.serialize());

        System.out.println(json);

        assertEquals("{\"==\":\"BlockVector\",\"x\":2.0,\"y\":1.0,\"z\":-1.5}", json);

        BlockVector is;
        try {
            is = mapper.readValue(json, BlockVector.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
            return;
        }

        assertEquals(item, is);
    }
}
