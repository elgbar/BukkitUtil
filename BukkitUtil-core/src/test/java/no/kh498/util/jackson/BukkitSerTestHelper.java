package no.kh498.util.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemFactory;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Map;

import static org.powermock.api.mockito.PowerMockito.when;

/**
 * @author Elg
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Bukkit.class)
public abstract class BukkitSerTestHelper {

    protected ObjectMapper mapper;

    @SuppressWarnings("deprecation")
    @BeforeClass
    public static void registerEnchantmentsBeforeClass() throws Exception {

        //noinspection unchecked
        Map<Integer, Enchantment> idMap = (Map<Integer, Enchantment>) Whitebox.getField(Enchantment.class, "byId").get(
            null);

        idMap.put(Enchantment.DURABILITY.getId(), new EnchantmentWrapper(Enchantment.DURABILITY.getId()) {
            //prevent infinite loop
            @Override
            public String getName() {
                return "DURABILITY";
            }
        });
        //these will also create infinite loops, but it's not really necessary to overwrite them, just use Durability
        // when a name is needed
        idMap.put(Enchantment.DIG_SPEED.getId(), Enchantment.DIG_SPEED);
        idMap.put(Enchantment.ARROW_FIRE.getId(), Enchantment.ARROW_FIRE);
    }

    @Before
    public void fixItemFactory() throws Exception {
        PowerMockito.mockStatic(Bukkit.class);
        when(Bukkit.getItemFactory()).thenReturn(
            (ItemFactory) Whitebox.getField(CraftItemFactory.class, "instance").get(null));
    }

    @Before
    public void before() {
        mapper = new ObjectMapper();
        mapper.registerModule(new BukkitModule());
    }
}
