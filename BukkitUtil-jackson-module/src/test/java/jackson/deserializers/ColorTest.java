package jackson.deserializers;

import org.bukkit.Color;
import org.junit.Test;

/**
 * @author Elg
 */
public class ColorTest extends BukkitSerTestHelper {

    @Test
    public void colorSerializable() {
        testSer(Color.WHITE);
    }
}
