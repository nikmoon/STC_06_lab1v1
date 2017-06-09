package nikpack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nikbird on 09.06.2017.
 *
 * Отчет, формирующийся в режиме реального времени
 *
 */
public class Report {

    private Map<String, Integer> words;
    private List<String> keys;

    public Report() {
        this.words = new HashMap<>();
        this.keys = new ArrayList<>();
    }

    // обновление отчета
    synchronized public boolean update(String word) {
        if (word == null)
            return false;
        Integer count = words.get(word);
        if (count == null) {
            count = 1;
            keys.add(word);
        }
        else {
            count += 1;
        }
        words.put(word, count);
        show(word, count);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    // вывод отчета
    private void show(String key, Integer newValue) {
        StringBuilder reportString = new StringBuilder();
        for(String word: keys) {
            reportString.append(word + ": " + words.get(word) + ";");
        }
        System.out.println(reportString);
    }
}
