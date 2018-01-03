package no.kh498.util.itemMenus.api.constants;

/**
 * Possible sizes of an ItemMenu.
 */
public enum Size {
    /**
     * One row; 9 slots
     */
    ONE(9),
    /**
     * Two rows; 18 slots
     */
    TWO(18),
    /**
     * Three rows; 27 slots
     */
    THREE(27),
    /**
     * Four rows; 36 slots
     */
    FOUR(36),
    /**
     * Five rows; 45 slots
     */
    FIVE(45),
    /**
     * Six rows; 54 slots
     */
    SIX(54);

    private final int size;

    private static final double COL = 9d;
    private static final int MAX_ROWS = Size.values().length - 1;

    Size(final int size) {
        this.size = size;
    }

    /**
     * Gets the required size for an amount of slots.
     *
     * @param slots
     *     The amount of slots.
     *
     * @return The required size.
     */
    public static Size fit(final int slots) {
        final int sanSlots = Math.max(1, slots) - 1;

        final int rows = (int) Math.floor(sanSlots / COL);

        final int sanRows = Math.min(rows, MAX_ROWS);

        return Size.values()[sanRows];
    }

    /**
     * Gets the Size's amount of slots.
     *
     * @return The amount of slots.
     */
    public int getSize() {
        return this.size;
    }
}
