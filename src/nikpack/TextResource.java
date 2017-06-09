package nikpack;

/**
 * Created by nikbird on 09.06.2017.
 *
 * Интерфейс, инкапсулирующий работу с текстовым ресурсом
 *
 */
interface TextResource {
    String readWord() throws FoundLatinException;
}


/**
 *
 */
class FileTextResource implements TextResource {

    private String fileName;

    public FileTextResource(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String readWord() throws FoundLatinException {
        throw new UnsupportedOperationException();
    }
}


class TestTextResource implements TextResource {

    private String[] strings;
    private int index = 0;

    public TestTextResource(String ... strings) {
        this.strings = strings;
    }

    @Override
    public String readWord() throws FoundLatinException {
        if (index >= strings.length)
            return null;
        return strings[index++];
    }
}


/**
 *
 */
class FoundLatinException extends Exception {

}
