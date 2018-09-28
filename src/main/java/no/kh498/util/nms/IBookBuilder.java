package no.kh498.util.nms;

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

public interface IBookBuilder {

    /**
     * Set the title of the book
     *
     * @param title
     *     The new title
     *
     * @throws NullPointerException
     *     if the title is {@code null}
     */
    IBookBuilder setTitle(String title);

    /**
     * @return The current title of the book
     */
    String getTitle();

    /**
     * Set the author of the book
     *
     * @param author
     *     Name of the book author
     *
     * @throws NullPointerException
     *     if the author is {@code null}
     */
    IBookBuilder setAuthor(String author);

    /**
     * Set the author of the book to a player
     *
     * @param player
     *     The author
     */
    default IBookBuilder setAuthor(OfflinePlayer player) {
        return setAuthor(player == null ? "" : player.getName());
    }

    /**
     * @return The current author of the book
     */
    String getAuthor();

    /**
     * Add a new page to the book
     *
     * @param page
     *     Page text to add
     */
    IBookBuilder addPage(String page);

    /**
     * @param pb
     *     The page builder to build a page from
     */
    default IBookBuilder addPage(PageBuilder pb) {
        pb.build();
        return this;
    }

    /**
     * Build the book into an itemstack
     */
    ItemStack build();

    default PageBuilder createPageBuilder() {
        return new PageBuilder(this);
    }

}
