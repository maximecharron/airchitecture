package ca.ulaval.glo4003.ws.integration;

import ca.ulaval.glo4003.AirChitectureMain;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.mockito.runners.MockitoJUnitRunner;

import static java.lang.Thread.sleep;

@RunWith(Suite.class)
@Suite.SuiteClasses({ FlightResourceIT.class, UserResourceIT.class })
public class BaseIntegrationTest {
    private static boolean isStarted = false;

    @BeforeClass
    public static void startWebServer() throws InterruptedException{
        if (!isStarted) {
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
            sleep(500);
        }
    }
}
