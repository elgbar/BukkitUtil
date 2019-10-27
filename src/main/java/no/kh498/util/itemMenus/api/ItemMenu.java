package no.kh498.util.itemMenus.api;

import com.google.common.base.Preconditions;
import no.kh498.util.itemMenus.api.constants.CommonPos;
import no.kh498.util.itemMenus.api.constants.Size;
import no.kh498.util.itemMenus.api.items.StaticMenuItem;
import no.kh498.util.itemMenus.events.ItemClickEvent;
import no.kh498.util.itemMenus.items.StaticColoredPaneItem;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.UUID;

/**
 * A Menu controlled by ItemStacks in an Inventory.
 *
 * @author Elg
 * @author nuclearcat1337
 * @author MrLittleKitty (taken from <a href="https://github.com/MrLittleKitty/AnnihilationPro">here</a>)
 * @author ampayne2 (orginal author, <a href="https://github.com/ampayne2/AmpMenus">github</a>)
 */

@SuppressWarnings({"WeakerAccess", "unused", "UnusedReturnValue"})
public class ItemMenu {

    /**
     * The {@link StaticMenuItem StaticMenuItem} that appears in empty slots if {@link
     * ItemMenu#fillEmptySlots()} is called.
     */
    private static final MenuItem EMPTY_SLOT_ITEM = new StaticColoredPaneItem(DyeColor.GRAY);
    private final Plugin plugin;
    @Nullable
    private String name;
    @Nullable
    private Size size;
    @Nullable
    private MenuItem[] items;
    @Nullable
    private ItemMenu parent;

    /**
     * Creates an ItemMenu with no parent.
     *
     * @param name
     *     The name of the inventory.
     * @param size
     *     The {@link Size Size} of the inventory.
     */
    public ItemMenu(Plugin plugin, final String name, @NotNull final Size size) {
        this(plugin, name, size, null);
    }

    /**
     * Creates an ItemMenu.
     *
     * @param name
     *     The name of the inventory.
     * @param size
     *     The {@link Size} of the inventory.
     * @param parent
     *     The ItemMenu's parent.
     */
    public ItemMenu(Plugin plugin, final String name, @NotNull final Size size, final ItemMenu parent) {
        this(plugin, name, size, parent, new MenuItem[size.getSize()]);
    }

    /**
     * Creates an ItemMenu.
     *
     * @param name
     *     The name of the inventory.
     * @param size
     *     The {@link Size} of the inventory.
     * @param parent
     *     The ItemMenu's parent.
     * @param items
     *     The items that this menu contains
     */
    public ItemMenu(Plugin plugin, @Nullable final String name, @Nullable final Size size, @Nullable final ItemMenu parent,
                    @Nullable final MenuItem[] items) {
        this.plugin = plugin;
        this.name = name;
        this.size = size;
        this.parent = parent;

        if (items == null) { this.items = null; }
        else { this.items = Arrays.copyOf(items, items.length); }
    }

    /**
     * Gets the name of the ItemMenu.
     *
     * @return The ItemMenu's name.
     */
    @Nullable
    public String getName() {
        return name;
    }

    /**
     * @param name
     *     the name to set
     *
     * @return this, for chaining
     */
    @NotNull
    public ItemMenu setName(final String name) {
        this.name = name;
        return this;
    }

    /**
     * Gets the parent of the ItemMenu.
     *
     * @return The ItemMenu's parent.
     */
    @Nullable
    public ItemMenu getParent() {
        return parent;
    }

    /**
     * Sets the parent of the ItemMenu.
     *
     * @param parent
     *     The ItemMenu's parent.
     *
     * @return this, for chaining
     */
    @NotNull
    public ItemMenu setParent(final ItemMenu parent) {
        this.parent = parent;
        return this;
    }

    /**
     * Sets the {@link MenuItem MenuItem} of a slot.
     *
     * @param position
     *     The slot position.
     * @param menuItem
     *     The {@link MenuItem MenuItem}.
     *
     * @return this, for chaining
     */
    @NotNull
    public ItemMenu setItem(final int position, final MenuItem menuItem) {
        Preconditions.checkArgument(position >= 0 && position < size.getSize(), "Position must be between zero and %s. was ",
                                    size.getSize(), position);
        items[position] = menuItem;
        return this;
    }

    /**
     * Sets the {@link MenuItem MenuItem} of a slot.
     *
     * @param position
     *     The common position of the item
     * @param menuItem
     *     The {@link MenuItem MenuItem}.
     *
     * @return this, for chaining
     */
    @NotNull
    public ItemMenu setItem(@NotNull final CommonPos position, final MenuItem menuItem) {
        setItem(position.getPos(), menuItem);
        return this;
    }

    /**
     * Sets the {@link MenuItem MenuItem} of a slot.
     *
     * @param position
     *     The common position of the item
     * @param row
     *     What row of the item to clear
     * @param menuItem
     *     The {@link MenuItem MenuItem}.
     *
     * @return this, for chaining
     */
    @NotNull
    public ItemMenu setItem(@NotNull final CommonPos position, @NotNull final Size row, final MenuItem menuItem) {
        return setItem(position.getPos(row), menuItem);
    }

    /**
     * @return All {@link MenuItem MenuItem}s in a ItemMenu
     */
    @Nullable
    private MenuItem[] getItems() {
        return items;
    }

    /**
     * @return A copy of this ItemMenu
     */
    @NotNull
    public ItemMenu copy() {
        return ItemMenu.copy(this);
    }

    /**
     * @return A copy of the original ItemMenu
     */
    private static ItemMenu copy(final ItemMenu orgMenu) {
        return new ItemMenu(orgMenu.plugin, orgMenu.name, orgMenu.size, orgMenu.parent, orgMenu.items);
    }

    /**
     * Remove a {@link MenuItem MenuItem} at a certain position in the ItemMenu
     *
     * @param position
     *     Position to clear
     *
     * @return this, for chaining
     */
    @NotNull
    public ItemMenu clearItem(final int position) {
        items[position] = new MenuItem("", new ItemStack(Material.AIR));
        return this;
    }

    /**
     * Remove a {@link MenuItem MenuItem} at a common position in the ItemMenu
     *
     * @param position
     *     Position to clear
     *
     * @return this, for chaining
     */
    @NotNull
    public ItemMenu clearItem(@NotNull final CommonPos position) {
        items[position.getPos()] = new MenuItem("", new ItemStack(Material.AIR));
        return this;
    }

    /**
     * Remove a {@link MenuItem MenuItem} at a common position in the ItemMenu
     *
     * @param position
     *     Position to clear
     * @param row
     *     What row of the item to clear
     *
     * @return this, for chaining
     */
    @NotNull
    public ItemMenu clearItem(@NotNull final CommonPos position, @NotNull final Size row) {
        items[position.getPos(row)] = new MenuItem("", new ItemStack(Material.AIR));
        return this;
    }

    /**
     * Remove all {@link MenuItem MenuItem}s in the ItemMenu
     *
     * @return this, for chaining
     */
    @NotNull
    public ItemMenu clearAllItems() {
        items = new MenuItem[getSize().getSize()];
        return this;
    }

    /**
     * @return The ItemMenu's {@link Size}.
     */
    @Nullable
    public Size getSize() {
        return size;
    }

    /**
     * Fills all empty slots in the ItemMenu with the default {@link ItemMenu#EMPTY_SLOT_ITEM
     * EMPTY_SLOT_ITEM}.
     *
     * @return this, for chaining
     */
    @NotNull
    public ItemMenu fillEmptySlots() {
        return fillEmptySlots(EMPTY_SLOT_ITEM);
    }

    /**
     * Fills all empty slots in the ItemMenu with a certain {@link MenuItem MenuItem}.
     *
     * @param menuItem
     *     The {@link MenuItem MenuItem} to fill with.
     *
     * @return this, for chaining
     */
    @NotNull
    public ItemMenu fillEmptySlots(final MenuItem menuItem) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = menuItem;
            }
        }
        return this;
    }

    /**
     * Handles InventoryClickEvents for the {@link MenuItem}.
     */
    void onInventoryClick(@NotNull final InventoryClickEvent event) {
        if (event.getClick() == ClickType.LEFT || event.getClick() == ClickType.RIGHT ||
            event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT) {
            final int slot = event.getRawSlot();
            if (slot >= 0 && slot < size.getSize() && items[slot] != null) {
                final Player player = (Player) event.getWhoClicked();
                final ItemClickEvent itemClickEvent =
                    new ItemClickEvent(player, event.getCurrentItem(), event.getClick(), this, slot);
                items[slot].onItemClick(itemClickEvent);
                if (itemClickEvent.willUpdate()) {
                    update(player);
                }
                else {
                    //noinspection deprecation
                    player.updateInventory();
                    if (itemClickEvent.willClose()) {
                        final UUID playerUUID = player.getUniqueId();
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            final Player p = Bukkit.getPlayer(playerUUID);
                            if (p != null) {
                                p.closeInventory();
                            }
                        }, 1L);
                    }
                    if (itemClickEvent.willGoBack() && hasParent()) {
                        final UUID playerUUID = player.getUniqueId();
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            final Player p = Bukkit.getPlayer(playerUUID);
                            if (p != null) {
                                p.closeInventory();
                                parent.open(p);
                            }
                        }, 1L);
                    }
                }
            }
        }
    }

    /**
     * Updates the ItemMenu for a player.
     *
     * @param player
     *     The player to update the ItemMenu for.
     *
     * @return this, for chaining
     */
    @NotNull
    public ItemMenu update(@NotNull final Player player) {
        if (player.getOpenInventory() != null) {
            final Inventory inventory = player.getOpenInventory().getTopInventory();
            if (inventory.getHolder() instanceof ItemMenuHolder &&
                ((ItemMenuHolder) inventory.getHolder()).getMenu().equals(this)) {
                apply(inventory, player);
                //noinspection deprecation
                player.updateInventory();
            }
        }
        return this;
    }

    /**
     * Checks if the ItemMenu has a parent.
     *
     * @return True if the ItemMenu has a parent, else false.
     */
    public boolean hasParent() {
        return parent != null;
    }

    /**
     * Opens the ItemMenu for a player.
     *
     * @param player
     *     The player.
     *
     * @return this, for chaining
     */
    @NotNull
    public ItemMenu open(@NotNull final Player player) {
        if (!ItemMenuListener.getInstance().isRegistered(plugin)) {
            ItemMenuListener.getInstance().register(plugin);
        }

        final Inventory inventory = Bukkit
            .createInventory(new ItemMenuHolder(this, Bukkit.createInventory(player, size.getSize())), size.getSize(), name);
        apply(inventory, player);
        player.openInventory(inventory);
        return this;
    }

    /**
     * Applies the {@link MenuItem MenuItem}s for a player to an Inventory.
     *
     * @param inventory
     *     The Inventory.
     * @param player
     *     The Player.
     *
     * @return this, for chaining
     */
    @NotNull
    private ItemMenu apply(@NotNull final Inventory inventory, final Player player) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                inventory.setItem(i, items[i].getFinalIcon(player));
            }
        }
        return this;
    }

    /**
     * Destroys the ItemMenu.
     */
    public void destroy() {
        name = null;
        size = null;
        items = null;
        parent = null;
    }
}

