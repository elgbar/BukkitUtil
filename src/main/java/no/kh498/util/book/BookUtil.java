package no.kh498.util.book;

import no.kh498.util.book.bookbuilder.BookBuilder;
import no.kh498.util.book.nms.v1_8_R3.BookBuilder18R3;
import no.kh498.util.book.nms.v1_8_R3.BookUtil18R3;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static no.kh498.util.VersionUtil.getNmsVersion;
import static no.kh498.util.VersionUtil.v1_8_R3;

/**
 * @author Elg
 */
public class BookUtil {

    /**
     * Maximum number of unicode code units there can be on a book page
     *
     * @see org.bukkit.inventory.meta.BookMeta#addPage(String...)
     */
    public static final int MAX_BOOK_PAGE_LENGTH = 256;

    private static final Logger logger = LoggerFactory.getLogger(BookUtil.class);

    /**
     * Open a book for a player
     *
     * <strong>Note:</strong> This is using NMS code, not all versions are supported
     *
     * @param player
     *     The player to open the book to
     * @param book
     *     The book to open. Must have the material {@link Material#WRITTEN_BOOK}
     *
     * @throws NotImplementedException
     *     if the NMS version is not supported
     */
    //TODO make it work with more versions
    public static void openBook(@NotNull Player player, @NotNull ItemStack book) {
        switch (getNmsVersion()) {
            case v1_8_R3:
                BookUtil18R3.openBook(player, book);
                break;
            default:
                throw new NotImplementedException("This version of minecraft is not supported yet.");
        }
    }

    /**
     * @return A book build compatible with current bukkit version
     *
     * @throws NotImplementedException
     *     if the NMS version is not supported
     */
    public static BookBuilder createBookBuilder() {
        switch (getNmsVersion()) {
            case v1_8_R3:
                return new BookBuilder18R3();
            default:
                throw new NotImplementedException("This version of minecraft is not supported yet.");
        }
    }
}
