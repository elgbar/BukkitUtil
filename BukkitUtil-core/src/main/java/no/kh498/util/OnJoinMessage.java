package no.kh498.util;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author Elg
 * @since 0.1.0
 */
public class OnJoinMessage implements Listener {

    private static final String FILE_PATH = "";
    private static final String FILE_NAME = "JoinMessages";
    private static HashMap<UUID, List<String>> messageMap;
    @NotNull
    private final Plugin plugin;

    public OnJoinMessage(@NotNull Plugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        try {
            load(plugin);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load(@NotNull final Plugin plugin) throws IOException {
        final String json = FileUtils.read(plugin, FILE_PATH, FILE_NAME + ".json");
        final Type type = new TypeToken<HashMap<UUID, ArrayList<String>>>() { }.getType();

        final HashMap<UUID, List<String>> msgMap = new Gson().fromJson(json, type);

        messageMap = (msgMap != null) ? msgMap : new HashMap<>();
    }

    /**
     * Send a message to a player if he is online, if not queue a message to be displayed the upon the next login
     *
     * @param player
     *     The player to message
     * @param msg
     *     The message to display
     */
    public static void sendIfOnline(@NotNull final OfflinePlayer player, final String msg) {
        Preconditions.checkNotNull(player, "Player cannot be null when sending a message");
        if (player.isOnline()) {
            ((Player) player).sendMessage(msg);
        }
        else {
            queueMessage(player, msg);
        }
    }

    /**
     * Display a message next time the player logs in
     *
     * @param player
     *     The player to message
     * @param msg
     *     The message to display
     */
    public static void queueMessage(final OfflinePlayer player, final String msg) {
        queueMessage(player.getUniqueId(), msg);
    }

    /**
     * Display a message next time the player logs in
     *
     * @param uuid
     *     The uuid of the player to message
     * @param msg
     *     The message to display
     */
    public static void queueMessage(final UUID uuid, final String msg) {
        Preconditions.checkNotNull(uuid, "Player cannot be null when sending a message");
        final List<String> list;

        //get the list of messages queued for a player (or create one)
        if (messageMap.containsKey(uuid)) {
            list = messageMap.get(uuid);
        }
        else {
            list = new ArrayList<>();
            messageMap.put(uuid, list);
        }

        list.add(msg);
//        save();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private static void onDisable(final PluginDisableEvent event) {
        save(event.getPlugin());
    }

    public static void save(@NotNull final Plugin plugin) {
        final Type type = new TypeToken<HashMap<UUID, ArrayList<String>>>() { }.getType();
        try {
            FileUtils.write(new Gson().toJson(messageMap, type), plugin, FILE_PATH, FILE_NAME + ".json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final UUID uuid = player.getUniqueId();
        if (messageMap.containsKey(uuid)) {
            //wait for other messages to appear
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                for (final String msg : messageMap.get(uuid)) {
                    player.sendMessage(msg);
                }
                messageMap.remove(uuid);
            }, MCConstants.ONE_SECOND_IN_TICKS / 2);
        }
    }
}
