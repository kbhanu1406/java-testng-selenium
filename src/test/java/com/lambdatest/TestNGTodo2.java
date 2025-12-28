package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestNGTodo2 {

    private RemoteWebDriver driver;
    private String Status = "failed";

    @BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {
        String username = System.getenv("LT_USERNAME") == null ? "Your LT Username" : System.getenv("LT_USERNAME");
        String authkey = System.getenv("LT_ACCESS_KEY") == null ? "Your LT AccessKey" : System.getenv("LT_ACCESS_KEY");

        String hub = "@hub.lambdatest.com/wd/hub";

        // ✅ LambdaTest-specific options (must go under "LT:Options")
        MutableCapabilities ltOptions = new MutableCapabilities();
        ltOptions.setCapability("build", "TestNG With Java");
        ltOptions.setCapability("name", m.getName() + this.getClass().getName());
        ltOptions.setCapability("platformName", "Windows 10");
        ltOptions.setCapability("plugin", "git-testng");
        ltOptions.setCapability("tags", new String[] { "Feature", "Magicleap", "Severe" });

        /*
        Enable Smart UI Project
        ltOptions.setCapability("smartUI.project", "<Project Name>");
        */

        // ✅ Standard browser options
        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setCapability("browserVersion", "latest");
        browserOptions.setCapability("LT:Options", ltOptions);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), browserOptions);
    }

    @Test
    public void basicTest() throws InterruptedException {
       WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://www.lambdatest.com/selenium-playground");
            driver.manage().window().maximize();

            driver.findElement(By.linkText("Drag & Drop Sliders")).click();

            WebElement slider = driver.findElement(By.xpath("//input[@value='15']"));
            WebElement rangeValue = driver.findElement(By.id("rangeSuccess"));

            // Keep pressing ARROW_RIGHT until value becomes 95
            while (!rangeValue.getText().equals("95")) {
                slider.sendKeys(Keys.ARROW_RIGHT);
            }

            System.out.println("Slider moved to: " + rangeValue.getText());

        } finally {
            driver.quit();
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.executeScript("lambda-status=" + Status);
            driver.quit();
        }
    }
}
