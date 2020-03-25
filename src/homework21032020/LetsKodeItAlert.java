package homework21032020;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class LetsKodeItAlert {

    private WebDriver driver;
    private String baseUrl = "https://learn.letskodeit.com/";
    private JavascriptExecutor js;

    @Before
    public void openBrowser() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NONE);
        driver = new ChromeDriver(options);
        js = (JavascriptExecutor) driver;
        driver.manage().window().setPosition(new Point(-2000, 0));//display 2
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(baseUrl);
    }
    /*
    TEST - Select Practice link, scroll down and select Alert and accept alert.
        */
    @Test
    public void tackleAlert() throws InterruptedException {
        WebElement practiceLink = driver.findElement(By.linkText("Practice"));
        practiceLink.click();
        //to scroll the page down
        js.executeScript("window.scrollBy(0, -1800);");
        Thread.sleep(2000);

        WebElement alertLink = driver.findElement(By.id("alertbtn"));
        alertLink.click();
        Thread.sleep(2000);

        Alert alert = driver.switchTo().alert();
        System.out.println(alert.getText());
        Thread.sleep(3000);
        alert.accept();
    }

    @After
    public void closeBrowser() {
        driver.quit();
    }
}
