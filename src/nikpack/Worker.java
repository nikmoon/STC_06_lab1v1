package nikpack;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 * Created by nikbird on 09.06.2017.
 *
 * Рабочий поток, обрабатывающий ресурс
 *
 */
public class Worker extends StoppableThread {

    // данный флаг полезен, если необходимо "мягко" остановить
    // все выполняющиеся потоки класса Worker
    public static volatile boolean stopAll = false;

    private TextResource resource;
    private Reporter reporter;
    private Parser parser;


    public Worker(Reporter reporter, TextResource resource, Parser parser) {
        this.reporter = reporter;
        this.resource = resource;
        this.parser = parser;
    }

    @Override
    public void run() {
        System.out.println("Поток \"Работник\" запущен");
        try {
            String line;
            break_thread:
            do {
                line = resource.readLine();
                System.out.println(line + line);
                for (String word : parser.init(line)) {
                    if (stopWork || Worker.stopAll)
                        break break_thread;
                    reporter.getQueue().put(word);
                    Thread.sleep(200);
                }
            } while (true);
        } catch (EndOfResourceException e) {
            // ресурс пуст, просто завершаем рабочий поток
            resource = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            if (!stopWork) {
                System.out.println("Неожиданное завершение потока \"Работник\"");
            }
        } catch (ParserException e) {
            e.printStackTrace();
            // парсер обнаружил латинский символ - останавливаем все потоки Worker
            Worker.stopAll = true;
        } finally {
            if (resource != null)
                try {
                    resource.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        System.out.println("Поток \"Работник\" завершен");
    }

}
