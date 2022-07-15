package com.tolley.eureka.pageobjects;

import com.tolley.eureka.annotation.EurekaDriver;
import com.tolley.eureka.driver.WebDriverManager;

public class BasePageObject {

    @EurekaDriver
    private static ThreadLocal<WebDriverManager> webDriverManagerThread = new ThreadLocal<WebDriverManager>();

    public static WebDriverManager getDriver(){
        return webDriverManagerThread.get();
    }

    public static String APP_URL = System.getenv("APP_URL");

}
