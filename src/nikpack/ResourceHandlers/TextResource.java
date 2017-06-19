package nikpack.ResourceHandlers;

import java.io.IOException;

/**
 * Created by nikbird on 09.06.2017.
 *
 * Интерфейс, инкапсулирующий работу с текстовым ресурсом
 *
 */
public interface TextResource {

    /**
     *  Данное исключение "бросается" экземпляром текстового ресурса, когда он
     *  опустошается
     *
     *  Не требуется закрывать ресурс при "отлавливании" данного исключения,
     *  т.к. он закрывается в конструкторе исключения
     */
    class EndOfResourceException extends Exception {
        public EndOfResourceException(TextResource resource) {
            try {
                resource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    String readLine() throws EndOfResourceException;
    void close() throws IOException;
    String getName();
}
