package nikpack;

/**
 * Created by nikbird on 11.06.2017.
 */
public class StoppableThread extends Thread {

    protected volatile boolean stopWork = false;

    /**
     * Мягкое завершение потока
     */
    public void softStop() {
        stopWork = true;
    }


    /**
     * "Мягкое" прерывание работы потока
     */
    public void softInterrupt() {
        stopWork = true;
        super.interrupt();
    }
}
