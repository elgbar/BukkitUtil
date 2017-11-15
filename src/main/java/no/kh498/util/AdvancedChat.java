package no.kh498.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

/**
 * @author kh498
 * @since 0.1.0
 */
@SuppressWarnings("unused")
public class AdvancedChat {

    private static final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

    /**
     * Send a message to a player using the action bar.
     * <p>
     * The action bar is the chat right above the inventory, experience, health, item name, etc.
     * <p>
     * There can only be one message at a time in the action bar, so sending another message will cancel the current
     * one.
     *
     * @param players
     *     The players to send the action bar to
     * @param msg
     *     The message to send
     *
     * @throws IllegalStateException
     *     if ProtocolLib is not installed
     */
    public static void sendActionbar(final String msg, final Player... players) {
        sendAltChat(msg, EnumWrappers.ChatType.GAME_INFO, players);
    }

    /**
     * Send a message to a player even when chat is turned off in the Minecraft client.
     *
     * @param players
     *     The players to send the action bar to
     * @param msg
     *     The message to send
     *
     * @throws IllegalStateException
     *     if ProtocolLib is not installed
     */
    public static void sendSystemChat(final String msg, final Player... players) {
        sendAltChat(msg, EnumWrappers.ChatType.SYSTEM, players);
    }

    /**
     * Send a type of chat that is not normally easily sent. This method is public so future ChatTypes will be able to
     * be used.
     *
     * @param players
     *     The players to send the action bar to
     * @param msg
     *     The message to send
     * @param type
     *     The type of chat to send
     *
     * @throws IllegalStateException
     *     if ProtocolLib is not installed
     */
    public static void sendAltChat(final String msg, final EnumWrappers.ChatType type, final Player... players) {
        if (Bukkit.getPluginManager().getPlugin("ProtocolLib") == null) {
            throw new IllegalStateException("You must be using ProtocolLib!");
        }
        final PacketContainer chatPacket =
            new PacketContainer(PacketType.Play.Server.CHAT);//protocolManager.createPacket();
        final WrappedChatComponent wcp = WrappedChatComponent.fromText(msg);
        chatPacket.getChatComponents().write(0, wcp);
        chatPacket.getBytes().write(0, type.getId());
        try {
            for (final Player p : players) {
                protocolManager.sendServerPacket(p, chatPacket);
            }
        } catch (final InvocationTargetException e) {
            Bukkit.getLogger().severe("AdvancedChat cannot be used after a reload!");
        }
    }
}
