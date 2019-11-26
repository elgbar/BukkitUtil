package no.kh498.util.book.nms.v1_12_R1;

import no.kh498.util.book.bookbuilder.BookBuilder;
import no.kh498.util.book.bookbuilder.PageBuilder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * @author Elg
 */
public class BookBuilder1_12_R1 extends BookBuilder {

    @NotNull
    @Override
    public ItemStack build() {
        net.minecraft.server.v1_12_R1.ItemStack book = new net.minecraft.server.v1_12_R1.ItemStack(
            net.minecraft.server.v1_12_R1.Item.getById(387));
        net.minecraft.server.v1_12_R1.NBTTagCompound tag = new net.minecraft.server.v1_12_R1.NBTTagCompound();
        tag.setString("author", getAuthor());
        tag.setString("title", getTitle());
        net.minecraft.server.v1_12_R1.NBTTagList pages = new net.minecraft.server.v1_12_R1.NBTTagList();
        for (PageBuilder page : getPages()) {
            pages.add(new net.minecraft.server.v1_12_R1.NBTTagString(page.build()));
        }
        tag.set("pages", pages);
        book.setTag(tag);
        return org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack.asBukkitCopy(book);
    }
}
