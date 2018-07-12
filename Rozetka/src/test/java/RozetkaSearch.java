import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RozetkaSearch {

    WebDriver driver;
    String url = "http://rozetka.com.ua/";

    @Before
    public void setupTest() {
        driver = new ChromeDriver();
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void test() {
        driver.get(url);
        WebElement searchField = driver.findElement(By.xpath("//*[@id='rz-search']/form/div[1]/div[2]/input"));
        searchField.sendKeys("Hyundai");
        searchField.submit();
        List<WebElement> resultOfSearch = driver.findElements(By.xpath("//*[@class='g-i-tile-i-box']"));
        for (WebElement element : resultOfSearch) {
            assertTrue(element.getText().contains("Hyundai"));
        }

        WebElement showMore = driver.findElement(By.xpath("//*[@class=\"novisited g-i-more-link\"]"));
        System.out.print(showMore.getText());
        assertEquals("Показать\nеще 32 товара", showMore.getText());
    }
}
