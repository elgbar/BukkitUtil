package no.kh498.util.book;

import no.kh498.util.VersionUtil;
import no.kh498.util.book.bookbuilder.IBookBuilder;
import no.kh498.util.nms.v1_8_R3.BookBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Bookutil {

    private static final Logger logger = LoggerFactory.getLogger(Bookutil.class);

    public static final int MAX_BOOK_LINES = 14;

    /**
     * Open a book for a player
     *
     * <strong>Note:</strong> This is using NMS code, not all versions are supported
     *
     * @param player
     *     The player to open the book to
     * @param book
     *     Tge book to open. Must have the material {@link org.bukkit.Material#WRITTEN_BOOK}
     *
     * @throws NotImplementedException
     *     if the NMS version is not supported
     */
    //TODO make it work with more versions
    @SuppressWarnings("deprecation")
    public static void openBook(Player player, ItemStack book) {
        switch (VersionUtil.getNmsVersion()) {
            case "v1_8_R3":
                no.kh498.util.nms.v1_8_R3.Bookutil.openBook(player, book);
                break;
            default:
                throw new NotImplementedException();
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
                throw new NotImplementedException();
        }
    }
}
