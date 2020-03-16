package no.kh498.util.jackson;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.junit.Test;

/**
 * @author Elg
 */
public class FireworkEffectTest extends BukkitSerTestHelper {

    @Test
    public void serializable() {
        FireworkEffect.Builder feb = FireworkEffect.builder();
        feb.flicker(true);
        feb.trail(true);
        feb.withColor(Color.GREEN, Color.NAVY, Color.AQUA, Color.RED);
        feb.withFade(Color.ORANGE);

        testSer(feb.build());
    }
}
