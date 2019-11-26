package no.kh498.util.book.nms.v1_8_R3;

import com.google.common.base.Preconditions;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import no.kh498.util.book.BookUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * @author Elg
 */
public class BookUtil1_8_R3 {

    /**
     * Use {@link BookUtil#openBook(Player, ItemStack)}
     */
    public static void openBook(@NotNull Player player, @NotNull ItemStack book) {
        Preconditions.checkArgument(book.getType() == Material.WRITTEN_BOOK);

        ByteBuf buf = Unpooled.buffer(256);
        buf.setByte(0, (byte) 0);
        buf.writerIndex(1);

        net.minecraft.server.v1_8_R3.PacketDataSerializer pds = new net.minecraft.server.v1_8_R3.PacketDataSerializer(
            buf);
        net.minecraft.server.v1_8_R3.PacketPlayOutCustomPayload packet =
            new net.minecraft.server.v1_8_R3.PacketPlayOutCustomPayload("MC|BOpen", pds);

        int slot = player.getInventory().getHeldItemSlot();
        ItemStack old = player.getInventory().getItem(slot);
        player.getInventory().setItem(slot, book);

        ((org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);

        player.getInventory().setItem(slot, old);
    }
}
