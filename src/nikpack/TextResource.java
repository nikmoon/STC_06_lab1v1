package nikpack;

import java.io.*;
import java.util.Scanner;

/**
 * Created by nikbird on 09.06.2017.
 *
 * Интерфейс, инкапсулирующий работу с текстовым ресурсом
 *
 */
interface TextResource {
    String readLine() throws EndOfResourceException;
    void close() throws IOException;
}

/**
 * Данное исключение "бросается" экземпляром текстового ресурса, когда он
 * опустошается
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


/**
 * Текстовый ресурс, считывающий данные из текстового файла
 */
class FileTextResource implements TextResource {

    private String name;
    private BufferedReader reader;

    public FileTextResource(String fileName, String charset) throws FileNotFoundException, UnsupportedEncodingException {
        this.name = fileName;

        // такая сложная конструкция необходима для того, чтобы читать текстовые файлы
        // в разных кодировках
        reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName),charset)
        );
    }

    /**
     * Закрываем ресурс
     *
     * @throws IOException
     */
    public void close() throws IOException {
        reader.close();
    }

    /**
     * Чтение строки из ресурса
     *
     * Пустые строки пропускаются
     * Если строк больше нет, кидается EndOfResourceException
     *
     * @return
     * @throws EndOfResourceException
     */
    @Override
    public String readLine() throws EndOfResourceException {
        try {
            String line;
            do {
                line = reader.readLine();
                if (line == null)
                    throw new EndOfResourceException(this);
            } while(line.equals(""));
            return line;
        } catch (IOException e) {
            System.out.println("Серьезная ошибка чтения из файла \"" + name + "\"");
        }

        // данная строка выполнится после вывода сообщения о серьезной ошибке ввода-вывода
        throw new EndOfResourceException(this);
    }
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
}

