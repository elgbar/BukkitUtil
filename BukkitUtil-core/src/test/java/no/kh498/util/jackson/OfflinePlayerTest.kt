package no.kh498.util.jackson

import com.fasterxml.jackson.core.JsonProcessingException
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.junit.Assert
import org.junit.Test
import org.powermock.api.mockito.PowerMockito
import java.util.*

/**
 * @author Elg
 */
class OfflinePlayerTest : BukkitSerTestHelper() {
    @Test
    @Throws(JsonProcessingException::class)
    fun serFromUUID() {
        val playerUUID = UUID.randomUUID()
        val mock = PowerMockito.mock(OfflinePlayer::class.java)
        PowerMockito.`when`(Bukkit.getOfflinePlayer(playerUUID)).thenReturn(mock)
        
        val player = mapper.readValue("\"$playerUUID\"", OfflinePlayer::class.java)
        Assert.assertSame(mock, player)
    }
}
