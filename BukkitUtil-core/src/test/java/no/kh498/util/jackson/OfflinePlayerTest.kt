package no.kh498.util.jackson

import com.fasterxml.jackson.core.JsonProcessingException
import org.bukkit.OfflinePlayer
import org.bukkit.craftbukkit.v1_x_Ry.JacksonMockServer
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.`when`
import org.powermock.api.mockito.PowerMockito.mock
import java.util.*

/**
 * @author Elg
 */
class OfflinePlayerTest : BukkitSerTestHelper() {

    @Test
    @Throws(JsonProcessingException::class)
    fun serFromUUID() {
        val playerUUID = UUID.randomUUID()

        val mockPlayer = mock(OfflinePlayer::class.java)
        `when`(mockPlayer.uniqueId).thenReturn(playerUUID)
        JacksonMockServer.offlinePlayers += mockPlayer

        val player = mapper.readValue("\"$playerUUID\"", OfflinePlayer::class.java)
        Assert.assertSame(mockPlayer, player)
    }
}
