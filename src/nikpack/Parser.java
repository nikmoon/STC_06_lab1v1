package nikpack;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by nikbird on 11.06.2017.
 *
 * Интерфейс, описывающий некий парсер текстовой строки
 *
 */
public interface Parser extends Iterable<String>, Iterator<String> {
    Iterable<String> init(String line);
}

/**
 * Данное исключение предназначено для сообщения коду, использующему парсер, о
 * возникновении ошибочной сутуации - при обнаружении хотя бы одного символа
 * в очередном слове анализируемой строки
 */
class ParserException extends RuntimeException {

}


class TestParser implements Parser {

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
                throw new ParserException();
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
