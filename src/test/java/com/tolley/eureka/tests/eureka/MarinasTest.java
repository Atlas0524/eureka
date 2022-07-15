package com.tolley.eureka.tests.eureka;

import com.tolley.eureka.listener.EurekaTestListener;
import com.tolley.eureka.pageobjects.BasePageObject;
import com.tolley.eureka.pageobjects.landingpage.LandingPage;
import com.tolley.eureka.util.ScreenshotUtil;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(EurekaTestListener.class)
public class MarinasTest {

  @Test(groups = "MARINAS")
  public void testLandingPageLayout() throws Exception {
    LandingPage.openLandingPage();
    ScreenshotUtil.takeScreenShot("Landing Page", BasePageObject.getDriver());
  }

  @Test(groups = "MARINAS")
  public void testSignupPageLayout() throws Exception {
    LandingPage.openLandingPage();
    LandingPage.clickSignUpButton();
    ScreenshotUtil.takeScreenShot("Sign Up Page", BasePageObject.getDriver());
  }

  @Test(groups = "MARINAS")
  public void testLoginPageLayout() throws Exception {
    LandingPage.openLandingPage();
    LandingPage.clickLogInButton();
    ScreenshotUtil.takeScreenShot("Log In Page", BasePageObject.getDriver());
  }

}
