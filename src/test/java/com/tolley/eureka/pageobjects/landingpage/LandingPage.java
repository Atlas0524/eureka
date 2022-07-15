package com.tolley.eureka.pageobjects.landingpage;

import com.tolley.eureka.pageobjects.BasePageObject;
import org.openqa.selenium.By;

public class LandingPage extends BasePageObject {
  //locator of the Log In button on the landing page
  private static final By LOGIN_BUTTON_LOCATOR = new By.ByLinkText("Log In");

  //locator of the Sign Up button on the landing page
  private static final By SIGNUP_BUTTON_LOCATOR = new By.ByLinkText("Sign Up");

  //opens the landing page
  public static void openLandingPage(){
    getDriver().goToUrl(APP_URL);
  }

  //clicks the Log In button on the landing page
  public static void clickLogInButton(){
        getDriver().clickElementWaitForPageLoad(LOGIN_BUTTON_LOCATOR);
    }

  //clicks the Sign Up button on the landing page
  public static void clickSignUpButton(){
    getDriver().clickElementWaitForPageLoad(SIGNUP_BUTTON_LOCATOR);
  }
}
