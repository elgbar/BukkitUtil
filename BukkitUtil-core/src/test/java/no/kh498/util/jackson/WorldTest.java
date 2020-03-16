package no.kh498.util.jackson;

import org.junit.Test;

/**
 * @author Elg
 */
public class WorldTest extends BukkitSerTestHelper {

    @Test
    public void serialize() {
        testSerAll(world);
    }
}
