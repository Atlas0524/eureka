## Eureka: A powerful yet simple WebDriver framework
To run Eureka locally, you need to do the following:
- Clone this repo
- Set ENV variables:
  - a. APP_URL: The URL of the app you want to test, example:https://marinas.com
To run the application locally from the command line:
- The following must be installed:
  - Java jdk 18
  - gradle 7.4.2
- run the commands:
```
export APP_URL=https://marinas.com
./gradlew test
```
This will open marinas.com and run 3 tests that do the following:
- Take a screenshot of the landing page
- Take a screenshot of the log in page
- Take a screenshot of the sign up page
The screenshots will be located under:
```
test-artifacts/screenshots/Landing Page.png
test-artifacts/screenshots/Log In Page.png
test-artifacts/screenshots/Sign Up Page.png
```
