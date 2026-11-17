package com.base;

import com.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.List;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final JavascriptExecutor js;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInt("explicit.wait.seconds")));
        this.js = (JavascriptExecutor) driver;
    }

    protected WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void hideNoticeContainers() {
        try {
            js.executeScript(
                "var containers = document.querySelectorAll('[id*=\"notice\"], .notice-container'); " +
                "containers.forEach(function(el) { el.style.display='none'; });");
        } catch (Exception e) {
            // Ignore if no notice containers exist
        }
    }

    protected void click(By locator) {
        hideNoticeContainers();
        try {
            WebElement element = waitForClickable(locator);
            element.click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            // If click is intercepted, use JavaScript to click
            WebElement element = waitForVisible(locator);
            js.executeScript("arguments[0].click();", element);
        }
    }

    protected void type(By locator, String text) {
        WebElement element = waitForVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return waitForVisible(locator).getText().trim();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return waitForVisible(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected void hoverOver(By locator) {
        WebElement element = waitForVisible(locator);
        new Actions(driver).moveToElement(element).perform();
    }

    protected void selectByVisibleText(By locator, String visibleText) {
        Select dropdown = new Select(waitForVisible(locator));
        dropdown.selectByVisibleText(visibleText);
    }

    protected List<WebElement> findAll(By locator) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        return driver.findElements(locator);
    }

    protected String getAttribute(By locator, String attributeName) {
        return waitForVisible(locator).getAttribute(attributeName);
    }

    protected void scrollIntoView(By locator) {
        WebElement element = waitForVisible(locator);
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected String getValidationMessage(By locator) {
        WebElement element = waitForVisible(locator);
        return (String) js.executeScript("return arguments[0].validationMessage;", element);
    }
}
