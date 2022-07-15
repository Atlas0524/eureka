package com.tolley.eureka.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class WebDriverManager {
  //global implicit wait time
  private static final long GLOBAL_WEBDRIVER_WAIT_IN_SECONDS = 10;

  private ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
  private static ThreadLocal<WebDriverManager> webDriverManagerThreadInstance = new ThreadLocal<WebDriverManager>();

  private WebDriverWait wait;
  private Actions actions;

  /**
   * Create a standard WebDriverManager instance.
   * @param driver is the type of driver (browser) to be used
   */
  public WebDriverManager(WebDriver driver) {
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(GLOBAL_WEBDRIVER_WAIT_IN_SECONDS));
    actions = new Actions(driver);
    setDriver(driver);
  }

  /**
   * Builds, sets and retrieves the WebDriverManager created.
   * @param driver The WebDriver currently being used.
   * @return WebDriverManager instance created
   */
  public static WebDriverManager getInstance(WebDriver driver) {
    webDriverManagerThreadInstance.set(new WebDriverManager(driver));
    return webDriverManagerThreadInstance.get();
  }

  /**
   * Gets a threadlocal WebDriver instance.
   * @return threadlocal WebDriver
   */
  public WebDriver getDriver() {
    return driver.get();
  }

  /**
   * Sets a WebDriver instance.
   * @param webDriver New WebDriver to set to.
   */
  private void setDriver(WebDriver webDriver) {
    driver.set(webDriver);
  }

  /**
   * Navigates to the provided URL.
   * @param url String containing the target page url.
   */
  public void goToUrl(String url) {
    getDriver().navigate().to(url);
  }

  /**
   * Fills a page element with the provided text. Sends key presses
   * as if a user was typing in the text.
   * @param locator used to find the page element
   * @param text    to enter into the found page element
   */
  public void sendText(By locator, String text) {
    WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    element.sendKeys(text);
  }

  /**
   * Clicks a page element and waits for the page to reload.
   * @param locator By locator used to find the page element
   */
  public void clickElementWaitForPageLoad(By locator) {
    ExpectedConditions.elementToBeClickable(locator);
    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
    element.click();
    wait.until(ExpectedConditions.stalenessOf(element));
  }

  /**
   * Hovers over an element
   * @param locator By locator used to find the page element
   */
  public void hoverOnElement(By locator) {
    ArrayList<WebElement> elements = getElements(locator);
    WebElement element = elements.get(0);
    actions.moveToElement(element);
    actions.build().perform();
  }

  /**
   * Scrolls to an element
   * @param locator By locator used to find the page element
   */
  public void scrollToElement(By locator) {
    ArrayList<WebElement> elements = getElements(locator);
    WebElement element = elements.get(0);
    ((JavascriptExecutor)getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
  }

  /**
   * Clicks a page element
   * @param locator By locator used to find the page element
   */
  public void click(By locator) {
    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
    moveMouseToElement(element);
    element.click();
  }

  /**
   * Returns all elements on page which match locator
   * @param locator By locator used to find the elements
   */
  public ArrayList<WebElement> getElements(By locator) {
    List<WebElement> elements = getDriver().findElements(locator);
    return new ArrayList<>(elements);
  }

  /**
   * Waits for an element to be visible
   * @param locator By locator used to find the element
   */
  public void waitForElementToBeVisible(By locator) {
    wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  /**
   * Waits for an element to be present
   * @param locator By locator used to find the element
   */
  public void waitForElementToBePresent(By locator) {
    wait.until(ExpectedConditions.presenceOfElementLocated(locator));
  }

  /**
   * Moves the mouse to the specified element
   */
  public void moveMouseToElement(WebElement element){
    Actions actions = new Actions(getDriver());
    actions.moveToElement(element);
  }

  /**
   * Waits for an element to become stale using a By locator
   * @param locator By locator used to find the element
   */
  public void waitForElementToBecomeStale(By locator){
    try {
      WebElement element = findElement(locator);
      wait.until(ExpectedConditions.stalenessOf(element));
    }catch (Exception e){

    }
  }

  /**
   * Gets the current URL of the page
   * @return String of the current URL
   */
  public String getCurrentURL() {
    return getDriver().getCurrentUrl();
  }

  /**
   *
   * @param locator By locator of the element to be found
   * @return WebElement of the found element
   */
  public WebElement findElement(By locator) {
    return getDriver().findElement(locator);
  }

  /**
   * Verifies supplied text is present on the Page
   * @param text of what to verify
   * @return boolean if the text exists
   */
  public boolean isTextPresent(String text) {
    return getDriver().getPageSource().contains(text);
  }

  /**
   * Determines if an element is clickable
   * @param locator of the element to determine if it's clickable
   * @return boolean is element is clickable
   */
  public boolean isElementClickable(By locator) {
    WebElement element = findElement(locator);
    try {
      wait.until(ExpectedConditions.elementToBeClickable(element));
      return true;
    }catch (Exception e){
      return false;
    }
  }

  /**
   * Determines if the element is on the page
   * @param locator of the element
   * @return
   */
  public boolean isElementPresent(By locator) {
    return getDriver().findElements(locator).size() > 0;
  }

  /**
   * Quits the current browser.
   */
  public void quit() {
    getDriver().quit();
  }

}
