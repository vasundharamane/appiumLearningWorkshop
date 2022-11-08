import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;

public class ParallelRun  {

    private final static String APPIUM_SERVER_URL = "http://127.0.0.1:4723";
    private WebDriver driver;
    String apkPath = getClass().getClassLoader().getResource("VodQA.apk").getPath(); //VodQA.apk


    @BeforeTest
    @Parameters({"udid", "systemPort", "device"})
    public void setupDriver(String udid, String systemPort, String device) throws IOException {
        URL url = null;
        try {
            System.out.println("Test starting");
            url = new URL(APPIUM_SERVER_URL);
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("appium:newCommandTimeout", 900000);
            capabilities.setCapability("appium:automationName", "UiAutomator2");
            capabilities.setCapability("appium:autoGrantPermissions", true);
//            capabilities.setCapability("appium:udid", udid);
            capabilities.setCapability("appium:systemPort", systemPort);
            capabilities.setCapability("appium:app", apkPath);
            capabilities.setCapability("appium:appWaitForLaunch", false);
            driver = new AndroidDriver(url, capabilities);
            Thread.sleep(100);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @AfterTest
    public void stopDriver(){
        driver.quit();
    }
    @Test
    public void testVodQASlider() {
        try {
            Thread.sleep(100);

            driver.findElement(new AppiumBy.ByAndroidUIAutomator("textStartsWith(\"LOG IN\")")).click();
            driver.findElement(new AppiumBy.ByAndroidUIAutomator("textStartsWith(\"Slider\")")).click();

            WebElement element = driver.findElement(new AppiumBy.ByAndroidUIAutomator("descriptionStartsWith(\"slider\")") {
            });
            //Get width of seekbar
            int end = element.getSize().getWidth();
            System.out.println("end " + element.getSize().getWidth());
            System.out.println("hight " + element.getSize().getHeight());

            System.out.println("end " + end);
//                //get location of seekbar vertically
            int y = element.getLocation().getY();
            System.out.println("y " + y);

            //Move it 40%
            int moveTo = (int) (end * 0.4);

            // Java
            ((JavascriptExecutor) driver).executeScript("mobile: dragGesture", ImmutableMap.of(
                    "elementId", ((RemoteWebElement) element).getId(),
                    "endX", end,
                    "endY", y
            ));

            //after send keys trial from Appium inspector it seems that send keys 130 goes to the end
            element.sendKeys("100");
//            Thread.sleep(200);
            Thread.sleep(100);
            driver.quit();

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }


}
