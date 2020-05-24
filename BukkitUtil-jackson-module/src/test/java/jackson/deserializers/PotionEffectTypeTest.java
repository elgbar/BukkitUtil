package jackson.deserializers;

import org.bukkit.potion.PotionEffectType;
import org.junit.Test;

/**
 * @author Elg
 */
public class PotionEffectTypeTest extends BukkitSerTestHelper {

    @Test
    public void serialize() {
        testSerAll(PotionEffectType.CONFUSION);
    }
}
