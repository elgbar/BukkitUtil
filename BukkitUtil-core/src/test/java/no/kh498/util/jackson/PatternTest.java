package no.kh498.util.jackson;

import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Elg
 */
@Ignore
public class PatternTest extends BukkitSerTestHelper {

    @Test
    public void serializable() {
        testSer(new Pattern(DyeColor.CYAN, PatternType.MOJANG));
    }
}
