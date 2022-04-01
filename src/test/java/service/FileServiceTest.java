package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class FileServiceTest {

    @InjectMocks
    private FileService fileService;

    private static final String VALID_FILE_PATH = "src/test/java/resources/test.txt";
    private static final String VALID_DIRECTORY = "src/test/java/resources/directory";
    private static final String INVALID_FILE_PATH = "src/test/java/resources/test.csv";


    /**
     * Test read file should retrieve existing file.
     */
    @Test
    public void testReadFileShouldRunOk() {
        File file = fileService.readFile(VALID_FILE_PATH);

        assertNotNull(file);
        assertTrue(file.exists());
    }


    /**
     * Test read null path should retrieve null file.
     */
    @Test
    public void testReadFileFailDueNullPointerException() {
        File file = fileService.readFile(null);

        assertNull(file);
    }

    /**
     * Test process file with valid path should run ok.
     */
    @Test
    public void testProcessFileShouldRunOk() {
        File file = fileService.readFile(VALID_FILE_PATH);
        LineProcessor lineProcessor = new PhoneNumberService();

        fileService.processFile(file, lineProcessor);
    }

    /**
     * Test process valid directory should run ok.
     */
    @Test
    public void testProcessFileShouldRunOkWithValidDirectoryPath() {
        File file = fileService.readFile(VALID_DIRECTORY);
        LineProcessor lineProcessor = new PhoneNumberService();

        fileService.processFile(file, lineProcessor);
    }

    /**
     * Test process file with null file should not fail.
     */
    @Test
    public void testProcessFileShouldRunOkWithNullFile() {
        LineProcessor lineProcessor = new PhoneNumberService();

        fileService.processFile(null, lineProcessor);
    }

    /**
     * Test process file with empty file should not fail.
     */
    @Test
    public void testProcessFileShouldRunOkWithNullFilePath() {
        File file = fileService.readFile(null);
        LineProcessor lineProcessor = new PhoneNumberService();

        fileService.processFile(file, lineProcessor);
    }

    /**
     * Test process invalid path should not fail.
     */
    @Test
    public void testProcessFileShouldRunOkWithInvalidFile() {
        File file = fileService.readFile(INVALID_FILE_PATH);
        LineProcessor lineProcessor = new PhoneNumberService();

        fileService.processFile(file, lineProcessor);
    }

}
