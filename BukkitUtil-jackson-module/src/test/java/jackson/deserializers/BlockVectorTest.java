package jackson.deserializers;

import org.bukkit.util.BlockVector;
import org.junit.Test;

/**
 * @author Elg
 */
public class BlockVectorTest extends BukkitSerTestHelper {

    @Test
    public void BlockVectorSerializable() {
        testSer(new BlockVector(2f, 1f, -1.5f));
    }
}
