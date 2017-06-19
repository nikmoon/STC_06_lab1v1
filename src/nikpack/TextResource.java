package nikpack;

import java.io.*;
import java.util.Scanner;

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



/**
 * Тестовый ресурс, получающий данный из обычной строки
 */
class TestTextResource implements TextResource {

    private Scanner scanner;

    public TestTextResource(String text) {
        this.scanner = new Scanner(text);

    }

    @Override
    public String readLine() throws EndOfResourceException {
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        else {
            throw new EndOfResourceException(this);
        }
    }

    @Override
    public void close() throws IOException {
        scanner.close();
    }

    @Override
    public String getName() {
        return "TestTextResource";
    }
}

