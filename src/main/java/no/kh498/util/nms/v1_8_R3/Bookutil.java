package no.kh498.util.nms.v1_8_R3;

import com.google.common.base.Preconditions;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Bookutil {

    /**
     * @deprecated Use {@link no.kh498.util.book.Bookutil#openBook(Player, ItemStack)}
     */
    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated
    public static void openBook(Player player, ItemStack book) {
        Preconditions.checkNotNull(player);
        Preconditions.checkNotNull(book);
        Preconditions.checkArgument(book.getType() == Material.WRITTEN_BOOK);

        int slot = player.getInventory().getHeldItemSlot();
        ItemStack old = player.getInventory().getItem(slot);
        player.getInventory().setItem(slot, book);

        ByteBuf buf = Unpooled.buffer(256);
        buf.setByte(0, (byte) 0);
        buf.writerIndex(1);

        PacketDataSerializer pds = new PacketDataSerializer(buf);
        net.minecraft.server.v1_8_R3.PacketPlayOutCustomPayload packet =
            new net.minecraft.server.v1_8_R3.PacketPlayOutCustomPayload("MC|BOpen", pds);

        ((org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);

        player.getInventory().setItem(slot, old);
    }
}
