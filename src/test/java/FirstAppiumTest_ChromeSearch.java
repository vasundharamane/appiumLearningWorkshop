import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;


public class FirstAppiumTest_ChromeSearch {

//    Chrome Automation
    @Test
    public void searchAvengersInChrome()
    {
         WebDriver driver;

        try {
            String chromedriverPath = getClass().getClassLoader().getResource("chromedriver").getPath(); //ChromeDriver
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("appium:platformName", "android");
            capabilities.setCapability("browserName","Chrome");
            capabilities.setCapability("appium:automationName", "UiAutomator2");
            capabilities.setCapability("appium:chromedriverExecutable",chromedriverPath);

            String apkPath = getClass().getClassLoader().getResource("VodQA.apk").getPath(); //VodQA.apk

            driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723"), capabilities);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            driver.get("http://google.com");
            Thread.sleep(1000);
            driver.findElement(By.xpath("//input[@name='q']")).sendKeys("Avengers: Endgame");
            Thread.sleep(1000);
            driver.findElement(By.xpath("//span[contains(text(),'Avengers: Endgame')]")).click();
            Thread.sleep(1000);

            WebElement elem = driver.findElement(By.xpath("//*[text()='Avengers: Endgame']"));

            Assert.assertEquals(elem.getText(),"Avengers: Endgame", "Compare text Avengers : Endgame");
            driver.quit();
        }
        catch (Exception e )
        {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void searchAvengersInChrome1()
    {
        WebDriver driver;

        try {

            UiAutomator2Options options =  new UiAutomator2Options();
//            options.setDeviceName("Pixel_4_API_33");
            options.setCapability("browserName","Chrome");

            String chromedriverPath = getClass().getClassLoader().getResource("chromedriver").getPath(); //ChromeDriver
            options.setChromedriverExecutable(chromedriverPath);  //chromedriver

            driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723"), options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            driver.get("http://google.com");
//            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver , Duration.ofSeconds(10));
            WebElement  textbox =  driver.findElement(By.xpath("//input[@name='q']"));
            wait.until(ExpectedConditions.visibilityOf(textbox));
            textbox.sendKeys("Avengers: Endgame");
            driver.findElement(By.xpath("//span[contains(text(),'Avengers: Endgame')]")).click();
            Thread.sleep(1000);

            WebElement elem = driver.findElement(By.xpath("//*[text()='Avengers: Endgame']"));
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