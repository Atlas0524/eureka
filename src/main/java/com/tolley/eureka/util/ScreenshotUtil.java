package com.tolley.eureka.util;

import com.tolley.eureka.driver.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScreenshotUtil {

  private static final String SCREENSHOT_DIRECTORY = "/test-artifacts/screenshots/";

  public static void takeScreenShot(String screenshotFileName, WebDriverManager driver) {
    Path currentRelativePath = Paths.get("");
    String screenshotFileDirectory = currentRelativePath.toAbsolutePath().toString() + SCREENSHOT_DIRECTORY;
//    Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(2000)).takeScreenshot(driver.getDriver());
    try {
      File screenshotFileDirectoryDirectory = new File(screenshotFileDirectory);
      if (!screenshotFileDirectoryDirectory.exists())
        screenshotFileDirectoryDirectory.mkdirs();
      File screenshot = ((TakesScreenshot) driver.getDriver()).getScreenshotAs(OutputType.FILE);
      FileUtils.copyFile(screenshot, new File(screenshotFileDirectory + screenshotFileName + ".png"));
//      ImageIO.write(screenshot.getImage(), "PNG", new File(screenshotFileDirectory + screenshotFileName + ".png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
