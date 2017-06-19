package nikpack;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * Created by nikbird on 11.06.2017.
 */
public class Reporter extends StoppableThread {

    /**
     *  Список всех запущенных потоков для вывода отчета
     */
    private static List<Reporter> reporters = new CopyOnWriteArrayList<>();

    private String name;
    private BlockingQueue<String> queue;
    private Map<String, Integer> words = new HashMap<>();

    public Reporter(String name) {
        this.name = name;
        queue = new LinkedBlockingQueue<>();
        start();
        Reporter.reporters.add(this);
    }

    /**
     *
     */
    @Override
    public void run() {
        System.out.println("Reporter thread [" + name + "] started");
        try {
            while (!stopWork) {
                processWord(queue.take());
                update();
            }
        } catch (InterruptedException e) {
            if (!stopWork) {     // неожиданное поведение
                System.out.println("Неожиданное прерывание потока \"Отчет\"");
            }
        }
        System.out.println("Reporter thread [" + name + "] ended");
    }

    /**
     * Обработка очередного слова из очереди
     *
     * @param word
     *
     */
    private void processWord(String word) throws InterruptedException {
        if (word.equals("")) {
            stopWork = true;
            throw new InterruptedException();
        }
        Integer count = words.get(word);
        count = count == null ? 1 : (count + 1);
        words.put(word, count);
    }

    /**
     * Вывод обновленного отчета
     *
     */
    private void update() {
        StringBuilder reportString = new StringBuilder(name + ": ");
        for(Map.Entry<String, Integer> entry: words.entrySet())
            reportString.append(entry.getKey() + ": " + entry.getValue() + "; ");
        System.out.println(reportString);
    }

    /**
     *  Передать очередной валидный токен каждому потоку-отчету
     * @param token
     */
    public static void sendToAllReporters(String token) {
        for(Reporter reporter: Reporter.reporters) {
            reporter.queue.offer(token);
        }
    }

    /**
     *  Массовый join для всех потоков Reporter
     */
    public static void joinAll(long millis) {
        for(Reporter reporter: Reporter.reporters) {
            try {
                reporter.join(millis);
            } catch (InterruptedException e) {
                System.out.println("Error!!! Thread [" + reporter.name + "] hang");
            }
        }
    }
}
