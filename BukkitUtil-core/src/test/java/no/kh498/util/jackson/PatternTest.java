package no.kh498.util.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Elg
 */
public class PatternTest extends BukkitSerTestHelper {

    @Test
    public void BlockVectorSerializable() {
        String json;
        Pattern item = new Pattern(DyeColor.CYAN, PatternType.MOJANG);
        try {
            json = mapper.writeValueAsString(item);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
            return;
        }
        System.out.println(item.serialize());
        System.out.println(json);

        Pattern is;
        try {
            is = mapper.readValue(json, Pattern.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
            return;
        }

        assertEquals(item, is);
    }
}
