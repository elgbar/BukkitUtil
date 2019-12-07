package no.kh498.util.itemMenus.api;

import no.kh498.util.itemMenus.events.ItemClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * An Item inside an {@link MenuItem}.
 */
public class MenuItem {

    private String displayName;
    private ItemStack icon;
    private List<String> lore;

    public MenuItem(final String displayName, final ItemStack icon, final String... lore) {
        this.displayName = displayName;
        this.icon = icon;
        this.lore = Arrays.asList(lore);
    }

    public MenuItem(final String displayName, final ItemStack icon, final List<String> lore) {
        this.displayName = displayName;
        this.icon = icon;
        this.lore = lore;
    }

    public void updateItem(@NotNull final MenuItem menuItem) {
        icon = menuItem.icon;
        displayName = menuItem.displayName;
        lore = menuItem.lore;
        setNameAndLore(icon, displayName, lore);
    }

    /**
     * Sets the display name and lore of an ItemStack.
     *
     * @param itemStack
     *     The ItemStack.
     * @param displayName
     *     The display name.
     * @param lore
     *     The lore.
     *
     * @return The ItemStack.
     */
    @NotNull
    public static ItemStack setNameAndLore(final ItemStack itemStack, final String displayName,
                                           final List<String> lore) {
        final ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(displayName);
            meta.setLore(lore);
            itemStack.setItemMeta(meta);
        }
        return itemStack;
    }

    /**
     * Gets the ItemStack to be shown to the player.
     *
     * @param player
     *     The player.
     *
     * @return The final icon.
     */
    public ItemStack getFinalIcon(final Player player) {
        return setNameAndLore(getIcon().clone(), getDisplayName(), getLore());
    }

    /**
     * Gets the icon of the MenuItem.
     *
     * @return The icon.
     */
    public ItemStack getIcon() {
        return icon;
    }

    /**
     * Gets the display name of the MenuItem.
     *
     * @return The display name.
     */
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(final String name) {
        displayName = name;
    }

    /**
     * Gets the lore of the MenuItem.
     *
     * @return The lore.
     */
    public List<String> getLore() {
        return lore;
    }

    public void setLore(final List<String> lore) {
        this.lore = lore;
    }

    public void setIcon(final ItemStack newIcon) {
        icon = newIcon;
    }

    /**
     * Called when the MenuItem is clicked.
     *
     * @param event
     *     The {@link ItemClickEvent}.
     */
    public void onItemClick(final ItemClickEvent event) {
        // Do nothing by default
    }
}
