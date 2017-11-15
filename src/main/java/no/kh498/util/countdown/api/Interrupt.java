package no.kh498.util.countdown.api;

/**
 * @author karl henrik
 */
class Interrupt {

    private final String message;
    private final long startTime;
    private final long duration;

    /**
     * @param message
     *     The interrupt message to display
     * @param duration
     *     The duration of the interrupt in milliseconds
     */
    Interrupt(final String message, final long duration) {

        this.message = message;
        this.duration = duration;
        this.startTime = System.currentTimeMillis();
    }

    /**
     * @return if the interrupted message should be shown
     */
    boolean shouldInterrupt() {
        return this.startTime + this.duration > System.currentTimeMillis();
    }

    String getMessage() {
        return this.message;
    }
}
