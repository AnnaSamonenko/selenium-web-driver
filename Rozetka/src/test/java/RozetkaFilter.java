import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.SystemClock;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

public class RozetkaFilter {

    WebDriver driver;
    String url = "http://rozetka.com.ua/mobile-phones/c80003/preset=smartfon/";

    @Before
    public void setupTest() {
        driver = new ChromeDriver();
        driver.get(url);
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testBrandSection() {
        WebDriverWait wait = new WebDriverWait(driver, 40);
        Actions actions = new Actions(driver);

        WebElement elementCheckButtonSamsung = driver.findElement(By.xpath("//*[@id=\"filter_producer_12\"]/label/a"));
        elementCheckButtonSamsung.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"reset_filter12\"]/a")));

        WebElement elementCheckButtonApple = driver.findElement(By.xpath("//*[@id=\"filter_producer_69\"]/label/a"));
        actions.moveToElement(elementCheckButtonApple).click().perform();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"reset_filter69\"]/a")));

        List<WebElement> result = driver.findElements(By.xpath("//*[@class='g-i-tile-i-box']"));

        for (WebElement el : result) {
            assertTrue(el.getText().contains("Apple") || el.getText().contains("Samsung"));
        }
    }

    @Test
    public void testPriceDescending() {
        WebDriverWait wait = new WebDriverWait(driver, 40);

        WebElement sortSwitch = driver.findElement(By.xpath("//*[@id=\"sort_view\"]/a"));
        sortSwitch.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"sort_view\"]/div")));

        WebElement buttonOfSort = driver.findElement(By.xpath("//*[@id=\"filter_sortcheap\"]/a"));
        buttonOfSort.click();

        wait.until(ExpectedConditions.urlContains("sort=cheap"));

        List<WebElement> prices = driver.findElements(By.xpath("//*[@class=\"g-price-uah\"]"));

        List<Double> pricesDouble = new LinkedList<Double>();

        for (WebElement el : prices) {
            pricesDouble.add(Double.parseDouble(el.getText().replaceAll("\\D+", "")));
        }

        assertTrue(isSortedDescending(pricesDouble));
    }

    private boolean isSortedDescending(List<Double> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i) > list.get(i + 1)) {
                return false;
            }
        }
        return true;
    }
}
