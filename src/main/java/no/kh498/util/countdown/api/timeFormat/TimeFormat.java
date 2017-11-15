package no.kh498.util.countdown.api.timeFormat;

/**
 * @author karl henrik
 */
public interface TimeFormat {

    /**
     * @param timeLeft
     *     The time left in milliseconds
     *
     * @return A way of displaying the time
     */
    String formatTime(long timeLeft);

    /**
     * @return How long to wait between each time run is called (in ms)
     */
    long delay();
}
