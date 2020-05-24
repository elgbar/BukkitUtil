package jackson.deserializers;

import org.bukkit.util.Vector;
import org.junit.Test;

/**
 * @author Elg
 */
public class VectorTest extends BukkitSerTestHelper {

    @Test
    public void vectorSerializable() {
        testSer(new Vector(2f, 1f, -1.5f));
    }
}
