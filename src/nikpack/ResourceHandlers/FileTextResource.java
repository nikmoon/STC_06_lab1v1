package nikpack.ResourceHandlers;

import nikpack.ResourceHandlers.TextResource;

import java.io.*;

/**
 * Текстовый ресурс, считывающий данные из текстового файла
 */
public class FileTextResource implements TextResource {

    private String fileName;
    private BufferedReader reader;

    public FileTextResource(String fileName, String charset) throws FileNotFoundException, UnsupportedEncodingException {
        this.fileName = fileName;

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
            System.out.println("Серьезная ошибка чтения из файла \"" + fileName + "\"");
        }

        // данная строка выполнится после вывода сообщения о серьезной ошибке ввода-вывода
        throw new EndOfResourceException(this);
    }

    @Override
    public String getName() {
        return fileName;
    }
}
