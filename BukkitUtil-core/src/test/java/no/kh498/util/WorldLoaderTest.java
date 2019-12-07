package no.kh498.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.UUID;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * @author Elg
 */
@PrepareForTest(Bukkit.class)
@RunWith(PowerMockRunner.class)
public class WorldLoaderTest {

    @Before
    public void setUp() {
        PowerMockito.mockStatic(Bukkit.class);
    }


    /////////////////////
    // worldFromConfig //
    /////////////////////


    @Test
    public void worldFromConfValidName() {
        YamlConfiguration conf = new YamlConfiguration();
        conf.set(WorldLoader.WORLD_NAME, "test");
        when(Bukkit.getWorld("test")).thenReturn(mock(World.class));

        World w = WorldLoader.worldFromConfig(conf, false);
        Assert.assertNotNull(w);
    }

    @Test
    public void worldFromConfValidUUID() {
        YamlConfiguration conf = new YamlConfiguration();

        UUID uuid = UUID.randomUUID();
        conf.set(WorldLoader.WORLD_UID, uuid.toString());
        when(Bukkit.getWorld(uuid)).thenReturn(mock(World.class));

        World w = WorldLoader.worldFromConfig(conf, true);
        Assert.assertNotNull(w);
    }

    @Test
    public void worldFromConfInValidName() {
        YamlConfiguration conf = new YamlConfiguration();

        conf.set(WorldLoader.WORLD_NAME, "aa");
        World w = WorldLoader.worldFromConfig(conf, true);
        Assert.assertNull(w);
    }

    @Test
    public void worldFromConfInValidUUID() {
        YamlConfiguration conf = new YamlConfiguration();

        conf.set(WorldLoader.WORLD_UID, UUID.randomUUID());

        World w = WorldLoader.worldFromConfig(conf, true);
        Assert.assertNull(w);
    }

    @Test
    public void worldFromConfNullName() {
        World w = WorldLoader.worldFromConfig(new YamlConfiguration(), false);
        Assert.assertNull(w);
    }

    @Test
    public void worldFromConfNullUUID() {
        World w = WorldLoader.worldFromConfig(new YamlConfiguration(), true);
        Assert.assertNull(w);
    }

    //////////////////////////////////////
    // worldlessBlockLocationFromConfig //
    //////////////////////////////////////


    @Test
    public void worldlessBlockLocFromConfEmpty() {
        Location loc = WorldLoader.worldlessBlockLocationFromConfig(new YamlConfiguration());
        Assert.assertNull(loc);
    }

    @Test
    public void worldlessBlockLocFromConfValid() {

        YamlConfiguration conf = new YamlConfiguration();
        conf.set(WorldLoader.X, 4);
        conf.set(WorldLoader.Y, 0);
        conf.set(WorldLoader.Z, -4);
        Location loc = WorldLoader.worldlessBlockLocationFromConfig(conf);
        Assert.assertNotNull(loc);
    }

    @Test
    public void worldlessBlockLocFromConfNotAllKeys() {

        YamlConfiguration conf = new YamlConfiguration();
        conf.set(WorldLoader.X, 4);
        conf.set(WorldLoader.Z, -4);
        Location loc = WorldLoader.worldlessBlockLocationFromConfig(conf);
        Assert.assertNull(loc);
    }

    @Test
    public void worldlessBlockLocFromConfWrongType() {
        YamlConfiguration conf = new YamlConfiguration();
        conf.set(WorldLoader.X, "aaaaaa");
        conf.set(WorldLoader.Y, 0);
        conf.set(WorldLoader.Z, -4);
        Location loc = WorldLoader.worldlessBlockLocationFromConfig(conf);
        Assert.assertNull(loc);
    }


    /////////////////////////////
    // blockLocationFromConfig //
    /////////////////////////////


    @Test
    public void blockLocationFromConfigValid() {
        YamlConfiguration conf = new YamlConfiguration();
        conf.set(WorldLoader.X, 6);
        conf.set(WorldLoader.Y, 0);
        conf.set(WorldLoader.Z, -4);
        conf.set(WorldLoader.WORLD_NAME, "test");

        when(Bukkit.getWorld("test")).thenReturn(mock(World.class));

        Location loc = WorldLoader.blockLocationFromConfig(conf, false);
        Assert.assertNotNull(loc);
    }

    @Test
    public void blockLocationFromConfigNoWorld() {
        YamlConfiguration conf = new YamlConfiguration();
        conf.set(WorldLoader.X, 6);
        conf.set(WorldLoader.Y, 0);
        conf.set(WorldLoader.Z, -4);

        Location loc = WorldLoader.blockLocationFromConfig(conf, false);
        Assert.assertNull(loc);
    }

    @Test
    public void blockLocationFromConfigInvalidPos() {
        YamlConfiguration conf = new YamlConfiguration();
        conf.set(WorldLoader.X, 6);
        conf.set(WorldLoader.Y, "ye");
        conf.set(WorldLoader.Z, -4);
        conf.set(WorldLoader.WORLD_NAME, "test");

        when(Bukkit.getWorld("test")).thenReturn(mock(World.class));

        Location loc = WorldLoader.blockLocationFromConfig(conf, false);
        Assert.assertNull(loc);
    }
}
