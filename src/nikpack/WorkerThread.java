package nikpack;

/**
 * Created by nikbird on 09.06.2017.
 *
 * Рабочий поток, обрабатывающий ресурс
 *
 */
public class WorkerThread extends Thread {

    private TextResource resource;
    private Report report;
    public volatile boolean stopWork;


    public WorkerThread(TextResource resource, Report report) {
        this.resource = resource;
        this.report = report;
        this.stopWork = false;
    }

    @Override
    public void run() {
        String currentWord;

        try {
            while (!stopWork) {
                currentWord = resource.readWord();
                this.report.update(currentWord);    // возвращаемый результат нас не интересует
            }
        }
        catch (FoundLatinException ex) {

        }
    }
}
