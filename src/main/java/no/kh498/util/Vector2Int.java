package no.kh498.util;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Immutable two dimensional vector
 *
 * @author Elg
 */
public class Vector2Int {

    public final int x;
    public final int y;

    public Vector2Int(int x, int y) {
        this.x = x;
        this.y = y;
    }


    /**
     * @param other
     *     The vector to add to this vector
     *
     * @return This vector added with the given vector
     */
    public Vector2Int add(@NotNull Vector2Int other) {
        return add(other.x, other.y);
    }

    /**
     * @param dx
     *     The number to add to this vector in the {@code x} space
     * @param dy
     *     The number to add to this vector in the {@code y} space
     *
     * @return This vector multiplied by the given x and y
     */
    public Vector2Int add(int dx, int dy) {
        return new Vector2Int(x + dx, y + dy);
    }


    /**
     * @param other
     *     The vector to multiplied by
     *
     * @return This vector multiplied by another vector
     */
    public Vector2Int mult(@NotNull Vector2Int other) {
        return mult(other.x, other.y);
    }

    /**
     * @param dx
     *     The number to multiplied by in the {@code x} space
     * @param dy
     *     The number to multiplied by in the {@code y} space
     *
     * @return This vector multiplied by the given x and y
     */
    public Vector2Int mult(int dx, int dy) {
        return new Vector2Int(x * dx, y * dy);
    }

    /**
     * @param other
     *     The vector to divide by
     *
     * @return This vector divided the given vector
     */
    public Vector2Int div(@NotNull Vector2Int other) {
        return div(other.x, other.y);
    }

    /**
     * @param dx
     *     The number to divide by in the {@code x} space
     * @param dy
     *     The number to divide by in the {@code y} space
     *
     * @return This vector divided by the given x and y
     */
    public Vector2Int div(int dx, int dy) {
        return new Vector2Int(x / dx, y / dy);
    }

    /**
     * @param other
     *     The vector to find the distance to
     *
     * @return The distance between this and another vector
     */
    public double dist(@NotNull Vector2Int other) {
        return Math.sqrt(distSqrt(other));
    }

    /**
     * @param other
     *     The vector to find the distance to
     *
     * @return The distance between this and another vector squared
     */
    public double distSqrt(@NotNull Vector2Int other) {
        return ((x - other.x) * (x - other.x)) + ((y - other.y) * (y - other.y));
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        Vector2Int anInt = (Vector2Int) o;
        return x == anInt.x && y == anInt.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
