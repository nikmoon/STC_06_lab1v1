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


/**
 *
 */
class FoundLatinException extends Exception {

}
