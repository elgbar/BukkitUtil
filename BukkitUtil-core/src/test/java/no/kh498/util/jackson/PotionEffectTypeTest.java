package no.kh498.util.jackson;

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
