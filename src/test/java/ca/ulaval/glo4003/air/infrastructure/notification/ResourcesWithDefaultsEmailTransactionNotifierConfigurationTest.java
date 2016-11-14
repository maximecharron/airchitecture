package ca.ulaval.glo4003.air.infrastructure.notification;

import ca.ulaval.glo4003.air.domain.notification.EmailTransactionNotifierConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class ResourcesWithDefaultsEmailTransactionNotifierConfigurationTest {

    private EmailTransactionNotifierConfiguration emailConfiguration;

    @Before
    public void setUp() throws IOException {
        this.emailConfiguration = new ResourcesWithDefaultsEmailTransactionNotifierConfiguration();
    }

    @Test
    public void givenAConfigurationPropertyRequested_whenReadingFromResourcesFile_thenValueIsReadCorrectly() throws Exception {
        String expectedValue = "airchitecture1@gmail.com";

        String actualValue = this.emailConfiguration.getFromAddress();

        Assert.assertEquals(expectedValue, actualValue);
    }
}
