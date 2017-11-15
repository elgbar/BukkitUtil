package no.kh498.util.countdown.api.timeFormat;

/**
 * {@inheritDoc}
 *
 * @author karl henrik
 */
public class CountdownSeconds implements TimeFormat {

    @Override
    public String formatTime(final long timeLeft) {
        return timeLeft / 1000L + " seconds";
    }
}
