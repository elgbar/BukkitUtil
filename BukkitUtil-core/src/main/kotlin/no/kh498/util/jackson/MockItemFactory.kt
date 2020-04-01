package no.kh498.util.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.inventory.ItemFactory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.*

/**
 * @author Elg
 */
internal class MockItemFactory(private val mapper: ObjectMapper) : ItemFactory {
    override fun asMetaFor(meta: ItemMeta?, stack: ItemStack?) = meta
    override fun asMetaFor(meta: ItemMeta?, material: Material?) = meta
    override fun equals(meta1: ItemMeta?, meta2: ItemMeta?) = true
    override fun isApplicable(meta: ItemMeta?, stack: ItemStack?) = true
    override fun isApplicable(meta: ItemMeta?, material: Material?) = true

    override fun getDefaultLeatherColor(): Color = Color.WHITE

    override fun getItemMeta(material: Material?): ItemMeta? {
        if (material == null || material == Material.AIR) return null
        val metaClass: Class<out ItemMeta> = when (material.ordinal) {
            24, 26, 34, 53, 55, 62, 85, 117, 138, 139, 147, 152, 155, 159, 179, 211, 212, 282, 338, 349, 363, 401 -> BlockStateMeta::class
            257, 258, 259, 260 -> LeatherArmorMeta::class
            317 -> MapMeta::class
            332, 397, 399, 400 -> PotionMeta::class
            345, 346 -> BookMeta::class
//            346 -> ((if (meta is BookMeta) meta else CraftMetaBookSigned(meta)) as ItemMeta)!!
            356 -> SkullMeta::class
            360 -> FireworkEffectMeta::class
//            361 -> ((if (meta is CraftMetaCharge) meta else CraftMetaCharge(meta)) as ItemMeta)!!
            362 -> BookMeta::class
            384 -> BannerMeta::class
            else -> ItemMeta::class
        }.java

        return mapper.convertValue(emptyMap<String, Any>(), metaClass)
    }
}
