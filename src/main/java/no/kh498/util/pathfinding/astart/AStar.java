package no.kh498.util.pathfinding.astart;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;

import java.util.*;

/**
 * @author karl henrik
 */
public class AStar {

    private final PriorityQueue<Tile> open = new PriorityQueue<>();
    private final HashSet<Tile> openContains = new HashSet<>();

    private final HashSet<Tile> closed = new HashSet<>();

    private World w;

    private final Tile start;
    private final Tile goal;

    public AStar(final Location start, final Location goal) {
        this(new Tile(start), new Tile(goal));
        if (!start.getWorld().equals(goal.getWorld())) {
            throw new IllegalArgumentException("The start and the goal is not in the same world.");
        }
        this.w = start.getWorld();
    }

    public AStar(final Tile start, final Tile goal) {
        this.start = start;
        this.goal = goal;

        addOpen(this.start);

        this.start.calcAll(this.goal, this.w);
        System.out.print("start: " + start);
    }

    public List<Location> pathfind() {
        if (this.w == null) {
            System.err.println("Cannot create a location path when the world has not been specified");
            return null;
        }
        final List<Tile> tilePath = pathfindTile();
        final ArrayList<Location> locPath = new ArrayList<>(tilePath.size());

        for (final Tile t : tilePath) {
            locPath.add(t.toLocation(this.w));
        }
        return locPath;
    }


    public List<Tile> pathfindTile() {
        while (!this.open.isEmpty()) {
            final Tile curr = pollOpen();
//            System.out.println("curr = " + curr);
            if (curr.equals(this.goal)) {
                return buildPath(curr);
            }

            this.closed.add(curr);

            for (final Tile adj : getAdjacent(curr)) {
                if (this.closed.contains(adj)) { //|| !isTileWalkable(adj)
                    continue;
                }

                adj.calcAll(this.goal, this.w);

                if (!containsOpen(adj)) {
                    addOpen(adj);
                }
                final float maybeG = curr.g + adj.distanceSquared(curr);
                if (maybeG < adj.g) {
                    adj.prev = curr;
                    adj.calcG(this.w);
                }
            }
        }
        return null;
    }

    private List<Tile> buildPath(final Tile goal) {
        final Stack<Tile> path = new Stack<>();
        Tile curr = goal;
        while (curr.prev != null) {
            path.push(curr);
            curr = curr.prev;
        }
        path.push(this.start);
        return path;
    }

    /**
     * If the world is not specified every tile is walkable (to enable debugging without bukkit)
     * <p>
     * Three conditions must be met:
     * <p>
     * 1) The tile {@code t} must be solid
     * <p>
     * 2) The two blocks above the tile must NOT be solid
     * <p>
     * 3) if any of the tiles above can be opened, only opened ones are passable
     *
     * @return If a humanoid can walk on this tile.
     */
    private boolean isTileWalkable(final Tile t) {
        if (this.w == null) {
            return true;
        }
        final Block b = t.toLocation(this.w).getBlock();
        final Material mat = b.getType();

        //all the material that we CANT walk on
        switch (mat) {
            case LAVA:
            case WATER:
            case STATIONARY_WATER:
            case STATIONARY_LAVA:
            case FIRE:
            case LADDER:
            case VINE:
            case WHEAT:
            case FENCE:
            case NETHER_FENCE:
            case FENCE_GATE:
                return false;
        }


        final Block lower = b.getRelative(BlockFace.UP, 1);
        final Block higher = b.getRelative(BlockFace.UP, 2);

        final MaterialData loDat = lower.getState().getData();
        final MaterialData hiDat = higher.getState().getData();

        //if either block can be opened check if one of them is closed, if so return false
        if (loDat instanceof Openable || hiDat instanceof Openable) {
            if (loDat instanceof Openable && !((Openable) loDat).isOpen()) {
                return false;
            }
            else if (hiDat instanceof Openable && !((Openable) hiDat).isOpen()) {
                return false;
            }
            else {
                return true;
            }
        }
        //if neither is openable, check if they can be walked through
        return canBeWalkedThrough(lower.getType()) && canBeWalkedThrough(higher.getType());
    }

    static boolean canBeWalkedThrough(final Material mat) {
        //sign?
        //more redstone stuff? & rails
        //penalty for web & soulsand?
        return mat == Material.AIR || mat == Material.SAPLING || mat == Material.TORCH || mat == Material.SIGN_POST ||
               mat == Material.WEB || mat == Material.LONG_GRASS || mat == Material.DEAD_BUSH ||
               mat == Material.YELLOW_FLOWER || mat == Material.RED_ROSE || mat == Material.BROWN_MUSHROOM ||
               mat == Material.RED_MUSHROOM || mat == Material.REDSTONE_WIRE || mat == Material.RAILS ||
               mat == Material.REDSTONE_TORCH_OFF || mat == Material.REDSTONE_TORCH_ON || mat == Material.SNOW ||
               mat == Material.ACTIVATOR_RAIL || mat == Material.POWERED_RAIL || mat == Material.DETECTOR_RAIL ||
               mat == Material.SIGN || mat == Material.SUGAR_CANE_BLOCK || mat == Material.DOUBLE_PLANT;
    }

    private void addOpen(final Tile tile) {
        this.open.offer(tile);
        this.openContains.add(tile);
    }

    private Tile pollOpen() {
        this.openContains.remove(this.open.peek());
        return this.open.poll();
    }

    private boolean containsOpen(final Tile t) {
        return this.openContains.contains(t);
    }

    /**
     * @return All locations around the location
     */
    private static Tile[] getAdjacent(final Tile tile) {
        final Tile[] neighbor = new Tile[24];
        int counter = 0;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {

                    //do not include directly above/below and at self
                    if (dx == 0 && dz == 0) {
                        continue;
                    }
                    neighbor[counter++] = new Tile(tile.x + dx, tile.y + dy, tile.z + dz, tile);
                }
            }
        }
        return neighbor;
    }

}
