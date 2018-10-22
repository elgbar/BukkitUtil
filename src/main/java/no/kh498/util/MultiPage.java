package no.kh498.util;

import no.kh498.util.itemMenus.MathUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Elg
 */
public class MultiPage {

    public final static int DEFAULT_MAX_PER_PAGE = 10;
    public final static String DEFAULT_HEADER = "&7&m\t&r&7 Page %d/%d&m\t";
    public final static String DEFAULT_FOOTER = "&7Type /%s %d to view next page";

    public int maxPerPage;
    public List<String> content;
    public ChatColor elementPrefix = ChatColor.YELLOW;
    /**
     * Must contain two %d, one for the current page and one for max number of pages
     */
    public String header = DEFAULT_HEADER;
    /**
     * Must contain first a %s for the command and a %d for the next page number
     */
    public String footer = DEFAULT_FOOTER;
    public String command;

    public MultiPage(final String command, final String... content) {
        this(command, DEFAULT_MAX_PER_PAGE, Arrays.asList(content));
    }

    public MultiPage(final String command, final int maxPerPage, final List<String> content) {
        this.command = command;
        this.maxPerPage = maxPerPage;
        this.content = content;
    }

    public MultiPage(final String command, final List<String> content) {
        this(command, DEFAULT_MAX_PER_PAGE, content);
    }

    public void viewPage(int page, final CommandSender sender) {
        final int pages = MathUtil.dividedRoundedUp(content.size(), maxPerPage);
        if (page > pages) {
            ChtUtil.sendFormattedMsg(sender, "&cThere is no page %d (max page is %d)", page, pages);
            return;
        }

        //no real need to return an error here
        if (page < 1) {
            page = 1;
        }

        final ArrayList<String> msgList = new ArrayList<>();
        msgList.add(ChtUtil.createFormattedMsg(header, page, pages));

        for (int i = (page - 1) * maxPerPage; i < maxPerPage * page; i++) {
            try {
                final String str = content.get(i);
                msgList.add(elementPrefix.toString() + str);
            } catch (final IndexOutOfBoundsException ignore) {
                //This might be called on last page
                break;
            }

        }

        if (page != pages) {
            msgList.add(ChtUtil.createFormattedMsg(footer, command, page + 1));
        }
        sender.sendMessage(msgList.toArray(new String[0]));
    }
}
