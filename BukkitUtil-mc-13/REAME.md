# BukkitUtils for Minecraft 1.13 and above

The utilities in this module are meant to supplement, or otherwise fix breaking changes that has occurred due to [the flattening](https://minecraft.gamepedia.com/Java_Edition_1.13/Flattening).

## How to use this module

1. Either fix all errors by calling `no.kh498.util.BukkitUtil.fixBukkitUtil()`
2. Call each wanted fix in `no.kh498.util.BukkitUtil` individually

## Known breaking changes

* Some predefined MenuItems are using old material
    * Mitigate by using the items found in `no.kh498.util.itemMenus.items.mc13`
