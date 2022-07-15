package com.tolley.eureka.driver;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class WebDriverFactory {

  private static final long GLOBAL_WEBDRIVER_IMPLICIT_WAIT_IN_SECONDS = 10;

  public WebDriver createInstance(String downloadDirectory) throws Exception {
    return buildDriver(downloadDirectory);
  }

  private WebDriver buildDriver(String downloadDirectory) throws Exception {
    Platform platform = Platform.getCurrent();

    switch (platform) {
      case LINUX: {
        System.setProperty("webdriver.chrome.driver", WebDriverFactory.class.getResource("/drivers/linux/chrome/chromedriver").getPath());
        Runtime.getRuntime().exec("chmod a+x " + WebDriverFactory.class.getResource("/drivers/linux/chrome/chromedriver").getPath());
        break;
      }
      case MAC: {
        System.setProperty("webdriver.chrome.driver", WebDriverFactory.class.getResource("/drivers/osx/chrome/chromedriver").getPath());
        Runtime.getRuntime().exec("chmod a+x " + WebDriverFactory.class.getResource("/drivers/osx/chrome/chromedriver").getPath());
        break;
      }
    }

    Path currentRelativePath = Paths.get("");
    String downloadFilepath = currentRelativePath.toAbsolutePath().toString() + "/test-artifacts/" + downloadDirectory;
    HashMap<String, Object> chromePrefs = new HashMap<>();
    chromePrefs.put("profile.default_content_settings.popups", 0);
    chromePrefs.put("download.default_directory", downloadFilepath);
    ChromeOptions options = new ChromeOptions();
    options.setExperimentalOption("prefs", chromePrefs);
    options.addArguments("--window-size=2500,3500");
    options.addArguments("start-maximized");
    System.setProperty("webdriver.chrome.silentOutput", "true");
    ChromeDriverService service = ChromeDriverService.createDefaultService();

    ChromeDriver driver = new ChromeDriver(service, options);
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(GLOBAL_WEBDRIVER_IMPLICIT_WAIT_IN_SECONDS));
    java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);

    return driver;
  }
}
