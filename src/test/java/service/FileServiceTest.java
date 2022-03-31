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

    private static final String VALID_PATH = "src/test/java/resources/test.txt";

    @Test
    public void testReadFileShouldRunOk(){
        File file = fileService.readFile(VALID_PATH);

        assertNotNull(file);
        assertTrue(file.exists());
    }

    @Test
    public void testReadFileFailDueNullPointerException(){
        File file = fileService.readFile(null);

        assertNull(file);
    }
}
