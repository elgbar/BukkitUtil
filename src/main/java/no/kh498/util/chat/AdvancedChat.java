package no.kh498.util.chat;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @author Elg
 * @since 0.1.0
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class AdvancedChat {

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
        sendAltChat(msg, ChatType.ACTION_BAR, players);
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
     */
    public static void sendAltChat(final String msg, final ChatType type, final Player... players) {
        try {
            final BaseComponent comp = new TextComponent(msg);
            final ChatMessageType cmType = ChatMessageType.valueOf(type.name());
            for (final Player player : players) {
                player.spigot().sendMessage(cmType, comp);
            }
        } catch (final NoSuchMethodError e) {
            //try to use the ProtocolLib
            sendAltChatProtocolLib(msg, type, players);
        }
    }

    /**
     * Send a type of chat that is not normally easily sent.
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
    private static void sendAltChatProtocolLib(final String msg, final ChatType type, final Player... players) {
        if (Bukkit.getPluginManager().getPlugin("ProtocolLib") == null) {
            throw new IllegalStateException("You must be using ProtocolLib!");
        }
        try {
            //create a chat packet
            final PacketContainer chatPacket =
                new PacketContainer(PacketType.Play.Server.CHAT);//protocolManager.createPacket();

            //create a wrapper for the text to send
            final WrappedChatComponent wcp = WrappedChatComponent.fromText(msg);
            chatPacket.getChatComponents().write(0, wcp);

            //set the type of chat to send
            chatPacket.getBytes().write(0, type.getId());

            for (final Player p : players) {
                ProtocolLibrary.getProtocolManager().sendServerPacket(p, chatPacket);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
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
        sendAltChat(msg, ChatType.SYSTEM, players);
    }
}
