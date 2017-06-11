package nikpack;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by nikbird on 11.06.2017.
 */
public interface Parser extends Iterable<String>, Iterator<String> {
    Iterable<String> init(String line);
}


class ParserException extends RuntimeException {

}


class TestParser implements Parser {

    private String[] words;
    private int index;

    @Override
    public Iterable<String> init(String line) {
        index = 0;
        words = line.trim().split(" ");
        return this;
    }

    @Override
    public boolean hasNext() {
        if (index < words.length) {
            return true;
        }
        return false;
    }

    @Override
    public String next() {
        return words[index++];
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<String> iterator() {
        return this;
    }
}
