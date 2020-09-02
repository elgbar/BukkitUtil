package no.kh498.util;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Elg
 */
public class InventoryUtilTest extends TestHelper {

  @Test
  public void testCountOneEqualElement() {
    int amount = 16;
    ItemStack item = new ItemStack(Material.APPLE, amount);

    Inventory inv = mock(Inventory.class);
    when(inv.getContents()).thenReturn(new ItemStack[] {item});

    Assert.assertEquals(amount, InventoryUtil.count(inv, item));
  }

  @Test
  public void testCountNoItems() {

    Inventory inv = mock(Inventory.class);
    when(inv.getContents()).thenReturn(new ItemStack[] {});

    Assert.assertEquals(0, InventoryUtil.count(inv, new ItemStack(Material.APPLE)));
  }

  @Test
  public void testCountDuplicate() {
    int amount = 16;
    ItemStack item = new ItemStack(Material.APPLE, amount);

    Inventory inv = mock(Inventory.class);
    when(inv.getContents()).thenReturn(new ItemStack[] {item, item});

    Assert.assertEquals(2 * amount, InventoryUtil.count(inv, item));
  }

  @Test
  public void testCountDuplicateNewInstance() {
    int amount = 16;
    ItemStack itemA = new ItemStack(Material.APPLE, amount);
    ItemStack itemB = new ItemStack(Material.APPLE, amount * 2);

    Inventory inv = mock(Inventory.class);
    when(inv.getContents()).thenReturn(new ItemStack[] {itemA, itemB});

    assertTrue(itemA.isSimilar(itemB));

    Assert.assertEquals(amount * 3, InventoryUtil.count(inv, itemA));
  }

  @Test
  public void testCountOtherItems() {
    int amount = 16;
    ItemStack itemA = new ItemStack(Material.APPLE, amount);
    ItemStack itemB = new ItemStack(Material.GOLDEN_APPLE, amount / 2);

    Inventory inv = mock(Inventory.class);
    when(inv.getContents()).thenReturn(new ItemStack[] {itemA, itemB});

    Assert.assertEquals(amount, InventoryUtil.count(inv, itemA));
  }

}
