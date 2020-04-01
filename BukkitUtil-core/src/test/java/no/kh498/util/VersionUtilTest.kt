package no.kh498.util

import no.kh498.util.VersionUtil.*
import org.bukkit.craftbukkit.v1_x_Ry.JacksonMockServer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.modules.junit4.PowerMockRunner

/**
 * @author Elg
 */
@RunWith(PowerMockRunner::class)
class VersionUtilTest {

    init {
        JacksonMockServer
    }

    @Test
    fun nmsClass() {
        assertNotNull(getNmsVersion())
    }

    @Throws(ClassNotFoundException::class)
    @Test
    fun versionedClass() {
        val expectedClass = Class.forName("$CB_PACKAGE.${getNmsVersion()}.${JacksonMockServer::class.simpleName}")
        assertNotNull(expectedClass)
        assertEquals(expectedClass, getVersionedClass(CB_PACKAGE, JacksonMockServer::class.simpleName))
    }

    @Throws(ClassNotFoundException::class)
    @Test
    fun cBClass() {
        val expectedClass = Class.forName("$CB_PACKAGE.${getNmsVersion()}.${JacksonMockServer::class.simpleName}")
        assertNotNull(expectedClass)
        assertEquals(expectedClass, getCBClass(JacksonMockServer::class.simpleName))
    }
}
