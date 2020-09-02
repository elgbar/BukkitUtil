package no.kh498.util;

import java.util.Arrays;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.ArgumentMatchers.nullable;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.impl.BukkitLoggerAdapter;

/**
 * @author Elg
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Bukkit.class})
public abstract class TestHelper {

  public static final String FAKE_PLUGIN_NAME = "JUnit Test";

  @Rule
  protected TemporaryFolder folder = new TemporaryFolder();

  @BeforeClass
  public static void mockStatic() {

    mockStaticMethods();
    //pretend the initialization is done, only needs to be done once per class
    try {
      Reflection.modifyStaticField(BukkitLoggerAdapter.class, "BUKKIT_PLUGIN_NAME", FAKE_PLUGIN_NAME);
      BukkitLoggerAdapter.init(false);
    } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodError e) {
      e.printStackTrace();
    }
  }

  /**
   * Methods that need to be mocked before each class, still needs to be done before each run
   */
  public static void mockStaticMethods() {
    PowerMockito.mockStatic(Bukkit.class);
    when(Bukkit.getPluginManager()).thenReturn(mock(PluginManager.class));


    ItemFactory itemFactory = mock(ItemFactory.class);
    when(itemFactory.equals(nullable(ItemMeta.class), any(ItemMeta.class))).thenReturn(true);
    when(itemFactory.equals(nullable(ItemMeta.class), isNull())).thenReturn(false);
    when(itemFactory.equals(isNull(), any(ItemMeta.class))).thenReturn(false);
    when(itemFactory.equals(isNull(), isNull())).thenReturn(true);
    when(Bukkit.getItemFactory()).thenReturn(itemFactory);


    when(Bukkit.getPluginManager()).thenReturn(mock(PluginManager.class));
    when(Bukkit.getLogger()).thenReturn(mock(java.util.logging.Logger.class));


    BukkitScheduler scheduler = mock(BukkitScheduler.class);
    when(Bukkit.getScheduler()).thenReturn(scheduler);

    //simulate scheduler by running it right away
    when(scheduler.runTaskLater(any(), any(Runnable.class), anyLong())).then(invocation -> {
      invocation.getArgument(1, Runnable.class).run();
      return null;
    });

//        when(scheduler.runTaskLaterAsynchronously(any(), any(Runnable.class), anyLong())).then(invocation -> {
//            invocation.getArgument(1, Runnable.class).run();
//            return null;
//        });
//
//        when(scheduler.runTask(any(), any(Runnable.class))).then(invocation -> {
//            invocation.getArgument(1, Runnable.class).run();
//            return null;
//        });
//
//        when(scheduler.runTaskAsynchronously(any(), any(Runnable.class))).then(invocation -> {
//            invocation.getArgument(1, Runnable.class).run();
//            return null;
//        });
  }

  /**
   * @return Create a mock player
   */
  @NotNull
  protected static Player createPlayer() {
    return createPlayer(UUID.randomUUID());
  }

  /**
   * @return Create a mock player
   */
  @NotNull
  protected static Player createPlayer(@NotNull UUID uuid) {
    Player player = Mockito.mock(Player.class);
    Mockito.when(player.getUniqueId()).thenReturn(uuid);
    Mockito.when(player.isOnline()).thenReturn(true);
    Mockito.when(player.getPlayer()).thenReturn(player);
    return player;
  }

  //Make sure bukkit is always mocked before each test methods
  @Before
  public void beforeEach() {
    mockStaticMethods();
  }

  //print the raw message without any formatting
  public Answer<Void> simplePrint() {
    return invocation -> {
      System.out.println(invocation.getArguments()[0]);
      return null;
    };
  }

  public Answer<Void> formatAllArgsPrint() {
    return invocation -> {
      String msg;
      switch (invocation.getArguments().length) {
        case 2:
          msg = MessageFormatter.format(invocation.getArgument(0, String.class), invocation.getArguments()[1])
                                .getMessage();
          break;
        case 3:
          msg = MessageFormatter.format(invocation.getArgument(0, String.class), invocation.getArguments()[1],
                                        invocation.getArguments()[2]).getMessage();
          break;
        default:
          Object[] args = Arrays.copyOfRange(invocation.getArguments(), 1, invocation.getArguments().length);
          msg = MessageFormatter.arrayFormat(invocation.getArgument(0, String.class), args).getMessage();

      }
      System.out.println(msg);

      return null;
    };
  }
}
