import service.FileService;
import service.PhoneNumberService;

public class Main {

    // This could be a directory or a file.
    //private static final String HARDCODED_PATH_TO_SCAN = "src/main/resources/prueba.txt";
    private static final String HARDCODED_PATH_TO_SCAN = "src/main/resources/directory";


    public static void main(String[] args) throws Exception {

        PhoneNumberService phoneNumberService = new PhoneNumberService();
        FileService fileService = new FileService();

        fileService.processFile(fileService.readFile(HARDCODED_PATH_TO_SCAN), phoneNumberService);

        phoneNumberService.processResponse();
    }
}
