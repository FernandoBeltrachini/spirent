package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PhoneNumberServiceTest {

    @InjectMocks
    private PhoneNumberService phoneNumberService;

    @Test
    public void testProcessLineShouldRunOk(){
        phoneNumberService.processLine("+7 812 1231233");
    }

}
