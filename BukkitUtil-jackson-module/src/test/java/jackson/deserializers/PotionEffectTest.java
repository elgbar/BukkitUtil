package jackson.deserializers;

import org.bukkit.Color;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.junit.Test;

/**
 * @author Elg
 */
public class PotionEffectTest extends BukkitSerTestHelper {

    @Test
    public void serialize() {
        testSer(new PotionEffect(PotionEffectType.CONFUSION, Integer.MAX_VALUE, 10, true, true, Color.RED));
    }
}
