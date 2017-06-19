package Tests;

import nikpack.ResourceHandlers.TextResource.EndOfResourceException;
import nikpack.ResourceHandlers.FileTextResource;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by sa on 17.06.17.
 */
class FileTextResourceTest {

    static String[] fileNames = new String[] {
            "test/test1.txt",
            "test/test2.txt",
            "test/test3.txt"};

    static List<String[]> fileContent = Arrays.asList(
            new String[] {
                    "Когда солнце взойдет?",
                    "В первом файле будет 2 строчки"
            },
            new String[] {
                    "Сегодня солнце взойдет около 6 часов",
                    "Во втором файле будет 3 строчки",
                    "Эй ямщик, не гони лошадей!"
            },
            new String[] {
                    "В третьем файле тоже будет 2 строчки",
                    "Просто 3 слова."
            }
    );

    static FileTextResource[] resources = new FileTextResource[3];

    @BeforeAll
    static void setUpAll() {

        // создадим 3 файла
        for(int i = 0; i < 3; i++) {
            String fileName = fileNames[i];
            String[] content = fileContent.get(i);
            try {
                Path path = Paths.get(fileName);
                if (Files.exists(path))
                    Files.delete(path);
                Files.createFile(Paths.get(fileName));

                // записываем тестовые значения в файл
                Files.write(path, Arrays.asList(content));

            } catch (IOException e) {
                e.printStackTrace();
                fail(e);
            }
        }

        // создаем 3 ресурса
        for(int i = 0; i < 3; i++) {
            try {
                resources[i] = new FileTextResource(fileNames[i], "utf-8");
            } catch (FileNotFoundException e) {

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                fail(e);
            }
        }

    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void close() {
    }

    @Test
    void readLine() {
        int exceptionsCount = 0;
        for(int i = 0; i < 3; i++) {
            FileTextResource resource = resources[i];
            String[] lines = fileContent.get(i);

            for(int j = 0; true; j++) {
                try {
                    String line = resource.readLine();
                    assertEquals(lines[j], line);
                } catch (EndOfResourceException e) {
                    exceptionsCount++;
                    break;
                }
            }
        }
        assertEquals(3, exceptionsCount);
    }

    @Test
    void getName() {

        // проверяем результат возврата метода getName
        for(int i = 0; i < 3; i++) {
            assertEquals(fileNames[i], resources[i].getName());
        }
    }
}