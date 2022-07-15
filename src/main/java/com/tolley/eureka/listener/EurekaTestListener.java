package com.tolley.eureka.listener;

import com.tolley.eureka.annotation.EurekaDriver;
import com.tolley.eureka.driver.WebDriverFactory;
import com.tolley.eureka.driver.WebDriverManager;
import com.tolley.eureka.util.ScreenshotUtil;
import com.tolley.eureka.util.StatusPrinter;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Paths;

import static org.apache.commons.io.FileUtils.deleteDirectory;

public class EurekaTestListener extends TestListenerAdapter {

  ThreadLocal<WebDriverManager> managerThread = new ThreadLocal<WebDriverManager>();
  ThreadLocal<WebDriver> driverThread = new ThreadLocal<WebDriver>();
  ThreadLocal<WebDriverFactory> webDriverFactoryThread = new ThreadLocal<WebDriverFactory>();

  /**
   * This is the entry point for the entire test run
   *
   * @param tc is the test suite context
   */
  @Override
  public void onStart(ITestContext tc) {
    cleanEurekaOutput();
    StatusPrinter.printRunStart();
  }

  /**
   * This is the entry point for an individual test method running
   *
   * @param tr the test method context
   */
  @Override
  public void onTestStart(ITestResult tr) {
    super.onTestStart(tr);

    StatusPrinter.printTestStart(tr);

    webDriverFactoryThread.set(new WebDriverFactory());

    try {
      driverThread.set(webDriverFactoryThread.get().createInstance(tr.getTestName()));
    } catch (Exception e) {
      e.printStackTrace();
      tr.setStatus(2);
      return;
    }

    try {
      for (Field classField : tr.getTestClass().getRealClass().getClassLoader().loadClass("com.tolley.eureka.pageobjects.BasePageObject").getDeclaredFields()) {
        if (classField.isAnnotationPresent(EurekaDriver.class)) {
          try {
            classField.setAccessible(true);
            managerThread.set(WebDriverManager.getInstance(driverThread.get()));
            ((ThreadLocal<WebDriverManager>) classField.get(tr.getInstance())).set(managerThread.get());
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Triggered on test passing
   *
   * @param tr test result
   */
  @Override
  public void onTestSuccess(ITestResult tr) {
//    Object currentClass = tr.getMethod().getInstance();
//    EurekaTestHelper eurekaTestHelper = ((EurekaTestHelper) currentClass);
    StatusPrinter.printTestSuccess(tr);
    quitDriver();
  }

  /**
   * Triggered on test failure
   *
   * @param tr test result
   */
  @Override
  public void onTestFailure(ITestResult tr) {
    StatusPrinter.printTestFailure(tr);
    ScreenshotUtil.takeScreenShot("FAILURE-" + tr.getTestClass().getTestName(), managerThread.get());
    quitDriver();
  }

  /**
   * This is the exit point for the entire test run
   *
   * @param tc is the test suite context
   */
  @Override
  public void onFinish(ITestContext tc) {

  }

  private void cleanEurekaOutput() {
    try {
      deleteDirectory(new File(Paths.get("").toAbsolutePath().toString() + "/eureka"));
    } catch (Exception e) {

    }
  }

  private void quitDriver() {
    try {
      managerThread.get().quit();
    } catch (Exception e) {
    }
  }

}
