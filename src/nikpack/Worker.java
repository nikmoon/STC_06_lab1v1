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
            do {
                line = resource.readLine();
                for (String word : parser.init(line)) {
                    if (stopWork || Worker.stopAll)
                        break;
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
            // останавливаем все потоки
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
