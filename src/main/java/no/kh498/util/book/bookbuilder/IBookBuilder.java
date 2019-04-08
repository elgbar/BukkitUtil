package no.kh498.util.book.bookbuilder;

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IBookBuilder {

    /**
     * @return The current title of the book
     */
    String getTitle();

    /**
     * Set the title of the book
     *
     * @param title
     *     The new title
     *
     * @throws NullPointerException
     *     if the title is {@code null}
     */
    @NotNull
    IBookBuilder setTitle(String title);

    /**
     * @return The current author of the book
     */
    String getAuthor();

    /**
     * Set the author of the book to a player
     *
     * @param player
     *     The author
     */
    @NotNull
    default IBookBuilder setAuthor(@Nullable OfflinePlayer player) {
        return setAuthor(player == null ? "" : player.getName());
    }

    /**
     * Set the author of the book
     *
     * @param author
     *     Name of the book author
     *
     * @throws NullPointerException
     *     if the author is {@code null}
     */
    @NotNull
    IBookBuilder setAuthor(String author);

    /**
     * Add a new page to the book
     *
     * @param page
     *     Page text to add
     */
    @NotNull
    IBookBuilder addPage(String page);

    /**
     * @param pb
     *     The page builder to build a page from
     */
    @NotNull
    default IBookBuilder addPage(@NotNull PageBuilder pb) {
        pb.build();
        return this;
    }

    /**
     * Build the book into an itemstack
     */
    @NotNull
    ItemStack build();

    @NotNull
    default PageBuilder createPageBuilder() {
        return new PageBuilder(this);
    }

}
