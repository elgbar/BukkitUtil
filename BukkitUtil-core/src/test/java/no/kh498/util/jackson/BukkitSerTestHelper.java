package no.kh498.util.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemFactory;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.potion.PotionEffectType;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * @author Elg
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Bukkit.class)
public abstract class BukkitSerTestHelper {

    protected ObjectMapper mapper;
    protected World world;


    @SuppressWarnings("deprecation")
    @BeforeClass
    public static void registerThingsBeforeClass() throws Exception {

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

        //PotionEffectType must also be registered to be deserialized

        PotionEffectType[] potEffTypesIdMap = (PotionEffectType[]) Whitebox.getField(PotionEffectType.class, "byId")
                                                                           .get(null);

        potEffTypesIdMap[PotionEffectType.CONFUSION.getId()] = PotionEffectType.CONFUSION;
    }

    @Before
    public void fixBukkitMethods() throws Exception {

        world = Mockito.mock(World.class);
        Mockito.when(world.getName()).thenReturn("World");
        Mockito.when(world.getUID()).thenReturn(UUID.nameUUIDFromBytes(new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10}));

        PowerMockito.mockStatic(Bukkit.class);
        when(Bukkit.getItemFactory()).thenReturn(
            (ItemFactory) Whitebox.getField(CraftItemFactory.class, "instance").get(null));

        when(Bukkit.getWorld(Mockito.anyString())).thenReturn(world);
        when(Bukkit.getWorld(Mockito.any(UUID.class))).thenReturn(world);
    }

    @Before
    public void before() {
        mapper = new ObjectMapper();
        mapper.registerModule(new BukkitModule());
    }


    public <T extends ConfigurationSerializable> void testSer(T org) {

        try {
            System.out.println(org.serialize());
        } catch (Throwable ignored) { }
        testSerAll(org);
    }

    public <T> void testSerAll(T org) {
        String json;
        try {
            json = mapper.writeValueAsString(org);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
            return;
        }
        System.out.println(json);

        T read;
        try {
            read = mapper.readValue(json, (Class<T>) org.getClass());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
            return;
        }

        assertEquals(org, read);
    }
}
