package no.kh498.util;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.NotNull;

/**
 * @author Elg
 * @author zachoooo
 * @since 0.1.0
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public final class InventoryUtil {

    /**
     * Remove an ItemStack (from a inventory even if the items are spread out
     * <p>
     * Based on <a href="https://bukkit.org/threads/remove-items-from-an-inventory.27853/#post-1790751">this</a> bukkit
     * thread
     * <p>
     * An item stack will be created based on {@code type}, {@code amount} and {@code data}
     *
     * @param inv
     *     inventory to check
     * @param type
     *     The malarial to remove
     * @param amount
     *     The amount of {@code material} to remove
     * @param data
     *     The data, if you only want to remove material use {@link #removeInvMat(Inventory, Material, int)}
     */
    @SuppressWarnings("deprecation")
    public static void removeInvItem(final Inventory inv, final Material type, final int amount,
                                     final MaterialData data) {
        removeInvItem(inv, new ItemStack(type, amount, data.getData()), false);
    }

    /**
     * Remove an ItemStack (from a inventory even if the items are spread out
     * <p>
     * Based on <a href="https://bukkit.org/threads/remove-items-from-an-inventory.27853/#post-1790751">this</a> bukkit
     * thread
     *
     * @param inv
     *     inventory to check
     * @param item
     *     the item to remove
     * @param matOnly
     *     If only the material should be checked, if true {@link ItemStack#isSimilar(ItemStack)} will not be called
     */
    public static void removeInvItem(final Inventory inv, final ItemStack item, final boolean matOnly) {
        final ItemStack[] items = inv.getContents();
        int amount = item.getAmount();

//        final ItemStack AIR = new ItemStack(Material.AIR);

        for (int i = 0; i < items.length; i++) {
            final ItemStack is = items[i];
            //this will work as isSimilar is checking for the type
            if (is != null && ((matOnly && item.getType() == is.getType()) || is.isSimilar(item))) {
                final int newAmount = is.getAmount() - amount;

                if (newAmount > 0) {
                    //only partially remove items from an itemstack
                    //this also means we are done removing items from the inventory
                    is.setAmount(newAmount);
                    break;
                }
                else {
                    items[i] = null; //replace the item with air
                    amount -= newAmount;
                    if (amount == 0) {
                        break;
                    }
                }
            }
        }
        inv.setContents(items);
    }

    /**
     * Remove an ItemStack (from a inventory even if the items are spread out
     * <p>
     * Based on <a href="https://bukkit.org/threads/remove-items-from-an-inventory.27853/#post-1790751">this</a> bukkit
     * thread
     *
     * @param inv
     *     inventory to check
     * @param item
     *     the item to remove
     */
    public static void removeInvItem(final Inventory inv, final ItemStack item) {
        removeInvItem(inv, item, false);
    }

    /**
     * Remove {@code amount} items of the type {@code material} in the {@code inv} inventory
     *
     * @param inv
     *     The inventory to remove from
     * @param material
     *     The material to remove
     * @param amount
     *     The amount of {@code material} to remove
     */
    public static void removeInvMat(final Inventory inv, final Material material, final int amount) {
        removeInvItem(inv, new ItemStack(material, amount), true);
    }

    /**
     * @param inv
     *     Inventory to check
     * @param item
     *     Item to check (amount from this variable is ignored)
     * @param amount
     *     The amount of {@code item}
     *
     * @return if an inventory contains a certain amount of items with the amount of the items set by {@code amount}
     */
    public static boolean contains(final Inventory inv, final ItemStack item, int amount) {
        if (item == null || inv == null) {
            return false;
        }
        else if (amount <= 0) {
            return true;
        }
        else {
            final ItemStack[] invContent = inv.getContents();

            for (final ItemStack is : invContent) {
                if (item.isSimilar(is)) {
                    amount -= is.getAmount();
                    if (amount <= 0) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    /**
     * @param inv
     *     The inventory to check
     * @param type
     *     The {@link Material} to check for
     *
     * @return The total amount of items with the {@link Material} {@code type}
     */
    public static int getTotalItems(final Inventory inv, final Material type) {
        int amount = 0;
        for (final ItemStack is : inv.getContents()) {
            if (is != null && is.getType() == type) {
                amount += is.getAmount();
            }
        }
        return amount;
    }

    /**
     * @param player
     *     The player's inventory to check
     * @param material
     *     What material to check
     *
     * @return The number of items that is a certain material
     */
    public static int countItems(final Player player, final Material material) {
        return countItems(player, material, (short) -1);
    }

    /**
     * @param player
     *     The player's inventory to check
     * @param material
     *     What material to check
     * @param damageValue
     *     What damageValue of the material to check (-1 to ignore damage value)
     *
     * @return The number of items has the same {@code material} and {@code damageValue} as specified
     */
    public static int countItems(final Player player, final Material material, final short damageValue) {
        Preconditions.checkNotNull(player);
        final PlayerInventory inventory = player.getInventory();
        final ItemStack[] items = inventory.getContents();
        int amount = 0;
        final boolean ignoreDmgVal = damageValue < 0;
        for (final ItemStack item : items) {
            if (item != null && item.getType() == material && (ignoreDmgVal || item.getDurability() == damageValue)) {
                amount += item.getAmount();
            }
        }
        return amount;
    }

    /**
     * @return How many of the given item is in in the given inventory
     */
    public static int count(@NotNull Inventory inv, @NotNull ItemStack item) {
        int amount = 0;
        for (ItemStack invItem : inv.getContents()) {
            if (item.isSimilar(invItem)) {
                amount += invItem.getAmount();
            }
        }
        return amount;
    }

    /**
     * @param player
     *     The player's inventory to check
     * @param item
     *     item to check if the player can hold
     *
     * @return true if the player can hold the item {@code new ItemStack(material, quantity, data)}
     */
    @SuppressWarnings("deprecation")
    public static boolean canHold(final Player player, final ItemStack item) {
        return canHold(player, item.getType(), item.getData().getData(), item.getAmount());
    }

    /**
     * @param player
     *     The player's inventory to check
     * @param material
     *     The material of the item
     * @param data
     *     The data of the item
     * @param quantity
     *     The amount of item that you want to check
     *
     * @return true if the player can hold the item {@code new ItemStack(material, quantity, data)}
     */
    public static boolean canHold(final Player player, final Material material, final byte data, final int quantity) {
        final PlayerInventory playerInv = player.getInventory();

        final int emptySlots = emptySlots(playerInv);

        // Reference item with one in quantity
        final ItemStack checkItem = new ItemStack(material, 1, data);

        // if the amount of empty slots fit our need return true
        if (emptySlots >= Math.ceil((float) quantity / (float) material.getMaxStackSize())) {
            return true;
        }
        else if (!playerInv.contains(material)) {
            // Guaranteed to be no space for the item.
            return false;
        }
        // here there isn't an empty slot or all the items cant be fitted into the empty slots

        // the number of items we need to fit for this to return true;
        int remainingItems = Math.abs((emptySlots * material.getMaxStackSize()) - quantity);

        for (final ItemStack item : playerInv.getContents()) {
            if (item != null && item.isSimilar(checkItem) && material.getMaxStackSize() != item.getAmount()) {
                remainingItems -= material.getMaxStackSize() - item.getAmount();
            }
        }
        return remainingItems <= 0;
    }

    /**
     * @param inv
     *     The inventory to check
     *
     * @return The number of empty slots in an inventory
     */
    public static int emptySlots(final Inventory inv) {
        int slots = 0;
        for (final ItemStack i : inv) {
            if (i == null) {
                slots++;
            }
        }
        return slots;
    }

    /**
     * @param player
     *     Player to get skull of
     *
     * @return The skull of the player
     */
    public static ItemStack getPlayerSkull(final OfflinePlayer player) {
        Preconditions.checkNotNull(player, "player");
        final ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        final SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(player.getName());
        skull.setItemMeta(meta);
        return skull;
    }

    /**
     * @return The armour of the given inventory in the order {@code helmet, chestplate, leggings, boots} or {@code
     * null} if given inventory does no have any armour slots
     *
     * @throws IllegalArgumentException
     *     if the given Inventory is {@code null} or
     */
    public static ItemStack[] getPlayerArmor(Inventory inv) {
        if (inv == null) { throw new IllegalArgumentException("Given inventory is null"); }
        if (inv instanceof PlayerInventory) {
            return new ItemStack[] {((PlayerInventory) inv).getHelmet(), ((PlayerInventory) inv).getChestplate(),
                ((PlayerInventory) inv).getLeggings(), ((PlayerInventory) inv).getBoots()};
        }
        else if (inv.getType() == InventoryType.CRAFTING) {
            return new ItemStack[] {inv.getItem(103), inv.getItem(102), inv.getItem(101), inv.getItem(100)};
        }
        return null;
    }

}
