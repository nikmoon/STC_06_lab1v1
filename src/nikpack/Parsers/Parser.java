package nikpack.Parsers;

import java.util.Iterator;

/**
 * Created by nikbird on 11.06.2017.
 *
 * Интерфейс, описывающий некий парсер текстовой строки
 *
 */
public interface Parser extends Iterable<String>, Iterator<String> {

    /**
     * Данное исключение предназначено для сообщения коду, использующему парсер, о
     * возникновении ошибочной сутуации - при обнаружении хотя бы одного символа
     * в очередном слове анализируемой строки
     */
    class ParserException extends RuntimeException {
        private String token;

        public ParserException(String token) {
            super();
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }

    Iterable<String> init(String line);

}


