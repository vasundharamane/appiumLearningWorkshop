import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URL;
import java.time.Duration;

public class FirstAppiumTest_ApiDemosDebug {

    //Execute Mobile Command (Executes a native mobile command) -  Used ApiDemosDebug app
    @Test
    public void scrollDown(){
        try {
            String apkPath = getClass().getClassLoader().getResource("ApiDemos-debug.apk").getPath(); //ApiDemos-debug.apk

            //here used desired capabilities
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("appium:automationName", "UiAutomator2");
            capabilities.setCapability("appium:autoGrantPermissions", true);
            capabilities.setCapability("appium:app", apkPath);


            capabilities.setCapability("appium:noReset", false);
            capabilities.setCapability("appium:fullReset", true);
            capabilities.setCapability("appium:appWaitForLaunch", false);
            AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), capabilities);

//            Set the amount of time the driver should wait when searching for elements
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

//            Configure the amount of time that a particular type of operation can execute for before they are aborted
//            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(2));

//            driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(2));

//            Set the amount of time, in milliseconds, that asynchronous scripts executed by execute async are permitted to run before they are aborted
            driver.findElement(AppiumBy.accessibilityId("Graphics")).click();

            //take screenshot
            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            File DestFile=new File("target/test.png");
            FileUtils.copyFile(scrFile, DestFile);

            //get page source
            String pageSource =  driver.getPageSource();
            System.out.println("Page Source " +  pageSource);

            //get screen orientation
            ScreenOrientation orientation = driver.getOrientation();
            System.out.println("orientation " + orientation);

            //Get context
            String context = driver.getContext();
            System.out.println("context " + context);

            Thread.sleep(50000);
            driver.quit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Assert.fail();
        }


    }
}
