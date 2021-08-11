package ru.raiffeisen.tests.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class BaseTests {

    private WebDriver driver;
    private Wait wait;

    public WebDriver getDriver() {
        return driver;
    }

    public Wait getWait() {
        return wait;
    }

    @BeforeEach
    public void beforeEach () {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 5);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.navigate().to("https://www.raiffeisen.ru/");
    }

    @AfterEach
    public void afterEach () {
        driver.quit();
    }

    public WebElement clickElement (By by) {
        try {
            WebElement element = getDriver().findElement(by);
            element.click();
            return element;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public WebElement fillField (By by, String str) {
        try {
            WebElement element = getDriver().findElement(by);
            element.sendKeys(str);
            Assertions.assertEquals(str, element.getAttribute("value"), "Field is not filled");
            return element;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public WebElement clickAndFillField (By by, String str) {
        try {
            WebElement element = getDriver().findElement(by);
            element.click();
            element.sendKeys(str);
            Assertions.assertEquals(str, element.getAttribute("value"), "Field is not filled");
            return element;
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public WebElement fillPhoneNumber (By by, String str) {
        try {
            WebElement element = getDriver().findElement(by);
            element.click();
            element.sendKeys(str);
            Assertions.assertEquals("+7 " + str, element.getAttribute("value"), "Field is not filled");
            return element;
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public WebElement checkExistance (By by, String str) {
        try {
            WebElement element = getDriver().findElement(by);
            Assertions.assertTrue(element.getText().contains(str), "Previus action was not executed");
            return element;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
