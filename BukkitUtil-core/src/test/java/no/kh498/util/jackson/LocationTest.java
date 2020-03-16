package no.kh498.util.jackson;

import org.bukkit.Location;
import org.junit.Test;

/**
 * @author Elg
 */
public class LocationTest extends BukkitSerTestHelper {

    @Test
    public void serializeLocation() {
        testSer(new Location(world, 1, 2, -1, 2.5f, 3.3f));
    }

    @Test
    public void serializeLocationNoPitchYaw() {
        testSer(new Location(world, 1, 2, -1));
    }

    @Test
    public void serializeLocationNoWorld() {
        testSer(new Location(null, 1, 2, -1, 2.5f, 3.3f));
    }

    @Test
    public void serializeLocationNoExtra() {
        testSer(new Location(null, 1, 2, -1));
    }
}
