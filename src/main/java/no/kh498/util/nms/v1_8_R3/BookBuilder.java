package no.kh498.util.nms.v1_8_R3;

import com.google.common.base.Preconditions;
import net.minecraft.server.v1_8_R3.*;
import no.kh498.util.nms.IBookBuilder;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;

import java.util.ArrayList;
import java.util.List;

public class BookBuilder implements IBookBuilder {

    private String title;
    private String author;
    private List<String> pages;

    public BookBuilder() {
        title = "";
        author = "";
        pages = new ArrayList<>();
    }

    @Override
    public org.bukkit.inventory.ItemStack build() {
        ItemStack book = new ItemStack(Item.getById(387));
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("author", author);
        tag.setString("title", title);
        NBTTagList pages = new NBTTagList();
        for (String page : this.pages) { pages.add(new NBTTagString(page)); }
        tag.set("pages", pages);
        book.setTag(tag);
        return CraftItemStack.asBukkitCopy(book);
    }

    @Override
    public IBookBuilder setTitle(String title) {
        Preconditions.checkNotNull(title, "The title cannot be null");
        this.title = title;
        return this;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public IBookBuilder setAuthor(String author) {
        Preconditions.checkNotNull(author, "The title cannot be null");
        this.author = author;
        return this;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public IBookBuilder addPage(String page) {
        pages.add(page);
        return this;
    }
}
