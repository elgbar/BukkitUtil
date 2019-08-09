package no.kh498.util.book.bookbuilder;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class BookBuilder {


    private String title = "";
    private String author = "";
    private List<String> pages = new ArrayList<>();

    /**
     * Build the book into an itemstack
     */
    @NotNull
    public abstract ItemStack build();

    @NotNull
    public BookBuilder setTitle(@NotNull String title) {
        this.title = title;
        return this;
    }

    @NotNull
    public String getTitle() {
        return title;
    }

    @NotNull
    public BookBuilder setAuthor(@NotNull String author) {
        this.author = author;
        return this;
    }

    @NotNull
    public String getAuthor() {
        return author;
    }

    @NotNull
    public BookBuilder addPage(@NotNull String page) {
        pages.add(page);
        return this;
    }

    public List<String> getPages() {
        return pages;
    }

    @NotNull
    public PageBuilder createPageBuilder() {
        return new PageBuilder(this);
    }

}
