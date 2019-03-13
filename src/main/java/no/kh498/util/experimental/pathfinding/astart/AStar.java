package no.kh498.util.experimental.pathfinding.astart;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author Elg
 */
public class AStar {

    private final PriorityQueue<Tile> open = new PriorityQueue<>();
    private final HashSet<Tile> openContains = new HashSet<>();
    private final HashSet<Tile> closed = new HashSet<>();
    private final Tile start;
    private final Tile goal;
    private Logger logger = LoggerFactory.getLogger(AStar.class);
    private World w;
    //how many blocks should be checked before no path found is declared?
    private int timeoutSize = 5000;

    public AStar(@NotNull final Location start, @NotNull final Location goal) {
        this(new Tile(start), new Tile(goal));
        if (!start.getWorld().equals(goal.getWorld())) {
            throw new IllegalArgumentException("The start and the goal is not in the same world.");
        }

        w = start.getWorld();
    }

    public AStar(final Tile start, final Tile goal) {
        this.start = start;
        this.goal = goal;

        addOpen(this.start);

        this.start.calcAll(this.goal, w);
        logger.debug("start: " + start);
    }

    private void addOpen(@NotNull final Tile tile) {
        open.offer(tile);
        openContains.add(tile);
    }

    @Nullable
    public List<Location> pathfind() {
        if (w == null) {
            logger.error("Cannot create a location path when the world has not been specified");
            return null;
        }
        final List<Tile> tilePath = pathfindTile();
        if (tilePath == null) {
            return null;
        }
        final ArrayList<Location> locPath = new ArrayList<>(tilePath.size());

        for (final Tile t : tilePath) {
            locPath.add(t.toLocation(w));
        }
        return locPath;
    }

    @Nullable
    public List<Tile> pathfindTile() {
        while (!open.isEmpty()) {
            final Tile curr = pollOpen();
            logger.trace("curr " + curr);
            if (curr.equals(goal)) {
                return buildPath(curr);
            }
            else if (closed.size() >= timeoutSize || curr.getWeight() >= Tile.NO_ACCESS) { //no path
                return null;
            }

            closed.add(curr);

            for (final Tile adj : getAdjacent(curr)) {
                if (closed.contains(adj)) {
                    continue;
                }

                adj.calcAll(goal, w);

                if (!containsOpen(adj)) {
                    addOpen(adj);
                }
                final float maybeG = curr.g + adj.distanceSquared(curr);
                if (maybeG < adj.g) {
                    adj.prev = curr;
                    adj.calcG(w);
                }
            }
        }
        return null;
    }

    private Tile pollOpen() {
        openContains.remove(open.peek());
        return open.poll();
    }

    @NotNull
    private List<Tile> buildPath(final Tile goal) {
        final Stack<Tile> path = new Stack<>();
        Tile curr = goal;
        while (curr.prev != null) {
            path.push(curr);
            curr = curr.prev;
        }
        path.push(start);
        return path;
    }

    /**
     * @return All locations around the location
     */
    @NotNull
    private static Tile[] getAdjacent(@NotNull final Tile tile) {
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

    private boolean containsOpen(final Tile t) {
        return openContains.contains(t);
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
    private boolean isTileWalkable(@NotNull final Tile t) {
        if (w == null) {
            return true;
        }
        final Block b = t.toLocation(w).getBlock();
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

    public int getTimeoutSize() {
        return timeoutSize;
    }

    public void setTimeoutSize(int timeoutSize) {
        this.timeoutSize = timeoutSize;
    }
}
