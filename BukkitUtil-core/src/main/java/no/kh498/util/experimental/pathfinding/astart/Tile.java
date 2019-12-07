package no.kh498.util.experimental.pathfinding.astart;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Elg
 */
public class Tile implements Comparable<Tile> {

    static int NO_ACCESS = 100000;
    private static Logger logger = LoggerFactory.getLogger(Tile.class);
    int x;
    int y;
    int z;
    float h = -1;
    float g = -1;
    Tile prev;
    private float multiplier = -1;
    private Location loc;

    public Tile(final int x, final int y, final int z, final Tile prev) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.prev = prev;
    }

    Tile(final Location location) {
        x = location.getBlockX();
        y = location.getBlockY();
        z = location.getBlockZ();
    }

    /**
     * @param i
     *     Integer to square
     *
     * @return The input squared
     */
    private static int square(final int i) {
        return i * i;
    }

    private static float abovePenalty(final Block b) {

        final Block lo = b.getRelative(BlockFace.UP, 1);
        final Block hi = b.getRelative(BlockFace.UP, 2);

        final Material loMat = lo.getType();
        final Material hiMat = hi.getType();

        final MaterialData loDat = lo.getState().getData();
        final MaterialData hiDat = hi.getState().getData();

        boolean loOpen = false;
        boolean hiOpen = false;

        //if either block can be opened check if one of them is closed
//        if (loDat instanceof Openable || hiDat instanceof Openable) {

        if (loDat instanceof Openable) {
            if (!((Openable) loDat).isOpen()) {
                logger.debug("Looked at openable of lower, was NOT open");
                return NO_ACCESS;
            }
            else {
                logger.debug("Looked at openable of lower, is OPEN");
                loOpen = true;
            }
        }


        if (hiDat instanceof Openable) {
            if (!((Openable) hiDat).isOpen()) {
                logger.debug("Looked at openable of upper, was NOT open");
                return NO_ACCESS;
            }
            else {
                logger.debug("Looked at openable of upper, is OPEN");
                hiOpen = true;
            }
        }


        if ((!loOpen && !AStar.canBeWalkedThrough(loMat)) || (!hiOpen && !AStar.canBeWalkedThrough(hiMat))) {
            return NO_ACCESS;
        }
        if (loMat == Material.WEB || hiMat == Material.WEB) {
            return 20f;
        }
        return 1f;
    }

    /**
     * @param t
     *     Other tile to find the distance to
     *
     * @return The squared distance between {@code this} tile and {@code t} tile
     */
    int distanceSquared(@NotNull final Tile t) {
        return square(x - t.x) + square(y - t.y) + square(z - t.z);
    }

    void calcAll(@NotNull final Tile goal, final World w) {
        calcH(goal);
        calcG(w);
    }

    private void calcH(@NotNull final Tile goal) {
        h = distanceSquared(goal);
    }

    void calcG(final World w) {
        Tile currentPrev = prev, currentTile = this;
        int gCost = 0;
        // follow path back to start
        while (currentPrev != null) {

            gCost += currentTile.distanceSquared(currentPrev) * materialPenalty(w);

            // move backwards a tile
            currentTile = currentPrev;
            currentPrev = currentTile.prev;
        }
        g = gCost;
    }

    Location toLocation(final World w) {
        if (loc == null) {
            loc = new Location(w, x, y, z);
        }
        return loc;
    }

    @Override
    public int hashCode() {
        return x * 31 + y * 11 + z * 7;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tile)) {
            return false;
        }
        final Tile tile = (Tile) o;
        return x == tile.x && y == tile.y && z == tile.z;
    }

    @NotNull
    @Override
    public String toString() {
        return "Tile{" + "coord=" + x + "," + y + "," + z + ", h=" + h + ", g=" + g + ", weight=" + getWeight() + '}';
    }

    float getWeight() {
        return h + g;
    }

    @Override
    public int compareTo(@NotNull final Tile o) {
        return (int) Math.signum((h + g) - (o.h + o.g));
    }

    private float materialPenalty(@Nullable final World w) {
        if (multiplier != -1) {
            return multiplier;
        }
        else if (w == null) {
            multiplier = 1f;
            return multiplier;
        }

        final Block b = toLocation(w).getBlock();

        switch (b.getType()) {
            case WATER:
                return 25f;
            case STATIONARY_WATER:
                return 12f;
            case SOUL_SAND:
                return 8f;
            case ICE:
            case PACKED_ICE:
                return 1.5f;
            case LAVA:
            case STATIONARY_LAVA:
            case FENCE:
            case ACACIA_FENCE:
            case BIRCH_FENCE:
            case DARK_OAK_FENCE:
            case JUNGLE_FENCE:
            case NETHER_FENCE:
            case SPRUCE_FENCE:
            case FENCE_GATE:
            case ACACIA_FENCE_GATE:
            case SPRUCE_FENCE_GATE:
            case JUNGLE_FENCE_GATE:
            case DARK_OAK_FENCE_GATE:
            case BIRCH_FENCE_GATE:
                return NO_ACCESS;
            default:
                return AStar.canBeWalkedThrough(b.getType()) ? NO_ACCESS : abovePenalty(b);
        }
    }
}
