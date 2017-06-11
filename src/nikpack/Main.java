package nikpack;


import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Main {

    public static void main(String[] args) {
        test(Arrays.asList("test/file1.txt", "test/file2.txt", "test/file3.txt"), "cp1251");
    }

    public static void test(List<String> fileNames, String charset) {

        Reporter reporter = new Reporter();
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        reporter.start();

        try {
            for (String fileName : fileNames) {
                TextResource resource;
                try {
                    resource = new FileTextResource(fileName, charset);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Worker.stopAll = true;
                    break;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Worker.stopAll = true;
                    break;
                }
                // если какой-либо рабочий поток попросил остановить все
                if (Worker.stopAll) {
                    break;
                }

                // пытаемся запустить очередной рабочий поток
                threadPool.execute(new Worker(reporter, resource, new TestParser()));
            }

            // дожидаемся завершения всех рабочих потоков
            threadPool.shutdown();
            threadPool.awaitTermination(60, TimeUnit.SECONDS);
            if (!threadPool.isTerminated()) {
                System.out.println("Серьезная ошибка!!! Не все рабочие потоки завершились корректно");
                System.exit(-1);
            }

            reporter.getQueue().put("");    // завершаем поток "Отчет"
            try {
                reporter.join(10000);
            } catch (InterruptedException e) {
                System.out.println("Серьезная ошибка!!! Поток \"Отчет\" не завершается корректно");
            }

        } catch (InterruptedException e) {
            System.out.println("Главный поток неожиданно прерван");
        }
    }
}
