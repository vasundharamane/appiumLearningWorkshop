import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Bytes;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Base64;

public class FirstAppiumTest_VodQA {
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
            driver.findElement(By.xpath("//android.widget.TextView[@text='LOG IN']")).click();

            //validate if Invalid Credential popup Appeared
//            if (driver.findElement(By.id("android:id/title_template")).isDisplayed())
//                System.out.println("popup appeared");
//            else System.out.println("popup did not appeared");
            WebElement errorPopup = driver.findElement(By.id("android:id/parentPanel"));

            Assert.assertEquals(errorPopup.isDisplayed(), true, "Check if invalid Credential popup appeared");

//            take screenshot method-1
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileHandler.copy(source, new File("target/invalidCredentialPopup1.png"));

            //take Screenshot method-2
            String base64code = driver.getScreenshotAs(OutputType.BASE64);
            String replaceBase64 = base64code.replaceAll("\n","");
            byte[] byteArr = Base64.getDecoder().decode(replaceBase64.getBytes(StandardCharsets.UTF_8));
            File destFile = new File("target/invalidCredentialPopup2.png");
		    FileOutputStream fos = new FileOutputStream(destFile);
		    fos.write(byteArr);
		    fos.close();

            //take Screenshot method-3
            File source1= driver.getScreenshotAs(OutputType.FILE);
            FileHandler.copy(source1, new File("target/invalidCredentialPopup3.png"));

            //Selenium 4 feature to take screenshot of particular element
            File source2= errorPopup.getScreenshotAs(OutputType.FILE);
            FileHandler.copy(source2, new File("target/errorPopup.png"));

            driver.quit();

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
//    Login Demo -
//    Provide random creds and login and assert on alert message -  here we have used ByAndroidUIAutomator
    @Test
    public void testVodQALoginWithInvalidCredential2() {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("appium:automationName", "UiAutomator2");
            capabilities.setCapability("appium:autoGrantPermissions", true);

            String apkPath = getClass().getClassLoader().getResource("VodQA.apk").getPath(); //VodQA.apk

            capabilities.setCapability("appium:app", apkPath);
            capabilities.setCapability("appium:noReset", true);
            capabilities.setCapability("appium:fullReset", false);
            capabilities.setCapability("appium:appWaitForLaunch", false);
            AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), capabilities);

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

            driver.findElement(AppiumBy.accessibilityId("username")).sendKeys("WrongUserName");
            //ByAndroidUIAutomator - As the name suggest , this locator is android specific
            driver.findElement(new AppiumBy.ByAndroidUIAutomator("textStartsWith(\"LOG IN\")")).click();

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
            capabilities.setCapability("appium:app", "/Users/vasundhara.mane/Documents/git/AppiumLearningWorkshop/src/test/resources/VodQA.apk");
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

    //Example Appium Service start example
    @Test
    public void appServiceStartEx() {
        //build the appium server
        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder.withIPAddress("127.0.0.1");
        builder.usingPort(4723);
//        builder.usingAnyFreePort()
//        builder.withArgument(GeneralServerFlag.LOG_LEVEL ,"warn");
        AppiumDriverLocalService service = AppiumDriverLocalService.buildService(builder);

        //start the Server with the builder
        service.start();
        try {
            String apkPath = getClass().getClassLoader().getResource("VodQA.apk").getPath(); //VodQA.apk

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("appium:automationName", "UiAutomator2");
            capabilities.setCapability("appium:autoGrantPermissions", true);
            capabilities.setCapability("appium:app", apkPath);
            capabilities.setCapability("appium:noReset", false);
            capabilities.setCapability("appium:fullReset", true);
            capabilities.setCapability("appium:appWaitForLaunch", false);
            AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), capabilities);

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

            //click on login
            driver.findElement(By.xpath("//android.widget.TextView[@text='LOG IN']")).click();

            service.stop();

        } catch (Exception e) {
            e.printStackTrace();
            service.stop();
            Assert.fail();

        }
    }

    @Test
    public void dragAndDropEx()
    {
        try {
            String apkPath = getClass().getClassLoader().getResource("VodQA.apk").getPath(); //VodQA.apk
            UiAutomator2Options options =  new UiAutomator2Options();
            options.setDeviceName("APPIUM_EMULATOR");
            options.setApp(apkPath);

            AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

            driver.findElement(AppiumBy.accessibilityId("login")).click();
            driver.findElement(AppiumBy.accessibilityId("Demo drag and drop")).click();

            WebElement dragElem = driver.findElement(AppiumBy.accessibilityId(("dragMe"))) ;
            WebElement dropElem = driver.findElement(AppiumBy.accessibilityId("dropzone"));

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

            Thread.sleep(500);

            driver.quit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Assert.fail();
        }

    }
}
