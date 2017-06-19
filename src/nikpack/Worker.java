package nikpack;

import java.io.IOException;
import java.util.List;

/**
 * Created by nikbird on 09.06.2017.
 *
 * Рабочий поток, обрабатывающий ресурс
 *
 */
public class Worker extends StoppableThread {

    /**
     *  Данный флаг предназначен для "мягкой" остановки
     *  всех выполняющихся потоков класса Worker
     */
    public static volatile boolean stopAll = false;

    /**
     *  Текстовый ресурс, из которого Worker получает "сырые" данные
     */
    private TextResource resource;

    /**
     *  Парсер для "сырых данных"
     */
    private Parser newParser;


    public Worker(TextResource resource) {
        this.resource = resource;
        newParser = new LineParser();
    }

    @Override
    public void run() {
        System.out.println("Worker for [" + resource.getName() + "] started.");
        try {
            String line;
            break_thread:
            do {
                line = resource.readLine();
                for (String word : newParser.init(line)) {
                    if (stopWork || Worker.stopAll)
                        break break_thread;

                    // отправляем токен всем потокам-потребителям
                    Reporter.sendToAllReporters(word);
                    Thread.sleep(100);
                }
            } while (true);
        } catch (TextResource.EndOfResourceException e) {
            // ресурс пуст, просто завершаем рабочий поток
//            resource = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            if (!stopWork) {
                System.out.println("Неожиданное завершение потока Worker[" + resource.getName() + "].");
            }
        } catch (ParserException e) {
            // парсер обнаружил латинский символ - останавливаем все потоки Worker
            System.out.println("Invalid token: [" + e.getToken() + "]. Stop parsing.");
            Worker.stopAll = true;
        }
        try {
            resource.close();
        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println("Resource [" + resource.getName() + "] exception on close");
        }
//        finally {
//            if (resource != null)
//                try {
//                    resource.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//        }
        System.out.println("Worker for [" + resource.getName() + "] ended");
    }

}
