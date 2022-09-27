import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;

public class firstIOSTest {

    AppiumDriver driver;

@Test
public void launch_app()
    {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("platformName", "iOS");
            capabilities.setCapability("platformVersion", "15.5");
            capabilities.setCapability("automationName","xcuitest");
            capabilities.setCapability("deviceName","iPhone 13 Pro Max");
            capabilities.setCapability("app", "/Users/vasundhara.mane/Desktop/Sample.app.2.7.1.app");
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), capabilities);

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        }
        catch(Exception e)
        {
            e.printStackTrace();
            Assert.fail();
        }

    }
}
