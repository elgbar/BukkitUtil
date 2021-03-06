package no.kh498.util.itemMenus.api.constants;

import org.jetbrains.annotations.NotNull;

/**
 * @author Elg
 * @since 0.1.0
 */
public enum CommonPos {
    /**
     * Leftmost slot
     */
    LEFT(0),
    /**
     * Slot in the middle between LEFT and MIDDLE
     */
    MIDDLE_LEFT(2),
    /**
     * Middle slot
     */
    MIDDLE(4),
    /**
     * Slot in the middle between MIDDLE and RIGHT
     */
    MIDDLE_RIGHT(6),
    /**
     * Rightmost slot
     */
    RIGHT(8);

    private final int position;

    CommonPos(final int position) {
        this.position = position;
    }

    public int getPos() {
        return position;
    }

    /**
     * @param row
     *     What row the position to be on
     *
     * @return A common position in a certain row
     */
    public int getPos(@NotNull final Size row) {
        if (row == Size.ONE) { return position; }
        else { return position + Size.values()[row.ordinal() - 1].getSize(); }
    }}
