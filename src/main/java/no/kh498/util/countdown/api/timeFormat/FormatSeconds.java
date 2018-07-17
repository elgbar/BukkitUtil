package no.kh498.util.countdown.api.timeFormat;

/**
 * {@inheritDoc}
 *
 * @author karl henrik
 */
public class FormatSeconds implements TimeFormat {

    private final String seconds;

    public FormatSeconds(final String seconds) {
        this.seconds = seconds;
    }

    public FormatSeconds() {
        this("seconds");
    }


    @Override
    public String formatTime(final long timeLeft) {
        return timeLeft / 1000L + " " + this.seconds;
    }

    @Override
    public long delay() {
        return 10L;
    }
}
