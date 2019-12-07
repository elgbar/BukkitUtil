package no.kh498.util.regionEvents;

import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.session.MoveType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * @author Elg
 */
public interface RegionMove {

    boolean testNewRegion(@NotNull Set<ProtectedRegion> entered, @NotNull Set<ProtectedRegion> exited);

    boolean callEvent(@NotNull Player player, @NotNull Location from, @NotNull Location to,
                      @NotNull Set<ProtectedRegion> entered, @NotNull Set<ProtectedRegion> exited,
                      @NotNull MoveType moveType, boolean hasBypass);

    StateFlag getFlagType();
}
