package no.kh498.util.jackson

import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ser.PropertyWriter
import no.kh498.util.jackson.mixIn.ItemMetaMixIn.Companion.DISPLAY_NAME
import no.kh498.util.jackson.mixIn.ItemMetaMixIn.Companion.ENCHANTMENT
import no.kh498.util.jackson.mixIn.ItemMetaMixIn.Companion.FLAGS
import no.kh498.util.jackson.mixIn.ItemMetaMixIn.Companion.LORE
import no.kh498.util.jackson.mixIn.ItemMetaMixIn.Companion.UNBREAKABLE
import org.bukkit.Color
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.junit.Test
import kotlin.reflect.KClass
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * @author Elg
 */
class ItemMetaIntrospection : BukkitSerTestHelper() {


    private fun KClass<*>.type(): JavaType {
        return mapper.typeFactory.constructType(this.java)
    }

    private fun verifySubMeta(metaType: KClass<out ItemMeta>, extraProps: Map<String, JavaType>) {

        val ser = mapper.serializerProviderInstance.findValueSerializer(metaType.type())
        val presentProps = HashMap<String, JavaType>().apply {
            ser.properties().forEach { prop: PropertyWriter -> this[prop.name] = prop.type }
        }

        fun checkContains(prop: String, type: JavaType) {
            assertNotNull(presentProps[prop], "Failed to find the property '$prop'. All properties are ${presentProps.keys}")
            assertEquals(type, presentProps[prop], "Property $prop has the wrong type")
        }

        val baseProps = HashMap<String, JavaType>()

        baseProps[DISPLAY_NAME] = String::class.type()
        baseProps[UNBREAKABLE] = Boolean::class.type()
        baseProps[FLAGS] = mapper.typeFactory.constructCollectionType(Set::class.java, ItemFlag::class.java)
        baseProps[LORE] = mapper.typeFactory.constructCollectionType(List::class.java, String::class.java)
        baseProps[ENCHANTMENT] =
                mapper.typeFactory.constructMapType(MutableMap::class.java, Enchantment::class.java, Int::class.javaObjectType)

        val expectedProps = HashMap<String, JavaType>(baseProps).apply { putAll(extraProps) }

        expectedProps.forEach { (t, u) -> checkContains(t, u) }

        assertEquals(expectedProps.keys, presentProps.keys, "Mismatch between expected keys and keys present")
    }

    @Test
    fun findBarePropertiesForRawItemMeta() {
        verifySubMeta(ItemMeta::class, emptyMap())
    }

    @Test
    fun findBarePropertiesForLeatherArmourMeta() {
        verifySubMeta(LeatherArmorMeta::class, mapOf("color" to Color::class.type()))
    }

//    @Test
//    fun findBarePropertiesForBookItemMeta() {
//        verifySubMeta(BookMeta::class, mapOf("color" to Color::class.type()))
//    }
//
//    @Test
//    fun findBarePropertiesForBlockStateMeta() {
//        verifySubMeta(BlockStateMeta::class, mapOf("color" to Color::class.type()))
//    }

//    @Test
//    fun findBarePropertiesForBlockDataMeta() {
//        verifySubMeta(BlockDataMeta::class, mapOf("color" to Color::class.type()))
//    }
//
//    @Test
//    fun findBarePropertiesFor() {
//        verifySubMeta(::class, mapOf("color" to Color::class.type()))
//    }
}
