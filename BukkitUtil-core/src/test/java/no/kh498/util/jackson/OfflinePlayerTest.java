package no.kh498.util.jackson;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.junit.Test;

import java.util.Map;
import java.util.UUID;

import static org.powermock.api.mockito.PowerMockito.when;

/**
 * @author Elg
 */
public class OfflinePlayerTest extends BukkitSerTestHelper {


    private static class OfflinePlayerTestImpl implements OfflinePlayer {

        UUID uuid;

        @Override
        public boolean isOnline() {
            return false;
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public UUID getUniqueId() {
            return uuid;
        }

        @Override
        public boolean isBanned() {
            return false;
        }

        @Override
        public void setBanned(boolean banned) {

        }

        @Override
        public boolean isWhitelisted() {
            return false;
        }

        @Override
        public void setWhitelisted(boolean value) {

        }

        @Override
        public Player getPlayer() {
            return null;
        }

        @Override
        public long getFirstPlayed() {
            return 0;
        }

        @Override
        public long getLastPlayed() {
            return 0;
        }

        @Override
        public boolean hasPlayedBefore() {
            return false;
        }

        @Override
        public Location getBedSpawnLocation() {
            return null;
        }

        @Override
        public Map<String, Object> serialize() {
            return null;
        }

        @Override
        public boolean isOp() {
            return false;
        }

        @Override
        public void setOp(boolean value) {

        }
    }

    @Test
    public void serFromUUID() {

        UUID playerUUID = UUID.randomUUID();

        OfflinePlayerTestImpl player = new OfflinePlayerTestImpl();
        player.uuid = playerUUID;

        when(Bukkit.getOfflinePlayer(playerUUID)).thenReturn(player);

        testSerAll(player);
    }
}
