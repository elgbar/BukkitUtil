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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author karl henrik
 * @since 0.1.0
 */
public final class OnJoinMessage implements Listener {

    private static HashMap<UUID, ArrayList<String>> messageMap;

    private static final String FILE_PATH = "";
    private static final String FILE_NAME = "JoinMessages";
    private final Plugin plugin;

    public OnJoinMessage(final Plugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        load(plugin);
    }

    /**
     * Send a message to a player if he is online, if not queue a message to be displayed the upon the next login
     *
     * @param player
     *     The player to message
     * @param msg
     *     The message to display
     */
    public static void sendIfOnline(final OfflinePlayer player, final String msg) {
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
        final ArrayList<String> list;

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
    private void onJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final UUID uuid = player.getUniqueId();
        if (messageMap.containsKey(uuid)) {
            //wait for other messages to appear
            Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
                for (final String msg : messageMap.get(uuid)) {
                    player.sendMessage(msg);
                }
                messageMap.remove(uuid);
            }, MCConstants.ONE_SECOND_IN_TICKS / 2);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private static void onDisable(final PluginDisableEvent event) {
        save(event.getPlugin());
    }

    public static void save(final Plugin plugin) {
        final Type type = new TypeToken<HashMap<UUID, ArrayList<String>>>() { }.getType();
        FileUtils.writeJSON(plugin, FILE_PATH, FILE_NAME, new Gson().toJson(messageMap, type), true);
    }

    public static void load(final Plugin plugin) {
        final String json = FileUtils.readJSON(plugin, FILE_PATH, FILE_NAME, true);
        final Type type = new TypeToken<HashMap<UUID, ArrayList<String>>>() { }.getType();

        final HashMap<UUID, ArrayList<String>> msgMap = new Gson().fromJson(json, type);

        messageMap = (msgMap != null) ? msgMap : new HashMap<>();
    }
}
