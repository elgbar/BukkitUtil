package no.kh498.util.book.nms.v1_8_R3;

import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.NBTTagString;
import no.kh498.util.book.bookbuilder.BookBuilder;
import no.kh498.util.book.bookbuilder.PageBuilder;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * @author Elg
 */
public class BookBuilder1_8_R3 extends BookBuilder {

    @NotNull
    @Override
    public ItemStack build() {
        net.minecraft.server.v1_8_R3.ItemStack book = new net.minecraft.server.v1_8_R3.ItemStack(Item.getById(387));
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("author", getAuthor());
        tag.setString("title", getTitle());
        NBTTagList pages = new NBTTagList();
        for (PageBuilder page : getPages()) {
            pages.add(new NBTTagString(page.build()));
        }
        tag.set("pages", pages);
        book.setTag(tag);
        return CraftItemStack.asBukkitCopy(book);
    }
}
