package jackson.deserializers

import org.bukkit.World
import org.bukkit.craftbukkit.v1_x_Ry.JacksonMockServer
import org.junit.Test
import org.powermock.api.mockito.PowerMockito
import java.util.*

/**
 * @author Elg
 */
class WorldTest : BukkitSerTestHelper() {
    @Test
    fun serialize() {
        world = PowerMockito.mock(World::class.java)
        PowerMockito.`when`(world.name).thenReturn("World")
        PowerMockito.`when`(world.uid).thenReturn(UUID.nameUUIDFromBytes(byteArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)))

        JacksonMockServer.worlds += world

        testSerAll(world)
    }
}
