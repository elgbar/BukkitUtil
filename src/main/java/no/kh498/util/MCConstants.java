package no.kh498.util;

/**
 * @author karl henrik
 * @since 0.1.0
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public final class MCConstants {

    /**
     * The factor to multiply with to convert ticks into milliseconds.
     * <p>
     * For example if you want to have one second from tick into millis you can do:
     * <p>
     * {@code 20 (nr of ticks in a second) * 50 = 1000 (nr of millis in a second)}
     */
    public final static long TICK_TO_MILLIS_FACTOR = 50L;
    public final static long ONE_SECOND_IN_TICKS = 20L;

    public final static long ONE_MINECRAFT_DAY_IN_TICKS = 24000L;
    public final static long ONE_MINECRAFT_DAY_IN_MILLIS = ONE_MINECRAFT_DAY_IN_TICKS * TICK_TO_MILLIS_FACTOR;

    public static final long ONE_MINUTE_IN_MILLIS = ONE_SECOND_IN_TICKS * 60L * TICK_TO_MILLIS_FACTOR;
    public static final long TEN_MINUTES_IN_MILLIS = ONE_MINUTE_IN_MILLIS * 10L;
    public final static long TEN_SECONDS_IN_TICKS = ONE_SECOND_IN_TICKS * 10L;

    /**
     * Convert ticks to milliseconds
     */
    public static long ticksToMS(final long ticks) {
        return ticks * TICK_TO_MILLIS_FACTOR;
    }

    /**
     * Convert milliseconds to ticks
     */
    public static long msToTicks(final long ms) {
        return ms / TICK_TO_MILLIS_FACTOR;
    }
}
