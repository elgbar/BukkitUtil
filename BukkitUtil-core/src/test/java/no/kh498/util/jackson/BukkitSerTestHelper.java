package no.kh498.util.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemFactory;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffectTypeWrapper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * @author Elg
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Bukkit.class, CraftServer.class})
public abstract class BukkitSerTestHelper {

    protected ObjectMapper mapper;
    protected World world;


    @SuppressWarnings("deprecation")
    @BeforeClass
    public static void registerThingsBeforeClass() throws Exception {

        //noinspection unchecked
        Map<Integer, Enchantment> enchIdMap = (Map<Integer, Enchantment>) Whitebox.getField(Enchantment.class, "byId")
                                                                                  .get(null);
        //noinspection unchecked
        Map<String, Enchantment> enchByName = (HashMap<String, Enchantment>) Whitebox.getField(Enchantment.class,
                                                                                               "byName").get(null);

        Enchantment ench = new EnchantmentWrapper(Enchantment.DURABILITY.getId()) {
            //prevent infinite loop
            @Override
            public String getName() {
                return "DURABILITY";
            }
        };

        enchIdMap.put(Enchantment.DURABILITY.getId(), ench);
        enchByName.put(Enchantment.DURABILITY.getName(), ench);
        //these will also create infinite loops, but it's not really necessary to overwrite them, just use Durability
        // when a name is needed
        enchIdMap.put(Enchantment.DIG_SPEED.getId(), Enchantment.DIG_SPEED);
        enchIdMap.put(Enchantment.ARROW_FIRE.getId(), Enchantment.ARROW_FIRE);

        //PotionEffectType must also be registered to be deserialized

        PotionEffectType[] potIdMap = (PotionEffectType[]) Whitebox.getField(PotionEffectType.class, "byId").get(null);
        //noinspection unchecked
        Map<String, PotionEffectType> potNameMap = (Map<String, PotionEffectType>) Whitebox.getField(
            PotionEffectType.class, "byName").get(null);

        PotionEffectType pet = new PotionEffectTypeWrapper(PotionEffectType.CONFUSION.getId()) {
            @Override
            public String getName() {
                return "CONFUSION";
            }

            @Override
            public PotionEffectType getType() {
                return super.getType();
            }
        };

        potIdMap[pet.getId()] = pet;
        potNameMap.put(pet.getName().toLowerCase(), pet);
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

        when(Bukkit.getServer()).thenReturn(PowerMockito.mock(CraftServer.class));
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
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(org);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
            return;
        }
        System.out.println(json);

        T read;
        try {
            //noinspection unchecked
            read = mapper.readValue(json, (Class<T>) org.getClass());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
            return;
        }

        assertEquals(org, read);
    }
}
