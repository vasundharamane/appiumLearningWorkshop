import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;

public class iOSBasicTest {

    AppiumDriver driver ;

    @BeforeTest
    public void launch_app()
    {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("platformName", "iOS");
            capabilities.setCapability("platformVersion", "15.5");
            capabilities.setCapability("automationName","xcuitest");
            capabilities.setCapability("deviceName","iPhone 13 Pro Max");
            String apkPath = getClass().getClassLoader().getResource("Sample.app.2.7.1.app").getPath(); //VodQA.apk

            capabilities.setCapability("app", apkPath);
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), capabilities);

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));


        }
        catch(Exception e)
        {
            e.printStackTrace();
            Assert.fail();
        }

    }

    @Test
    public void ex1_login()
    {
        driver.findElement(AppiumBy.accessibilityId("test-Username")).sendKeys("standard_user");
        driver.findElement(AppiumBy.accessibilityId("test-Password")).sendKeys("secret_sauce");
        driver.findElement(AppiumBy.accessibilityId("test-LOGIN")).click();
    }

}
