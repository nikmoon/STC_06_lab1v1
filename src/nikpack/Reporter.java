package nikpack;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * Created by nikbird on 11.06.2017.
 */
public class Reporter extends StoppableThread {

    private BlockingQueue<String> queue;
    private Map<String, Integer> words = new HashMap<>();

    public Reporter() {
        this.queue = new LinkedBlockingQueue<>();
    }

    /**
     *
     */
    @Override
    public void run() {
        System.out.println("Поток \"Отчет\" запущен");
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
        System.out.println("Поток \"Отчет\" завершен");
    }

    /**
     * Обработка очередного слова из очереди
     *
     * @param word
     *
     */
    private void processWord(String word) throws InterruptedException {
        System.out.println("Считано слово " + word);
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
        StringBuilder reportString = new StringBuilder();
        for(Map.Entry<String, Integer> entry: words.entrySet())
            reportString.append(entry.getKey() + ": " + entry.getValue() + "; ");
        System.out.println(reportString);
    }

    public BlockingQueue<String> getQueue() {
        return queue;
    }
}
