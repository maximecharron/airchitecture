package ca.ulaval.glo4003.air.infrastructure;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ResourcesEmailTransactionNotifierConfiguration implements EmailTransactionNotifierConfiguration {

    private static final String EMAIL_NOTIFIER_CONFIGURATIONS_PROPERTIES_FILE = "emailTransactionNotifierConfigurations.properties";
    private static final String FROM_ADDRESS_PROPERTY = "airchitecture.EmailTransactionNotifier.FromAddress";

    private Properties properties;

    public ResourcesEmailTransactionNotifierConfiguration() throws IOException {
        this.properties = new Properties();
        this.loadProperties();
    }

    private void loadProperties() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(EMAIL_NOTIFIER_CONFIGURATIONS_PROPERTIES_FILE);
        properties.load(inputStream);
    }

    public String getFromAddress() {
        return properties.getProperty(FROM_ADDRESS_PROPERTY);
    }
}
