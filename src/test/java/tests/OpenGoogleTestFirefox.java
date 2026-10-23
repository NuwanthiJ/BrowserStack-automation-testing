package tests;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class OpenGoogleTestFirefox {

    @Test
    public void openGoogleFirefox() throws Exception {

        String username = "nuwanthijayashan_sbgirz";
        String accessKey = "zea83Gz2piCgoaP3v5nX";

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browserName", "firefox");
        caps.setCapability("browserVersion", "latest");

        Map<String, Object> bstackOptions = new HashMap<>();
        bstackOptions.put("os", "Windows");
        bstackOptions.put("osVersion", "11");
        bstackOptions.put("projectName", "BrowserStack Demo Project");
        bstackOptions.put("buildName", "CrossBrowserTesting_" + System.currentTimeMillis());
        bstackOptions.put("sessionName", "Firefox Google Test");

        caps.setCapability("bstack:options", bstackOptions);

        WebDriver driver = new RemoteWebDriver(
                new URL("https://" + username + ":" + accessKey + "@hub.browserstack.com/wd/hub"),
                caps
        );

        try {
            driver.get("https://myslt.slt.lk/");
            System.out.println("Opened MySLT Website on Firefox");

            // Wait for the login button using XPath
            WebDriverWait wait = new WebDriverWait(driver,
                    Duration.ofSeconds(15));
            WebElement loginButton =
                    wait.until(ExpectedConditions.elementToBeClickable(

                            By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[2]/button")
                    ));
            loginButton.click();

            System.out.println("Clicked Login Button on Firefox");

            // Mark session as Passed
            ((JavascriptExecutor) driver).executeScript( "browserstack_executor: " +
                    "{\"action\":\"setSessionStatus\", \"arguments\": {\"status\": " +
                    "\"passed\", \"reason\":\"Login button clicked successfully on Firefox.\"}}"
            );
            System.out.println("Test Passed in Firefox browser on BrowserStack");

        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("browserstack_executor: " +
                    "{\"action\":\"setSessionStatus\", \"arguments\": {\"status\": " +
                    "\"failed\", \"reason\":" +
                    "\"Exception: " + e.getMessage().replace("\"", "'") + "\"}}"
            );
            e.printStackTrace();
            System.out.println(" Test Failed in Firefox browser");
        }

        driver.quit();
    }
}
