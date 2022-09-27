import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;

public class firstAppiumTest_VodQA {

    //    Login Demo -
//    Provide random creds and login and assert on alert message
    @Test
    public void testVodQALoginWithInvalidCredential() {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("appium:automationName", "UiAutomator2");
            capabilities.setCapability("appium:autoGrantPermissions", true);

            String apkPath = getClass().getClassLoader().getResource("VodQA.apk").getPath(); //VodQA.apk

            capabilities.setCapability("appium:app", apkPath);
            capabilities.setCapability("appium:noReset", false);
            capabilities.setCapability("appium:fullReset", true);
            capabilities.setCapability("appium:appWaitForLaunch", false);
            AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), capabilities);

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

            driver.findElement(AppiumBy.accessibilityId("username")).sendKeys("WrongUserName");
//            driver.findElement(AppiumBy.accessibilityId("password")).sendKeys("");
            driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"login\"]/android.widget.Button/android.widget.TextView")).click();

            //validate if Invalid Credential popup Appeared
//            if (driver.findElement(By.id("android:id/title_template")).isDisplayed())
//                System.out.println("popup appeared");
//            else System.out.println("popup did not appeared");

            Assert.assertEquals(driver.findElement(By.id("android:id/title_template")).isDisplayed(), true, "Check if invalid Credential popup appeared");
            driver.quit();

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    //use of DragGesture -  Slider movement
    @Test
    public void testVodQASlider() {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("appium:automationName", "UiAutomator2");
            capabilities.setCapability("appium:autoGrantPermissions", true);
            capabilities.setCapability("appium:app", "/Users/vasundhara.mane/Desktop/VodQA.apk");
            capabilities.setCapability("appium:noReset", false);
            capabilities.setCapability("appium:fullReset", true);
            capabilities.setCapability("appium:appWaitForLaunch", false);
            AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), capabilities);

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

//            driver.findElement(AppiumBy.accessibilityId("username")).sendKeys("WrongUserName");
//            driver.findElement(AppiumBy.accessibilityId("password")).sendKeys("");
            //click on login
            driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"login\"]/android.widget.Button/android.widget.TextView")).click();

            driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"slider1\"]/android.view.ViewGroup")).click();
            //validate if Invalid Credential popup Appeared
//                if(driver.findElement(By.id("android:id/title_template")).isDisplayed())
//                {System.out.println("popup appeared");}
//                else
//                {System.out.println("popup did not appeared");}

            WebElement element = driver.findElement(By.xpath("//android.widget.SeekBar[@content-desc='slider']"));

//                int start=seek_bar.getLocation().getX();
//                //Get width of seekbar
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

            //after send keys trial from Appium server it seems that send keys 130 goes to the end
            element.sendKeys("130");
//            Thread.sleep(200);

            driver.quit();

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    //Example Appium Service start examle
    @Test
    public void appServiceStartEx() {
        try {
//set capability
//    DesiredCapabilities cap = new DesiredCapabilities();
// noReset - Clears the App Data ,  fullReset -  uninstalls the App - https://appium.io/docs/en/writing-running-appium/other/reset-strategies/index.html
//            [This is not compulsory , but we can add it]
//    cap.setCapability("noReset", "false");

            //build the appium server
            AppiumServiceBuilder builder = new AppiumServiceBuilder();
            builder.withIPAddress("127.0.0.1");
            builder.usingPort(4723);
//    builder.withCapabilities(cap);
//            [This is not compulsory, but we can add it]
//    SESSION_OVERRIDE:enables session overrride , LOG_LEVEL :set the server log level for console      https://appium.io/docs/en/writing-running-appium/server-args/
//    builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE,"false");
//    builder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");

            //start the Server with the builder
            AppiumDriverLocalService service = AppiumDriverLocalService.buildService(builder);
            service.start();


            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("appium:automationName", "UiAutomator2");
            capabilities.setCapability("appium:autoGrantPermissions", true);
            capabilities.setCapability("appium:app", "/Users/vasundhara.mane/Desktop/VodQA.apk");
            capabilities.setCapability("appium:noReset", false);
            capabilities.setCapability("appium:fullReset", true);
            capabilities.setCapability("appium:appWaitForLaunch", false);
            AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), capabilities);

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

            //click on login
            driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"login\"]/android.widget.Button/android.widget.TextView")).click();

            service.stop();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void dragAndDropEx()
    {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("appium:automationName", "UiAutomator2");
            capabilities.setCapability("appium:autoGrantPermissions", true);
            capabilities.setCapability("appium:app", "/Users/vasundhara.mane/Desktop/VodQA.apk");
            capabilities.setCapability("appium:noReset", false);
            capabilities.setCapability("appium:fullReset", true);
            capabilities.setCapability("appium:appWaitForLaunch", false);
            AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), capabilities);

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

            driver.findElement((AppiumBy.accessibilityId("login"))).click();

            driver.findElement((AppiumBy.accessibilityId("Demo drag and drop"))).click();

            WebElement dragElem = driver.findElement(AppiumBy.accessibilityId(("dragMe"))) ;
            WebElement dropElem = driver.findElement(AppiumBy.accessibilityId("dropzone"));

//            ((JavascriptExecutor) driver).executeScript("mobile: dragGesture", ImmutableMap.of(
//                    "elementId", ((RemoteWebElement) dragElem).getId(),
//                    "endX", dropElem.getLocation().x+dragElem.getSize().width/2,
//                    "endY", dropElem.getLocation().y+dragElem.getSize().height/2
//            ));

            int leftX = dropElem.getLocation().getX();
            int rightX = leftX + dropElem.getSize().getWidth();
            int middleX = (rightX + leftX) / 2;

            int upperY = dropElem.getLocation().getY();
            int lowerY = upperY + dropElem.getSize().getHeight();
            int middleY = (upperY + lowerY) / 2;

            ((JavascriptExecutor) driver).executeScript("mobile: dragGesture", ImmutableMap.of(
                    "elementId", ((RemoteWebElement) dragElem).getId(),
                    "endX", middleX,
                    "endY", middleY,
                    "speed",700

            ));

//            System.out.println("X ");
            Thread.sleep(500);

//            Assert the test of success
            driver.quit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Assert.fail();
        }

    }
}
