package ca.ulaval.glo4003.ws.integration;

import ca.ulaval.glo4003.AirChitectureMain;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractIntegrationTest {

    @BeforeClass
    public static void startWebServer() {
        Thread t = new Thread() {
            public void run() {
                try {
                    AirChitectureMain.main(new String[]{"8080"});
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };
        t.setDaemon(true);
        t.start();
    }
}
