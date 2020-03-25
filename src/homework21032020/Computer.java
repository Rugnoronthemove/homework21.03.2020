package homework21032020;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class Computer {

    private WebDriver driver;
    private String baseUrl = "https://demo.nopcommerce.com/";
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
    TEST - User should navigate to Computers page & Assert text "Computers"
     */
    @Test
    public void UserShouldNavigateToComputerPage() {
        driver.findElement(By.linkText("Computers")).click();
        WebElement assertTxt = driver.findElement(By.xpath("//div[@class='page-title']/h1 "));
        String expectedTxt = "Computers";
        String actualTxt = assertTxt.getText();
        Assert.assertEquals(actualTxt, expectedTxt);
    }
    /*
    TEST - User should navigate to Computers page, select Build your own computer,
    select 400gb HDD option radio button, select Add to cart & Assert message "The product has been added to your shopping cart"
     */
    @Test
    public void addProductToShoppingCart() throws InterruptedException {

        driver.findElement(By.linkText("Computers")).click();
        //element for desktop
        driver.findElement(By.xpath("//li[@class='inactive']//a[@href='/desktops']")).click();
        Thread.sleep(2000);
        //to scrool the page down
        js.executeScript("window.scrollBy(0, -1800);");
        Thread.sleep(2000);
        //element for build your own computer
        driver.findElement(By.xpath("//h2[@class='product-title']/a[contains(text(),'Build your own computer')]")).click();
        //to scroll the page down
        js.executeScript("window.scrollBy(0, -1800);");
        Thread.sleep(2000);
        //element for 400gb hdd hardriver radio button
        driver.findElement(By.id("product_attribute_3_7")).click();
        //element for add to cart button
        driver.findElement(By.id("add-to-cart-button-1")).click();
        //element for message for add to cart
        WebElement productAddedMsg = driver.findElement(By.xpath("//p[@class='content']"));
        String expectedMsg = "The product has been added to your shopping cart";
        String actualMsg = productAddedMsg.getText();
        Assert.assertEquals(actualMsg, expectedMsg);
    }

    @After
    public void closeBrowser() throws InterruptedException {
        Thread.sleep(3000);
        driver.close();
    }

}
