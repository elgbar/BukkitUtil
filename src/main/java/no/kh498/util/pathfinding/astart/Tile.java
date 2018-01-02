package no.kh498.util.pathfinding.astart;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;

import static no.kh498.util.pathfinding.astart.AStar.canBeWalkedThrough;

/**
 * @author karl henrik
 */
public class Tile implements Comparable<Tile> {

    int x;
    int y;
    int z;

    float h = -1;
    float g = -1;

    private float multiplier = -1;
    private Location loc;

    Tile prev;

    static int NO_ACCESS = 100000;

    public Tile(final int x, final int y, final int z, final Tile prev) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.prev = prev;
    }

    Tile(final Location location) {
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
    }

    /**
     * @param t
     *     Other tile to find the distance to
     *
     * @return The squared distance between {@code this} tile and {@code t} tile
     */
    int distanceSquared(final Tile t) {
        return square(this.x - t.x) + square(this.y - t.y) + square(this.z - t.z);
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

    void calcAll(final Tile goal, final World w) {
        calcH(goal);
        calcG(w);
    }

    private void calcH(final Tile goal) {
        this.h = distanceSquared(goal);
    }

    void calcG(final World w) {
        Tile currentPrev = this.prev, currentTile = this;
        int gCost = 0;
        // follow path back to start
        while (currentPrev != null) {

            gCost += currentTile.distanceSquared(currentPrev) * materialPenalty(w);

            // move backwards a tile
            currentTile = currentPrev;
            currentPrev = currentTile.prev;
        }
        this.g = gCost;
    }

    Location toLocation(final World w) {
        if (this.loc == null) {
            this.loc = new Location(w, this.x, this.y, this.z);
        }
        return this.loc;
    }

    float getWeight() {
        return this.h + this.g;
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
        return this.x == tile.x && this.y == tile.y && this.z == tile.z;
    }

    @Override
    public int hashCode() {
        return this.x * 31 + this.y * 11 + this.z * 7;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public int compareTo(final Tile o) {
        return (int) Math.signum((this.h + this.g) - (o.h + o.g));
    }

    @Override
    public String toString() {
        return "Tile{" + "coord=" + this.x + "," + this.y + "," + this.z + ", h=" + this.h + ", g=" + this.g +
               ", weight=" + getWeight() + '}';
    }

    private float materialPenalty(final World w) {
        if (this.multiplier != -1) {
            return this.multiplier;
        }
        else if (w == null) {
            this.multiplier = 1f;
            return this.multiplier;
        }

        final Block b = toLocation(w).getBlock();

        switch (b.getType()) {
            case SOUL_SAND:
            case WATER:
                return 20f;
            case STATIONARY_WATER:
                return 10f;
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
                return canBeWalkedThrough(b.getType()) ? NO_ACCESS : abovePenalty(b);
        }
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
                System.out.println("Looked at openable of lower, was NOT open");
                return NO_ACCESS;
            }
            else {
                System.out.println("Looked at openable of lower, is OPEN");
                loOpen = true;
            }
        }


        if (hiDat instanceof Openable) {
            if (!((Openable) hiDat).isOpen()) {
                System.out.println("Looked at openable of upper, was NOT open");
                return NO_ACCESS;
            }
            else {
                System.out.println("Looked at openable of upper, is OPEN");
                hiOpen = true;
            }
        }


        if ((!loOpen && !canBeWalkedThrough(loMat)) || (!hiOpen && !canBeWalkedThrough(hiMat))) {
            return NO_ACCESS;
        }
        if (loMat == Material.WEB || hiMat == Material.WEB) {
            return 20f;
        }
        return 1f;
    }
}
