package com.tolley.eureka.util;

import org.testng.ITestResult;

public class StatusPrinter {

    /**
     * Indicates we are starting the test run
     */
    public static void printRunStart() {
        System.out.println("[STARTING FULL TEST RUN]");
    }

    /**
     * Outputs test class/method information at start of test execution
     *
     * @param tr ITestResult current test method
     */
    public static void printTestStart(ITestResult tr) {
        System.out.println("[STARTING] " + formatTestClassPath(tr));
    }

    /**
     * Outputs test success information
     *
     * @param tr ITestResult current test
     */
    public static void printTestSuccess(ITestResult tr) {
        System.out.println("[SUCCESS] " + tr.getName());
    }

    /**
     * Outputs test failure information
     *
     * @param tr ITestResult current test
     */
    public static void printTestFailure(ITestResult tr) {
        System.out.println("[FAILED] " + formatTestClassPath(tr));
    }

    /**
     * Takes the full path of the test run and formats it to be more readable
     *
     * @param tr ITestResult current test
     * @return String friendly test path format
     */
    private static String formatTestClassPath(ITestResult tr) {
        String friendlyPath = tr.getTestClass().getRealClass().getName().replace('.', '/');
        return friendlyPath + "#" + tr.getName();
    }
}
