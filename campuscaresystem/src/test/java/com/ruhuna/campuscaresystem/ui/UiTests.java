package com.ruhuna.campuscaresystem.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class UiTests {

    private WebDriver driver;

    @BeforeEach
    void setUpDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1280,900");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void teardown() {
        if (driver != null) driver.quit();
    }

    @Test
    void signupAndLogin_flow() {
        driver.get("http://localhost:5173/signup");
        driver.findElement(By.id("name")).sendKeys("Test User");
        String email = "test" + System.currentTimeMillis() + "@example.com";
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("password")).sendKeys("password123");
        driver.findElement(By.cssSelector("button.signup-btn")).click();

        // Handle unexpected alert if present
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            System.out.println("Alert says: " + alert.getText());
            alert.accept();
        } catch (TimeoutException ignored) {
            // No alert appeared — continue normally
        }

        // Wait for redirect to login
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("/login"));

        assertThat(driver.getCurrentUrl()).contains("/login");

        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("password")).sendKeys("password123");
        driver.findElement(By.cssSelector("button.login-btn")).click();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlToBe("http://localhost:5173/"));

        assertThat(driver.getCurrentUrl()).endsWith("/");
    }

    @Test
    void addIssue_flow() {
        driver.get("http://localhost:5173/add-update");

        driver.findElement(By.id("title")).sendKeys("Window broken - UI");
        driver.findElement(By.id("category")).sendKeys("Broken");
        driver.findElement(By.id("status")).sendKeys("Pending");
        driver.findElement(By.id("description")).sendKeys("A broken window found by UI test");
        driver.findElement(By.id("location")).sendKeys("Library");
        driver.findElement(By.id("date")).sendKeys("01-03-2025");

        // Skip image upload — it's optional

        driver.findElement(By.cssSelector("button.submit-btn")).click();

        // Wait for redirect to home
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlToBe("http://localhost:5173/"));

        assertThat(driver.getCurrentUrl()).endsWith("/");
    }
}