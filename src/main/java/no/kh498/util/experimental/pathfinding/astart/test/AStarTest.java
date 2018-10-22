package no.kh498.util.experimental.pathfinding.astart.test;

import no.kh498.util.experimental.pathfinding.astart.AStar;
import no.kh498.util.experimental.pathfinding.astart.Tile;

import java.util.List;

/**
 * @author Elg
 */
public class AStarTest {

    public static void main(final String[] args) {
        final Tile start = new Tile(0, 0, 0, null);
        final Tile goal = new Tile(1, 20, 1, null);
        final AStar aStar = new AStar(start, goal);
        final List<Tile> loc = aStar.pathfindTile();
        System.out.println(loc);
    }
}
