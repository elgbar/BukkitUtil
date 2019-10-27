package no.kh498.util.book.bookbuilder;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Create a readable book, each page is created with {@link PageBuilder}
 */
public abstract class BookBuilder {

    private String title = "";
    private String author = "";
    private List<PageBuilder> pages = new ArrayList<>();

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

    /**
     * <b>NOTE</b> Do not use if the page was obtained through {@link #createPageBuilder()}
     *
     * @param page
     *     The page to add
     */
    @NotNull
    public BookBuilder addPage(@NotNull PageBuilder page) {
        pages.add(page);
        return this;
    }

    @NotNull
    public List<PageBuilder> getPages() {
        return pages;
    }

    /**
     * Create a new page builder and add it to the book
     *
     * @return A new page builder
     */
    @NotNull
    public PageBuilder createPageBuilder() {
        PageBuilder pb = new PageBuilder();
        addPage(pb);
        return pb;
    }

}
