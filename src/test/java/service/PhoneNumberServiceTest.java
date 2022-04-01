package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.TreeSet;

import static org.junit.Assert.assertTrue;
import static org.mockito.internal.util.reflection.Whitebox.getInternalState;

@RunWith(MockitoJUnitRunner.class)
public class PhoneNumberServiceTest {

    @InjectMocks
    private PhoneNumberService phoneNumberService;

    /**
     * Test process line "+7 812 1231233" should run ok
     */
    @Test
    public void testProcessLineShouldRunOk() {
        TreeSet<String> mappedValues = (TreeSet<String>) getInternalState(phoneNumberService, "mappedValues");

        phoneNumberService.processLine("+7 812 1231233");

        assertTrue(mappedValues.contains(Long.valueOf("78121231233")));
    }

    /**
     * Test that default country and city code are added.
     */
    @Test
    public void testProcessLineShouldRunOkWith7NumbersAndDefaultValuesAreAdded() {
        TreeSet<String> mappedValues = (TreeSet<String>) getInternalState(phoneNumberService, "mappedValues");

        phoneNumberService.processLine("1231233");

        assertTrue(mappedValues.contains(Long.valueOf("78121231233")));
    }

    /**
     * Test number without country code should run ok and country code is added.
     */
    @Test
    public void testProcessLineShouldRunOkWith10Numbers() {
        TreeSet<String> mappedValues = (TreeSet<String>) getInternalState(phoneNumberService, "mappedValues");

        phoneNumberService.processLine("8121231233");

        assertTrue(mappedValues.contains(Long.valueOf("78121231233")));
    }

    /**
     * Test invalid line (Missing numbers) should not add the line
     */
    @Test
    public void testProcessLineShouldNotAddInvalidLine() {
        TreeSet<String> mappedValues = (TreeSet<String>) getInternalState(phoneNumberService, "mappedValues");

        phoneNumberService.processLine("+1");

        assertTrue(mappedValues.isEmpty());
    }

    /**
     * Test invalid line (number too long) should not add the line
     */
    @Test
    public void testProcessLineShouldNotProcessLineDueTooLong() {
        TreeSet<String> mappedValues = (TreeSet<String>) getInternalState(phoneNumberService, "mappedValues");

        phoneNumberService.processLine("+7 812 12312330000000");

        assertTrue(mappedValues.isEmpty());
    }

    /**
     * Process response should format lines.
     */
    @Test
    public void testProcessResponseShouldRunOk() {
        TreeSet<Long> mappedValues = new TreeSet<>();
        mappedValues.add(Long.valueOf("78121231233"));
        Whitebox.setInternalState(phoneNumberService, "mappedValues", mappedValues);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        phoneNumberService.processResponse();

        assertTrue(outContent.toString().contains("+7 (812) 123-1233"));
    }

    /**
     * Test ascending order of the response.
     */
    @Test
    public void testProcessResponseWithMultipleValuesShouldBeOrdered() {
        TreeSet<Long> mappedValues = new TreeSet<>();
        mappedValues.add(Long.valueOf("78121231233"));
        mappedValues.add(Long.valueOf("78121231231"));
        mappedValues.add(Long.valueOf("78121231235"));
        Whitebox.setInternalState(phoneNumberService, "mappedValues", mappedValues);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        phoneNumberService.processResponse();
        String[] output = outContent.toString()
                .replace("Printing result set", "")
                .replace("End of result set", "")
                .replaceFirst("\r\n", "")
                .split("\r\n");

        assertTrue(output[0].equals("+7 (812) 123-1235"));
        assertTrue(output[1].equals("+7 (812) 123-1233"));
        assertTrue(output[2].equals("+7 (812) 123-1231"));
    }

}
