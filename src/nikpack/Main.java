package nikpack;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        List<Thread> workers = new ArrayList<>();
        Report report = new Report();
        TextResource[] resources = {
                new TestTextResource("мама", "папа", "дядя"),
                new TestTextResource("папа", "сынок", "дочка"),
                new TestTextResource("папа", "мама", "теща"),
        };

        for(TextResource res: resources) {
            Thread worker = new WorkerThread(res, report);
            workers.add(worker);
            worker.start();
        }

        try {
            for(Thread worker: workers)
                worker.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

class Window extends JFrame {

    JButton btnClick;

    public Window(String title) throws HeadlessException {
        super(title);
        btnClick = new JButton("Нажми сюда");
        btnClick.setBounds(100, 100, 150, 100);
        add(btnClick);
        setSize(400, 300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
