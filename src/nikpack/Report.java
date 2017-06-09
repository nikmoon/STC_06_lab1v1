package nikpack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nikbird on 09.06.2017.
 *
 * Отчет, формирующийся в режиме реального времени
 *
 */
public class Report {

    private Map<String, Integer> words;

    public Report() {
        this.words = new HashMap<>();
    }

    // обновление отчета
    synchronized public boolean update(String word) {
        if (word == null)
            return false;

        Integer count = words.get(word);
        if (count == null)
            count = 1;
        else
            count += 1;
        words.put(word, count);
        this.show();
        return true;
    }

    private void show() {

    }
}
