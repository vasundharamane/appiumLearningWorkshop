import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;

public class GestureEx {

    AndroidDriver driver;

    @BeforeTest
    public void setup()
    {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("appium:automationName", "UiAutomator2");
            capabilities.setCapability("appium:autoGrantPermissions", true);

            String apkPath = getClass().getClassLoader().getResource("VodQA.apk").getPath(); //VodQA.apk


            capabilities.setCapability("appium:app", apkPath);
            capabilities.setCapability("appium:noReset", false);
            capabilities.setCapability("appium:fullReset", true);
            capabilities.setCapability("appium:appWaitForLaunch", false);
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
    public void doubleClick()
    {
        driver.findElement((AppiumBy.accessibilityId("login"))).click();
        driver.findElement(By.xpath("//android.widget.TextView[@text='Double Tap']")).click();
        WebElement btn_doubleTap = driver.findElement(By.xpath("//android.widget.TextView[@text='Double Tap Me']"));
        ((JavascriptExecutor) driver).executeScript("mobile: doubleClickGesture", ImmutableMap.of(
                "elementId",((RemoteWebElement) btn_doubleTap).getId()
        ));
    }

}
