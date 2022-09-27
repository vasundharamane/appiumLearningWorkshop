import io.appium.java_client.Setting;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;


public class firstAppiumTest_ChromeSearch {

//    Chrome Automation
    @Test
    public void searchAvengersInChrome()
    {
         WebDriver driver;

        try {

            UiAutomator2Options options =  new UiAutomator2Options();
//            options.setDeviceName("Pixel_4_API_33");
            options.setCapability("browserName","Chrome");
            String chromedriverPath = getClass().getClassLoader().getResource("chromedriver").getPath(); //ApiDemos-debug.apk

            options.setChromedriverExecutable(chromedriverPath);  //chromedriver
            driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723"), options);
//            driver.setSetting(Setting.KEY_INJECTION_DELAY, 500);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            driver.get("http://google.com");
            Thread.sleep(1000);
            driver.findElement(By.xpath("//input[@name='q']")).sendKeys("Avengers: Endgame");
            Thread.sleep(1000);
            driver.findElement(By.xpath("//span[contains(text(),'Avengers: Endgame')]")).click();
            Thread.sleep(1000);


//            WebElement elem = driver.findElement(By.xpath("//div[@data-attrid='title']//span"));
//            WebElement elem = driver.findElement(By.xpath("//span[@role='heading']"));
            WebElement elem = driver.findElement(By.xpath("//*[text()='Avengers: Endgame']"));

//            System.out.println("elem" +  elem.getText());
            Assert.assertEquals(elem.getText(),"Avengers: Endgame", "Compare text Avengers : Endgame");
            driver.quit();
        }
        catch (Exception e )
        {
            e.printStackTrace();
            Assert.fail();
        }
    }
}

