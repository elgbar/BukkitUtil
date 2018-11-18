package no.kh498.util;

/**
 * @author Elg
 * @since 0.1.0
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public final class MCConstants {

    /**
     * The factor to multiply with to convert ticks into milliseconds.
     * <p>
     * For example if you want to have one second from tick into milliseconds you can do:
     * <p>
     * {@code 20 (nr of ticks in a second) * 50 = 1000 (nr of millis in a second)}
     */
    public final static int TICK_TO_MILLIS_FACTOR = 50;

    public final static int ONE_MINECRAFT_DAY_IN_TICKS = 24000;
    public final static int ONE_MINECRAFT_DAY_IN_MILLIS = ONE_MINECRAFT_DAY_IN_TICKS * TICK_TO_MILLIS_FACTOR;


    public final static int ONE_SECOND_IN_TICKS = 20;
    public final static int ONE_MINUTE_IN_TICKS = ONE_SECOND_IN_TICKS * 60;
    public final static int ONE_HOUR_IN_TICKS = ONE_MINUTE_IN_TICKS * 60;

    public static final int ONE_SECOND_IN_MILLIS = 1000;
    public static final int ONE_MINUTE_IN_MILLIS = ONE_SECOND_IN_MILLIS * 60;
    public static final int ONE_HOUR_IN_MILLIS = ONE_MINUTE_IN_MILLIS * 60;


    /**
     * Convert ticks to milliseconds
     *
     * @param ticks
     *     the tick to convert
     *
     * @return the input converted to milliseconds
     */
    public static int ticksToMS(final int ticks) {
        return ticks * TICK_TO_MILLIS_FACTOR;
    }

    /**
     * Convert milliseconds to ticks
     *
     * @param ms
     *     the milliseconds to convert
     *
     * @return the input converted to ticks
     */
    public static long msToTicks(final int ms) {
        return ms / TICK_TO_MILLIS_FACTOR;
    }
}
