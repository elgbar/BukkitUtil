package no.kh498.util.itemMenus.api;

import com.google.common.base.Preconditions;
import no.kh498.util.itemMenus.MathUtil;
import no.kh498.util.itemMenus.api.constants.CommonPos;
import no.kh498.util.itemMenus.api.constants.Size;
import no.kh498.util.itemMenus.api.items.ActionMenuItem;
import no.kh498.util.itemMenus.api.items.StaticMenuItem;
import no.kh498.util.itemMenus.events.ItemClickEvent;
import no.kh498.util.itemMenus.items.StaticColoredPaneItem;
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
 * @author Elg
 * @since 0.1.0
 */
@SuppressWarnings("unused")
public class ItemMenuList {

    public static final MenuItem DEFAULT_BOTTOM_PANE = new StaticColoredPaneItem(DyeColor.BLACK);
    /* constants */
    private static final int FIVE_LINES_SIZE = Size.FIVE.getSize();
    /* set by the user */
    private final List<MenuItem> menuItemList;
    private final String title;
    private final Plugin plugin;
    /* generated */
    private final int pages;
    private boolean smoothUpdate;
    private MenuItem bottomPane = DEFAULT_BOTTOM_PANE;

    /**
     * @param menuItemList
     *     The list of MenuItems to include
     * @param title
     *     The title of the menu, <b>can maximum be 22 characters long</b>
     *
     * @throws IllegalArgumentException
     *     if the title is longer than 22 characters
     * @throws NullPointerException
     *     if {@code plugin} or {@code menuItemList} is {@code null}
     */
    public ItemMenuList(Plugin plugin, final List<MenuItem> menuItemList, final String title) {
        this(plugin, menuItemList, title, true);
    }

    /**
     * @param menuItemList
     *     The list of MenuItems to include
     * @param title
     *     The title of the menu, <b>can maximum be 22 characters long</b>
     * @param smoothUpdate
     *     If page number should be traded for nicer page transitions
     *
     * @throws IllegalArgumentException
     *     if the title is longer than 22 characters
     * @throws NullPointerException
     *     if {@code plugin} or {@code menuItemList} is {@code null}
     */
    @SuppressWarnings("WeakerAccess")
    public ItemMenuList(Plugin plugin, final List<MenuItem> menuItemList, final String title, boolean smoothUpdate) {

        Preconditions.checkArgument(smoothUpdate || title.length() <= 24,
                                    "Title must be shorter than 24 characters when displaying page number");
        Preconditions.checkNotNull(plugin);
        Preconditions.checkNotNull(menuItemList);

        this.plugin = plugin;
        this.menuItemList = menuItemList;
        this.title = title;
        pages = MathUtil.dividedRoundedUp(menuItemList.size(), FIVE_LINES_SIZE);
        this.smoothUpdate = smoothUpdate;

    }

    public void openMenu(final Player player) {
        ItemMenu menu = new ItemMenu(plugin, title, Size.SIX);
        changePage(player, 0, menu);
        menu.open(player);
    }

    private void changePage(final Player player, final int page, ItemMenu menu) {
        if (!useSmoothUpdate()) {
            menu.setName(title + " " + (page + 1) + "/" + pages);
        }

        //clean menu for next page
        menu.clearAllItems();
        for (int m = 0; m < 9; m++) {
            menu.setItem(Size.FIVE.getSize() + m, bottomPane);
        }

        //apply this pages items
        int index = FIVE_LINES_SIZE * page;
        for (int i = 0; i < FIVE_LINES_SIZE; i++, index++) {
            if (menuItemList.size() > index) { menu.setItem(i, menuItemList.get(index)); }
        }

        //set items to go to next and previous pages, if any
        if (page != 0) menu.setItem(CommonPos.LEFT, Size.SIX, prevItem(player, page, menu));
        if (page != pages - 1) menu.setItem(CommonPos.RIGHT, Size.SIX, nextItem(player, page, menu));
    }

    private void open(final Player player, final int page, ItemMenu menu, ItemClickEvent event) {
        //do not change page if there is no other page
        if (page < 0 || page >= pages) {
            return;
        }

        changePage(player, page, menu);
        if (useSmoothUpdate()) {
            event.setWillUpdate(true);
        }
        else {
            event.setWillClose(true);
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> menu.open(player), 2L);
        }
    }

    private MenuItem nextItem(final Player player, final int page, ItemMenu menu) {
        return new ActionMenuItem(ChatColor.WHITE + "Next page", event -> open(player, page + 1, menu, event),
                                  new ItemStack(Material.SLIME_BALL));
    }

    private MenuItem prevItem(final Player player, final int page, ItemMenu menu) {
        return new ActionMenuItem(ChatColor.WHITE + "Previous page", event -> open(player, page - 1, menu, event),
                                  new ItemStack(Material.MAGMA_CREAM));
    }

    /**
     * @return the List of menuItem that will be displayed
     */
    public final List<MenuItem> getMenuItemList() {
        return menuItemList;
    }

    /**
     * @return the title
     */
    public final String getTitle() {
        return title;
    }

    /**
     * @return the pages
     */
    public final int getPages() {
        return pages;
    }

    public boolean useSmoothUpdate() {
        return smoothUpdate;
    }

    public ItemMenuList setSmoothUpdate(boolean smoothUpdate) {
        this.smoothUpdate = smoothUpdate;
        return this;
    }

    public MenuItem getBottomPane() {
        return bottomPane;
    }

    /**
     * The filler items at the bottom of tha page.
     * <p>
     * default is a black {@link StaticColoredPaneItem}
     */
    public void setBottomPane(StaticMenuItem bottomPane) {
        this.bottomPane = bottomPane;
    }

    @Override
    public String toString() {
        return "ListItemMenu{" + ", title='" + title + '\'' + ", pages=" + pages + '}';
    }
}
