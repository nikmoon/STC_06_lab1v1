package nikpack.Parsers;

import java.util.Iterator;

/**
 * Created by sa on 20.06.17.
 */
public class LineParser implements Parser {

    private String[] words;
    private int index;

    /**
     * Подготовка итератора
     *
     * @param line
     * @return
     */
    @Override
    public Iterable<String> init(String line) {
        index = 0;

        // заменяем все знаки пунктуации на пробелы
        line = line.trim().replaceAll("\\p{Punct}", " ");

        // теперь заменяем все управляющие символы на пробелы
        line = line.replaceAll("\\p{Cntrl}", " ");

        // теперь заменяем все числа на пробелы
        line = line.replaceAll(" \\p{Digit}+", " ");

        // теперь разбиваем на слова
        words = line.split("\\s+");
        return this;
    }

    @Override
    public boolean hasNext() {
        if (index < words.length) {

            // если есть хоть один
            if (words[index].matches(".*\\p{Alpha}+.*"))
                throw new ParserException(words[index]);
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
