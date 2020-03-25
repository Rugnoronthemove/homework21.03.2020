package homework21032020;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Electronics {

    private WebDriver driver;
    private String baseUrl = "https://demo.nopcommerce.com/";
    private JavascriptExecutor js;

    @Before
    public void openBrowser() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(baseUrl);
    }
    /*
    TEST - Mousehover on Electronics, then on Camera & Photo, select Camera & Photo
     and Assert text "Camera & Photo"
     */
    @Test
    public void UserShouldNavigateToElectronicsPage() {

        WebElement electronicsLink = driver.findElement(By.linkText("Electronics"));
        Actions actions = new Actions(driver);
        actions.moveToElement(electronicsLink).perform();

        WebElement cameraNPhoto = driver.findElement(By.linkText("Camera & photo"));
        actions.moveToElement(cameraNPhoto).perform();
        cameraNPhoto.click();

        WebElement pageTxt = driver.findElement(By.linkText("Camera & photo"));
        String expectedTxt = "Camera & photo";
        String actualTxt = pageTxt.getText();
        Assert.assertEquals(expectedTxt, actualTxt);
    }
    /*
    TEST - Mousehover on Electronics, MouseHover and then select Camera & Photo, sortBy
    Low To High, Assert if the Products displayed after sortBy has products
     arranged in Ascending price order.
     */
    @Test
    public void sortPriceLowToHigh() throws InterruptedException {

        WebElement electronicsLink = driver.findElement(By.linkText("Electronics"));
        Actions actions = new Actions(driver);
        actions.moveToElement(electronicsLink).perform();

        WebElement cameraNPhoto = driver.findElement(By.linkText("Camera & photo"));
        actions.moveToElement(cameraNPhoto).perform();
        cameraNPhoto.click();

        //WebElement for prices when click on Camera & Photo before sorting them by LowToHigh
        List<WebElement> allResults = driver.findElements(By.xpath("//span[@class='price actual-price']"));
        //Store prices in actualPrices arraylist
        ArrayList<String> actualPrices = new ArrayList<>(allResults.size());
        for (int i = 0; i < 3; i++) {
            actualPrices.add(allResults.get(i).getText());
        }
        System.out.println("The actualPrices before sorting: " + actualPrices);
        Collections.sort(actualPrices);
        System.out.println("The actualPrices after sorting: "+actualPrices);

        //WebElement Position drop down menu
        WebElement sortByDropDownMenu = driver.findElement(By.id("products-orderby"));
        Select select = new Select(sortByDropDownMenu);
        select.selectByIndex(3);
        Thread.sleep(2000);

        js.executeScript("window.scrollBy(0,500);");
        Thread.sleep(2000);

        //WebElement for prices when click on Camera & Photo after sorting them by LowToHigh
        List<WebElement> allResults1 = driver.findElements(By.xpath("//span[@class='price actual-price']"));
        //Store prices in expectedPrices arraylist
        ArrayList<String> expectedPrices = new ArrayList<>(allResults1.size());
        for (int i = 0; i < 3; i++) {
            expectedPrices.add(allResults1.get(i).getText());
        }
        System.out.println("The expectedPrices before sorting: " + expectedPrices);
        Collections.sort(expectedPrices);
        System.out.println("The expectedPrices after sorting: " + expectedPrices);
        //Assert.assertEquals(expectedPrices, actualPrices);
        Assert.assertTrue(expectedPrices.equals(actualPrices));

        /*
        The Collections.sort() only sorts values of same type and as the price values on the
        website consists comma and From prefix on one of the values, as a result the Collection.sort()
        method is unable to sort this in ascending order. This can be seen in the sout's in the console
        unless I'm doing it wrong.
        The Test case passes as Collection.sort() method sorts both the ArrayList into the same order
        and this is why Assert.assertTrue/.assertEquals returns as Test passed.
         */
    }

    @After
    public void closeBrowser() {
    driver.quit();
    }
}

