package com.tolley.eureka.pageobjects.loginpage;

import com.tolley.eureka.pageobjects.BasePageObject;
import org.openqa.selenium.By;

public class LoginPage extends BasePageObject {

    private static final By USERNAME_LOCATOR = new By.ById("user_login");
    private static final By PASSWORD_LOCATOR = new By.ById("user_password");
    private static final By LOGIN_BUTTON_LOCATOR = new By.ByCssSelector("input[value=\"Log in\"]");

    public static void login(String username, String password){
        getDriver().goToUrl(APP_URL);
        enterUsername(username);
        enterPassword(password);
        clickLogIn();
    }

    public static void enterUsername(String userName){
        getDriver().sendText(USERNAME_LOCATOR, userName);
    }

    public static void enterPassword(String userName){
        getDriver().sendText(PASSWORD_LOCATOR, userName);
    }

    public static void clickLogIn(){
        getDriver().clickElementWaitForPageLoad(LOGIN_BUTTON_LOCATOR);
    }
}
