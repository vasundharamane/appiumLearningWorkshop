import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;
import java.util.Arrays;

import static java.time.Duration.ofMillis;

public class PointerInputEx {
    AndroidDriver driver;

    @BeforeClass
    public void launch_app()
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

    //Drag and Drop element using Pointer and Sequence
    @Test
    public void dragNDropEx()
    {
        driver.findElement((AppiumBy.accessibilityId("login"))).click();

        driver.findElement((AppiumBy.accessibilityId("Demo drag and drop"))).click();

        WebElement dragElem = driver.findElement(AppiumBy.accessibilityId(("dragMe"))) ;
        WebElement dropElem = driver.findElement(AppiumBy.accessibilityId("dropzone"));



        int leftX = dropElem.getLocation().getX();
        int rightX = leftX + dropElem.getSize().getWidth();
        int middleX = (rightX + leftX) / 2;

        int upperY = dropElem.getLocation().getY();
        int lowerY = upperY + dropElem.getSize().getHeight();
        int middleY = (upperY + lowerY) / 2;


        Point source = dragElem.getLocation();
        Point target = dropElem.getLocation();

        //here pointer input is finger
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        //create a sequence
        Sequence dragNDrop = new Sequence(finger, 1);

        //mentione from where to start
        dragNDrop.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), source.x, source.y));
        //press down
        dragNDrop.addAction(finger.createPointerDown(PointerInput.MouseButton.MIDDLE.asArg()));
        //wait for some sec
        dragNDrop.addAction(new Pause(finger, ofMillis(600)));
        //move mouse to other location
        dragNDrop.addAction(finger.createPointerMove(Duration.ofMillis(700),
                PointerInput.Origin.viewport(),middleX, middleY));
        //release mouse
        dragNDrop.addAction(finger.createPointerUp(PointerInput.MouseButton.MIDDLE.asArg()));

        //perform sequence
        driver.perform(Arrays.asList(dragNDrop));
    }
}
