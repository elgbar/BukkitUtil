package no.kh498.util.jackson

import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ser.PropertyWriter
import com.fasterxml.jackson.databind.type.CollectionType
import com.fasterxml.jackson.databind.type.MapType
import no.kh498.util.jackson.mixIn.ItemMetaMixIn.Companion.DISPLAY_NAME
import no.kh498.util.jackson.mixIn.ItemMetaMixIn.Companion.ENCHANTMENT
import no.kh498.util.jackson.mixIn.ItemMetaMixIn.Companion.FLAGS
import no.kh498.util.jackson.mixIn.ItemMetaMixIn.Companion.LORE
import no.kh498.util.jackson.mixIn.ItemMetaMixIn.Companion.REPAIR_COST
import no.kh498.util.jackson.mixIn.ItemMetaMixIn.Companion.TYPE_FIELD
import no.kh498.util.jackson.mixIn.ItemMetaMixIn.Companion.UNBREAKABLE
import no.kh498.util.jackson.mixIn.meta.*
import no.kh498.util.jackson.mixIn.meta.BannerMetaMixIn.Companion.BASE
import no.kh498.util.jackson.mixIn.meta.BannerMetaMixIn.Companion.PATTERNS
import no.kh498.util.jackson.mixIn.meta.EnchantmentStorageMetaMixIn.Companion.STORED_ENCHANTMENTS
import no.kh498.util.jackson.mixIn.meta.FireworkEffectMetaMixIn.Companion.EXPLOSION
import no.kh498.util.jackson.mixIn.meta.FireworkMetaMixIn.Companion.EXPLOSIONS
import no.kh498.util.jackson.mixIn.meta.FireworkMetaMixIn.Companion.FLIGHT
import no.kh498.util.jackson.mixIn.meta.LeatherArmorMetaMixIn.Companion.COLOR
import org.bukkit.Color
import org.bukkit.DyeColor
import org.bukkit.FireworkEffect
import org.bukkit.block.banner.Pattern
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.meta.*
import org.bukkit.potion.PotionData
import org.bukkit.potion.PotionEffect
import org.junit.Test
import kotlin.reflect.KClass
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * @author Elg
 */
class ItemMetaIntrospection : BukkitSerTestHelper() {

    private fun KClass<*>.type(): JavaType {
        return type(mapper)
    }

    private fun Class<*>.type(): JavaType {
        return type(mapper.typeFactory)
    }

    private fun KClass<*>.listType(): CollectionType {
        return mapper.typeFactory.constructCollectionType(List::class.java, this.java)
    }

    private fun KClass<*>.setType(): CollectionType {
        return mapper.typeFactory.constructCollectionType(Set::class.java, this.java)
    }

    private fun mapType(key: Class<*>, value: Class<*>): MapType {
        return mapper.typeFactory.constructMapType(MutableMap::class.java, key, value)
    }

    private fun verifySubMeta(metaType: KClass<out ItemMeta>, extraProps: Map<String, JavaType>) {

        val mixIn = mapper.findMixInClassFor(metaType.java)
        val realType = mixIn ?: metaType.java

        val ser = mapper.serializerProviderInstance.findValueSerializer(realType.type())

        val presentProps = HashMap<String, JavaType>().apply {
            ser.properties().forEach { prop: PropertyWriter -> this[prop.name] = prop.type }
        }

        fun checkContains(prop: String, type: JavaType) {
            assertNotNull(presentProps[prop],
                    "Failed to find the property '$prop'. Expected properties in class '${metaType.simpleName}' are ${presentProps.keys}")
            assertEquals(type, presentProps[prop], "Property $prop has the wrong type")
        }

        val baseProps = HashMap<String, JavaType>()

        baseProps[DISPLAY_NAME] = String::class.type()
        baseProps[TYPE_FIELD] = String::class.type()
        baseProps[UNBREAKABLE] = Boolean::class.type()
        baseProps[REPAIR_COST] = Int::class.type()
        baseProps[FLAGS] = ItemFlag::class.setType()
        baseProps[LORE] = String::class.listType()
        baseProps[ENCHANTMENT] = mapType(Enchantment::class.java, Int::class.javaObjectType)

        val expectedProps = HashMap<String, JavaType>(baseProps).apply { putAll(extraProps) }

        expectedProps.forEach { (t, u) -> checkContains(t, u) }

        val a = HashSet(expectedProps.keys).apply { removeAll(presentProps.keys) }
        val b = HashSet(presentProps.keys).apply { removeAll(expectedProps.keys) }

        assertTrue(a.isEmpty(), "There are some expected properties not present: $a")
        assertTrue(b.isEmpty(), "Did not expect to find the following properties: $b")
    }

    @Test
    fun verifyPropertiesFor_ItemMeta() {
        verifySubMeta(ItemMeta::class, emptyMap())
    }

    @Test
    fun verifyPropertiesFor_LeatherArmorMeta() {
        verifySubMeta(LeatherArmorMeta::class, mapOf(COLOR to Color::class.type()))
    }

    @Test
    fun verifyPropertiesFor_BookMeta() {
        verifySubMeta(BookMeta::class, mapOf(
                "title" to String::class.type(),
                "author" to String::class.type(),
//                "resolved" to Boolean::class.type(), //TODO What does this do??
                "generation" to BookMeta.Generation::class.type(),
                "pages" to String::class.listType()
        ))
    }

    @Test
    fun verifyPropertiesFor_BannerMeta() {
        verifySubMeta(BannerMeta::class,
                mapOf(BASE to DyeColor::class.type(), PATTERNS to Pattern::class.listType()))
    }


    @Test
    fun verifyPropertiesFor_EnchantmentStorageMeta() {
        verifySubMeta(EnchantmentStorageMeta::class,
                mapOf(STORED_ENCHANTMENTS to mapType(Enchantment::class.java, Int::class.javaObjectType)))
    }

    @Test
    fun verifyPropertiesFor_FireworkEffectMeta() {
        verifySubMeta(FireworkEffectMeta::class, mapOf(EXPLOSION to FireworkEffect::class.type()))
    }

    @Test
    fun verifyPropertiesFor_FireworkMeta() {
        verifySubMeta(FireworkMeta::class, mapOf(EXPLOSIONS to FireworkEffect::class.listType(), FLIGHT to Int::class.type()))
    }

    @Test
    fun verifyPropertiesFor_KnowledgeBookMetaMixIn() {
        verifySubMeta(KnowledgeBookMeta::class, mapOf(KnowledgeBookMetaMixIn.BOOK_RECIPES to String::class.listType()))
    }

    @Test
    fun verifyPropertiesFor_MapMetaMixIn() {
        verifySubMeta(MapMeta::class, mapOf(
                MapMetaMixIn.MAP_COLOR to Color::class.type(),
                MapMetaMixIn.MAP_LOC_NAME to String::class.type(),
                MapMetaMixIn.MAP_SCALING to Byte::class.type()
        ))
    }

    @Test
    fun verifyPropertiesFor_PotionMetaMixIn() {
        verifySubMeta(PotionMeta::class, mapOf(
                PotionMetaMixIn.DEFAULT_POTION to PotionData::class.type(),
                PotionMetaMixIn.POTION_COLOR to Color::class.type(),
                PotionMetaMixIn.POTION_EFFECTS to PotionEffect::class.listType()
        ))
    }

    @Test
    fun verifyPropertiesFor_SkullMetaMixIn() {
        verifySubMeta(SkullMeta::class, mapOf(SkullMetaMixIn.SKULL_OWNER to String::class.type()))
    }

    @Test
    fun verifyPropertiesFor_SpawnEggMetaMixIn() {
        verifySubMeta(SpawnEggMeta::class, mapOf(SpawnEggMetaMixIn.ENTITY_ID to String::class.type()))
    }

//    @Test
//    fun verifyPropertiesFor_() {
//        verifySubMeta(::class, mapOf("color" to Color::class.type()))
//    }
}
