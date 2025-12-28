package com.lambdatest;

import java.lang.reflect.Method;
import java.util.HashMap;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.chrome.ChromeOptions;


public class TestNGTodo1 {

    private RemoteWebDriver driver;
    private String Status = "failed";

    @BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {
        String username = System.getenv("LT_USERNAME") == null ? "kbhanutejaswini" : System.getenv("LT_USERNAME");
        String authkey = System.getenv("LT_ACCESS_KEY") == null ? "LT_E2lbrv0hO5QYBA1gBlI63312f1fY97Bcbr2KlLZg5Fi2RkE" : System.getenv("LT_ACCESS_KEY");

        String hub = "@hub.lambdatest.com/wd/hub";
        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 10");
        browserOptions.setBrowserVersion("dev");
        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("username", "Your LambdaTest Username");
        ltOptions.put("accessKey", "Your LambdaTest Access Key");
        ltOptions.put("visual", true);
        ltOptions.put("video", true);
        ltOptions.put("network", true);
        ltOptions.put("build", "Lambdatest");
        ltOptions.put("project", "selenium101");
        ltOptions.put("name", "testscenario");
        ltOptions.put("tunnel", true);
        ltOptions.put("networkThrottling", "Regular 3G");
        ltOptions.put("w3c", true);
        ltOptions.put("plugin", "java-testNG");
        ltOptions.put("accessibility", true);
        browserOptions.setCapability("LT:Options", ltOptions);

        // ✅ Use LambdaTest W3C-compliant structure (LT:Options)
        MutableCapabilities ltOptions1 = new MutableCapabilities();
        ltOptions1.setCapability("build", "TestNG With Java");
        ltOptions1.setCapability("name", m.getName() + " - " + this.getClass().getName());
        ltOptions1.setCapability("platformName", "macOS Sonoma"); // modern macOS
        ltOptions1.setCapability("plugin", "git-testng");
        ltOptions1.setCapability("tags", new String[] { "Feature", "Falcon", "Severe" });
        

        /*
        Enable Smart UI Project (optional)
        ltOptions.setCapability("smartUI.project", "<Project Name>");
        */

        // ✅ Safari browser setup (works with Selenium 4.x)
        SafariOptions browserOptions1 = new SafariOptions();
        browserOptions1.setCapability("browserVersion", "latest");
        browserOptions1.setCapability("LT:Options", ltOptions);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), browserOptions1);
        
    }

    @Test
    public void basicTest() throws InterruptedException {
        System.out.println("Loading Url");
       
        driver.get("https://lambdatest.github.io/sample-todo-app/");
        WebDriver driver = new ChromeDriver();

        try {
            // 1. Open LambdaTest’s Selenium Playground
            driver.get("https://www.lambdatest.com/selenium-playground");
            driver.manage().window().maximize();

            // 2. Click “Simple Form Demo” using XPath
            WebElement simpleFormDemoLink = driver.findElement(By.xpath("//a[text()='Simple Form Demo']"));
            simpleFormDemoLink.click();

            // 3. Validate that the URL contains “simple-form-demo”
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.contains("simple-form-demo")) {
                System.out.println("URL validation passed: " + currentUrl);
            } else {
                System.out.println("URL validation failed: " + currentUrl);
            }

            // 4. Create a variable for a string value
            String inputmessage = "Welcome to LambdaTest";

            // 5. Enter values in the “Enter Message” text box using XPath (attribute-based)
            WebElement messageInput = driver.findElement(By.xpath("//input[@id='user-message']"));
            messageInput.sendKeys(inputmessage);

            // 6. Click “Get Checked Value” using XPath (contains function)
            WebElement getCheckedValueButton = driver.findElement(By.xpath("//button[contains(text(),'Get Checked Value')]"));
            getCheckedValueButton.click();

            // 7. Validate whether the same text message is displayed using XPath (relative path)
            WebElement displayedMessage = driver.findElement(By.id("message"));
            String output = displayedMessage.getText();

            if (output.equals(inputmessage)) {
                System.out.println("Message validation passed: " + output);
            } else {
                System.out.println("Message validation failed. Expected: " + inputmessage + " actual: " + output);
            }

        } finally {
            driver.quit();
        }
    }
       |}


    @AfterMethod
    public void tearDown() {
        try {
            driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"Adding Test Result and Closing Browser\", \"level\": \"info\"}}");
            driver.executeScript("lambda-status=" + Status);
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
