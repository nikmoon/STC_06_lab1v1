package nikpack;


import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;


public class Main {

    public static void main(String[] args) {
        test(Arrays.asList("test/file1.txt", "test/file2.txt", "test/file3.txt"), "cp1251");
    }

    public static void test(List<String> fileNames, String charset) {


        ExecutorService threadPool = Executors.newFixedThreadPool(3);


        new Reporter("report 1");
        new Reporter("report 2");
        new Reporter("report 3");

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
                threadPool.execute(new Worker(resource));
            }

            // дожидаемся завершения всех рабочих потоков
            threadPool.shutdown();
            threadPool.awaitTermination(60, TimeUnit.SECONDS);
            if (!threadPool.isTerminated()) {
                System.out.println("Серьезная ошибка!!! Не все рабочие потоки завершились корректно");
                System.exit(-1);
            }

            //  отправляем всем потокам-отчетам флаг завершения
            Reporter.sendToAllReporters("");

            // дожидаемся завершения всех отчетных потоков
            Reporter.joinAll(5000);

        } catch (InterruptedException e) {
            System.out.println("Главный поток неожиданно прерван");
        }
    }
}
