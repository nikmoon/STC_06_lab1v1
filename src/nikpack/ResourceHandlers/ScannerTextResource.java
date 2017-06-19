package nikpack.ResourceHandlers;

import nikpack.ResourceHandlers.TextResource;

import java.io.*;
import java.util.Scanner;


/**
 * Тестовый ресурс, получающий данный из обычной строки
 */
class ScannerTextResource implements TextResource {

    private Scanner scanner;

    public ScannerTextResource(String text) {
        this.scanner = new Scanner(text);

    }

    @Override
    public String readLine() throws EndOfResourceException {
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        else {
            throw new EndOfResourceException(this);
        }
    }

    @Override
    public void close() throws IOException {
        scanner.close();
    }

    @Override
    public String getName() {
        return "ScannerTextResource";
    }
}

