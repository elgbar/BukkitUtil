package no.kh498.util.pathfinding.astart;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

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
        if (!AStar.canBeWalkedThrough(b.getRelative(BlockFace.UP).getType()) ||
            !AStar.canBeWalkedThrough(b.getRelative(BlockFace.UP, 2).getType())) {
            return NO_ACCESS;
        }

        switch (b.getType()) {
            case SOUL_SAND:
            case WEB:
                return 20f;
            case WATER:
                return 20f;
            case STATIONARY_WATER:
                return 10f;
            case ICE:
            case PACKED_ICE:
                return 1.25f;
            case LAVA:
            case STATIONARY_LAVA:
            case AIR:
            case FENCE:
            case SNOW:
                return NO_ACCESS;
            default:
                return 1f;
        }
    }
}
