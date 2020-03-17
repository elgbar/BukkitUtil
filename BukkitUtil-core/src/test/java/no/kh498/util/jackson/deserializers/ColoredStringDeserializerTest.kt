package no.kh498.util.jackson.deserializers

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import no.kh498.util.jackson.BukkitModule
import no.kh498.util.jackson.BukkitSerTestHelper
import org.junit.Test
import kotlin.test.assertEquals

/**
 * @author Elg
 */
class ColoredStringDeserializerTest : BukkitSerTestHelper() {

    class ExplicitDeserTestClass {
        @JsonProperty(PROP_PATH)
        @JsonDeserialize(using = ColoredStringDeserializer::class)
        lateinit var test: String
    }

    class ImplicitDeserTestClass {

        @JsonProperty(PROP_PATH)
        lateinit var test: String
    }

    @Test
    internal fun deserializeIntoColor() {
        val value = "&c&gWow!\r\t\n\r\n"

        //{"prop":"&c&gWow!\r\t\n\r\n"}
        val json = ObjectMapper().writeValueAsString(ExplicitDeserTestClass().apply { test = value })
        val obj = ObjectMapper().readValue(json, ExplicitDeserTestClass::class.java)

        assertEquals("§c&gWow!\n    \n\n", obj.test)
    }


    @Test
    fun colorizeIfSpesified() {
        val mapper = ObjectMapper().apply {
            registerModule(BukkitModule(true))
        }

        val json = mapper.writeValueAsString(ImplicitDeserTestClass().apply { test = "&cMuch!" })

        val obj = mapper.readValue(json, ImplicitDeserTestClass::class.java)
        assertEquals("§cMuch!", obj.test)
    }

    @Test
    fun noColorChangeByDefault() {
        val mapper = ObjectMapper().apply {
            registerModule(BukkitModule())
        }

        val str = "&cColor!"

        val json = mapper.writeValueAsString(ImplicitDeserTestClass().apply { test = str })

        val obj = mapper.readValue(json, ImplicitDeserTestClass::class.java)
        assertEquals(str, obj.test)
    }

    companion object {
        const val PROP_PATH = "prop"
    }
}
