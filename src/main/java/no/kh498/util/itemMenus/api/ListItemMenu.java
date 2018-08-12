package no.kh498.util.itemMenus.api;

import com.google.common.base.Preconditions;
import no.kh498.util.itemMenus.MathUtil;
import no.kh498.util.itemMenus.PluginHolder;
import no.kh498.util.itemMenus.api.constants.CommonPos;
import no.kh498.util.itemMenus.api.constants.Size;
import no.kh498.util.itemMenus.api.items.ActionMenuItem;
import no.kh498.util.itemMenus.api.items.StaticMenuItem;
import no.kh498.util.itemMenus.events.ItemClickEvent;
import no.kh498.util.itemMenus.items.ColoredPaneItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * View a {@link ArrayList ArrayList}{@code <}{@link MenuItem MenuItem}{@code
 * >} using the {@link ItemMenu ItemMenu} system.
 * <p>
 * If there are more than 45 (9 items in each row * 5 columns) items then you can scroll between pages.
 * <p>
 * <b>Note:</b> You need JVM 1.8 or higher to run this due to lambda expressions.
 *
 * @author kh498
 * @since 0.1.0
 */
@SuppressWarnings("unused")
public class ListItemMenu {

    public static final int GOLD_INDEX = CommonPos.MIDDLE.getPos(Size.SIX);
    /* constants */
    private static final int FIVE_LINES_SIZE = Size.FIVE.getSize();
    /* set by the user */
    private final List<MenuItem> menuItemList;
    private final String title;
    private final Plugin plugin;

    /* generated */
    private final int pages;
    private final ItemMenu[] cache;

    /**
     * @param menuItemList
     *     The list of MenuItems to include
     * @param title
     *     The title of the menu, <b>can maximum be 22 characters long</b>
     *
     * @deprecated Use constructor with plugin reference
     */
    @Deprecated
    public ListItemMenu(final List<MenuItem> menuItemList, final String title) {
        this(PluginHolder.getPlugin(), menuItemList, title);
    }

    /**
     * @param menuItemList
     *     The list of MenuItems to include
     * @param title
     *     The title of the menu, <b>can maximum be 22 characters long</b>
     */
    public ListItemMenu(Plugin plugin, final List<MenuItem> menuItemList, final String title) {

        Preconditions.checkArgument(title.length() <= 22, "Title cannot be longer than 22 characters");
        Preconditions.checkNotNull(plugin);

        this.plugin = plugin;
        this.menuItemList = menuItemList;
        this.title = title;
        this.pages = MathUtil.dividedRoundedUp(menuItemList.size(), FIVE_LINES_SIZE);
        this.cache = new ItemMenu[this.pages];
    }

    public void openMenu(final Player player) {
        openMenu(player, 0);
    }

    private void openMenu(final Player player, final int page) {
        final ItemMenu menu;
        if (this.cache[page] != null) {
            menu = this.cache[page];
        }
        else {
            menu = new ItemMenu(this.title + " - " + (page + 1) + "/" + this.pages, Size.SIX);

            //The panes at the bottom of the menu,
            final MenuItem bottomPane = new ColoredPaneItem(DyeColor.BLACK);
            for (int m = 1; m < 8; m++) {
                menu.setItem(FIVE_LINES_SIZE + m, bottomPane);
            }

            int index = FIVE_LINES_SIZE * page;
            for (int l = 0; l < FIVE_LINES_SIZE; l++, index++) {
                if (this.menuItemList.size() > index) {
                    menu.setItem(l, this.menuItemList.get(index));
                }
            }
            this.cache[page] = menu.copy();
        }

        menu.setItem(CommonPos.LEFT, Size.SIX, prevItem(player, page));
        menu.setItem(CommonPos.RIGHT, Size.SIX, nextItem(player, page));

        final ItemMenu finalMenu = menu;
        Bukkit.getScheduler().scheduleSyncDelayedTask(PluginHolder.getPlugin(), () -> finalMenu.open(player), 2L);
    }

    private MenuItem nextItem(final Player player, final int page) {
        //noinspection Duplicates
        return (new ActionMenuItem(ChatColor.WHITE + "Next page", event -> {
            final int newPage = page + 1;
            if (newPage < ListItemMenu.this.pages) {
                event.setWillClose(true);
                openMenu(player, (newPage >= ListItemMenu.this.pages) ? ListItemMenu.this.pages - 1 : newPage);
            }
        }, new ItemStack(Material.SLIME_BALL)));
    }

    private MenuItem prevItem(final Player player, final int page) {
        //noinspection Duplicates
        return (new ActionMenuItem(ChatColor.WHITE + "Previous page", event -> {
            final int newPage = page - 1;
            if (newPage >= 0) {
                event.setWillClose(true);
                openMenu(player, newPage);
            }
        }, new ItemStack(Material.MAGMA_CREAM)));
    }

    /**
     * @return the List of menuItem that will be displayed
     */
    public final ArrayList<MenuItem> getMenuItemList() {
        return this.menuItemList;
    }

    /**
     * @return the title
     */
    public final String getTitle() {
        return this.title;
    }

    /**
     * @return the pages
     */
    public final int getPages() {
        return this.pages;
    }

    @Override
    public String toString() {
        return "ListItemMenu{" + ", title='" + this.title + '\'' + ", pages=" + this.pages + '}';
    }
}
