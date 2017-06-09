package nikpack;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        try {
            throw new NullPointerException();
        }
        catch (NullPointerException ex) {
            System.out.println("was exception");
        }
    }
}
