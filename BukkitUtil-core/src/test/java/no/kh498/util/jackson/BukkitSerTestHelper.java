package no.kh498.util.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.craftbukkit.v1_x_Ry.JacksonMockServer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffectTypeWrapper;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.powermock.reflect.Whitebox;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Elg
 */
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

    @After
    public void tearDown() throws Exception {
        JacksonMockServer.INSTANCE.reload();
    }

    @Before
    public void before() {
        mapper = new ObjectMapper();
        mapper.registerModule(new BukkitModule());
    }


    public <T extends ConfigurationSerializable> void testSer(T org) {

        try {
            System.out.println("bukkit ser=" + org.serialize());
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
        System.out.println("jackson ser=" + json);

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
