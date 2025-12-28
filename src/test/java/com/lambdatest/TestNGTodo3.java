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

public class TestNGTodo3 {

    private RemoteWebDriver driver;
    private String Status = "failed";

    @BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {
        String username = System.getenv("LT_USERNAME") == null ? "Your LT Username" : System.getenv("LT_USERNAME");
        String authkey = System.getenv("LT_ACCESS_KEY") == null ? "Your LT AccessKey" : System.getenv("LT_ACCESS_KEY");

        String hub = "@hub.lambdatest.com/wd/hub";

        // ✅ W3C-compliant capabilities using ChromeOptions + LT:Options
        MutableCapabilities ltOptions = new MutableCapabilities();
        ltOptions.setCapability("build", "TestNG With Java");
        ltOptions.setCapability("name", m.getName() + " - " + this.getClass().getName());
        ltOptions.setCapability("platformName", "macOS Sonoma"); // updated OS name
        ltOptions.setCapability("plugin", "git-testng");
        ltOptions.setCapability("tags", new String[] { "Feature", "Tag", "Moderate" });

        // ✅ Chrome-specific options
        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setBrowserVersion("latest");
        browserOptions.setCapability("LT:Options", ltOptions);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), browserOptions);
    }

    @Test
    public void basicTest() throws InterruptedException {
       WebDriver driver = new ChromeDriver();

        try {
            // 1. Open LambdaTest’s Selenium Playground
            driver.get("https://www.lambdatest.com/selenium-playground");
            driver.manage().window().maximize();

        
            // Click “Input Form Submit”
            WebElement inputFormLink = driver.findElement(By.linkText("Input Form Submit"));
            inputFormLink.click();

            // 2. Click “Submit” without filling in any information
            WebElement submitBtn = driver.findElement(By.xpath("//button[text()='Submit']"));
            submitBtn.click();

            // 3. Assert “Please fill out this field.” error message
            WebElement nameField = driver.findElement(By.xpath("//input[@id='name']"));
            String validationMessage = nameField.getAttribute("validationMessage");
            if (validationMessage.equals("Please fill out this field.")) {
                System.out.println("Validation message displayed correctly: " + validationMessage);
            } else {
                System.out.println("Validation message mismatch: " + validationMessage);
            }

            // 4. Fill in Name, Email, and other fields
            nameField.sendKeys("John");
            WebElement emailField = driver.findElement(By.id("inputEmail4"));
            emailField.sendKeys("john.doe@example.com");
            WebElement passwordField = driver.findElement(By.xpath("//input[@id='inputPassword4']"));
            passwordField.sendKeys("Password123");

            WebElement companyField = driver.findElement(By.name("company"));
            companyField.sendKeys("LambdaTest Inc.");
            WebElement websiteField = driver.findElement(By.xpath("//input[@id='websitename']"));
            websiteField.sendKeys("www.lambdatest.com");

            // 5. From the Country drop-down, select “United States” using text property
            WebElement countryDropdown = driver.findElement(By.cssSelector("select[name='country']"));
            Select countrySelect = new Select(countryDropdown);
            countrySelect.selectByVisibleText("United States");

            // Fill remaining fields
            driver.findElement(By.id("inputCity")).sendKeys("New York");
            driver.findElement(By.id("inputAddress1")).sendKeys("123 Main Street");
            driver.findElement(By.id("inputAddress2")).sendKeys("Suite 456");
            driver.findElement(By.id("inputState")).sendKeys("NY");
            driver.findElement(By.id("inputZip")).sendKeys("10001");

            // 6. Click “Submit”
            submitBtn.click();

            // 7. Validate success message
            WebElement successMsg = driver.findElement(By.xpath("//p[@class='success-msg hidden']"));
            String successText = successMsg.getText();
            if (successText.contains("Thanks for contacting us, we will get back to you shortly.")) {
                System.out.println("Success message validated: " + successText);
            } else {
                System.out.println("Success message mismatch: " + successText);
            }

        } finally {
            driver.quit();
        }
    }

    @AfterMethod
    public void tearDown() {
        try {
            driver.executeScript("lambda-status=" + Status);
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
