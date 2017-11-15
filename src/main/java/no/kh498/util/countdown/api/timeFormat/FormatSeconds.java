package no.kh498.util.countdown.api.timeFormat;

/**
 * {@inheritDoc}
 *
 * @author karl henrik
 */
public class FormatSeconds implements TimeFormat {

    @Override
    public String formatTime(final long timeLeft) {
        return timeLeft / 1000L + " seconds";
    }

    @Override
    public long delay() {
        return 10L;
    }
}
