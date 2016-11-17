package ca.ulaval.glo4003.air.infrastructure.notification;

import ca.ulaval.glo4003.air.domain.notification.EmailTransactionNotifierConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ResourcesWithDefaultsEmailTransactionNotifierConfiguration implements EmailTransactionNotifierConfiguration {

    private static final String EMAIL_NOTIFIER_CONFIGURATIONS_PROPERTIES_FILE = "emailTransactionNotifierConfigurations.properties";
    private static final String FROM_ADDRESS_PROPERTY = "airchitecture.EmailTransactionNotifier.FromAddress";
    private static final String DEFAULT_FROM_ADDRESS = "<UNSET>";

    private Properties properties;

    /**
     * airchitecture.EmailTransactionNotifier.FromAddress=airchitecture1@gmail.com
     */
    public ResourcesWithDefaultsEmailTransactionNotifierConfiguration() {
        this.properties = new Properties();
        this.loadProperties();
    }

    private void loadProperties() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(EMAIL_NOTIFIER_CONFIGURATIONS_PROPERTIES_FILE);
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            properties.setProperty(FROM_ADDRESS_PROPERTY, DEFAULT_FROM_ADDRESS);
        }
    }

    public String getFromAddress() {
        return properties.getProperty(FROM_ADDRESS_PROPERTY);
    }
}
