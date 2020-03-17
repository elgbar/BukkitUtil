package no.kh498.util

import no.kh498.util.VersionUtil.CB_PACKAGE
import no.kh498.util.VersionUtil.getCBClass
import no.kh498.util.VersionUtil.getVersionedClass
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_8_R3.CraftServer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito
import org.powermock.api.mockito.PowerMockito.`when`
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

/**
 * @author Elg
 */
@RunWith(PowerMockRunner::class)
@PrepareForTest(Bukkit::class, CraftServer::class)
class VersionUtilTest {
    @Before
    fun setUp() {
        PowerMockito.mockStatic(Bukkit::class.java)
        `when`(Bukkit.getServer()).thenReturn(PowerMockito.mock(CraftServer::class.java))
    }

    @Test
    fun nmsClass() {
        assertNotNull(VersionUtil.getNmsVersion())
    }

    @Throws(ClassNotFoundException::class)
    @Test
    fun versionedClass() {
        val expectedClass = Class.forName("$CB_PACKAGE.${VersionUtil.getNmsVersion()}.CraftServer")
        assertNotNull(expectedClass)
        assertEquals(expectedClass, getVersionedClass(CB_PACKAGE, "CraftServer"))
    }

    @Throws(ClassNotFoundException::class)
    @Test
    fun cBClass() {
        val expectedClass = Class.forName("$CB_PACKAGE.${VersionUtil.getNmsVersion()}.CraftServer")
        assertNotNull(expectedClass)
        assertEquals(expectedClass, getCBClass("CraftServer"))
    }
}
