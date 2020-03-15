package no.kh498.util.jackson.deserializers

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.junit.Test
import kotlin.test.assertEquals

/**
 * @author Elg
 */
class ColoredStringDeserializerTest {

    class TestClass {

        @JsonProperty(PROP_PATH)
        @JsonDeserialize(using = ColoredStringDeserializer::class)
        lateinit var test: String

    }

    @Test
    internal fun deserializeIntoColor() {
        val value = "&c&gWow!\r\t\n\r\n"

        //{"prop":"&c&gWow!\r\t\n\r\n"}
        val json = ObjectMapper().writeValueAsString(TestClass().also { it.test = value })
//        println("json = $json")

        val obj = ObjectMapper().readValue(json, TestClass::class.java)

        assertEquals("§c&gWow!\n    \n\n", obj.test)
    }

    companion object {
        const val PROP_PATH = "prop"
    }
}
