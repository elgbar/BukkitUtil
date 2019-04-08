package no.kh498.util.book;

import no.kh498.util.VersionUtil;
import no.kh498.util.book.bookbuilder.IBookBuilder;
import no.kh498.util.nms.v1_8_R3.BookBuilder;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Elg
 */
public class Bookutil {

    public static final int MAX_BOOK_LINES = 14;
    private static final Logger logger = LoggerFactory.getLogger(Bookutil.class);

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
    @SuppressWarnings("deprecation")
    public static void openBook(@NotNull Player player, @NotNull ItemStack book) {
        switch (VersionUtil.getNmsVersion()) {
            case "v1_8_R3":
                no.kh498.util.nms.v1_8_R3.Bookutil.openBook(player, book);
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
    public static IBookBuilder createBookBuilder() {
        switch (VersionUtil.getNmsVersion()) {
            case "v1_8_R3":
                return new BookBuilder();
            default:
                throw new NotImplementedException("This version of minecraft is not supported yet.");
        }
    }
}
