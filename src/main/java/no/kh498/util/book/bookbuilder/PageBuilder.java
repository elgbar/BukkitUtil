package no.kh498.util.book.bookbuilder;

import no.kh498.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A builder for each page in a book. To add text {@link TextBuilder} are used within the extra array of the page
 *
 * @author Elg
 */
public class PageBuilder {

    private List<TextBuilder> texts = new ArrayList<>();

    /**
     * @return A new text builder with this as its parent
     */
    @NotNull
    public TextBuilder add() {
        TextBuilder tb = new TextBuilder();
        texts.add(tb);
        return tb;
    }

    /**
     * @return A text builder with the given text
     */
    @NotNull
    public TextBuilder add(@NotNull String text) {
        TextBuilder tb = new TextBuilder();
        tb.setText(text);
        texts.add(tb);
        return tb;
    }

    @NotNull
    public String build() {
        StringBuilder page = new StringBuilder("{text:\"\", extra:[");
        for (TextBuilder text : texts) {
            page.append(text.build()).append(", ");
        }
        if (!texts.isEmpty()) {
            StringUtil.removeTail(page, 2);
        }
        return page.append("]}").toString();
    }

    @NotNull
    public List<TextBuilder> getTexts() {
        return texts;
    }
}
