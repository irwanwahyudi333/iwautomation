package id.co.juaracoding.selenium;

import id.co.juaracoding.selenium.pages.LicenseGatePage;
import id.co.juaracoding.selenium.pages.LoginPage;
import id.co.juaracoding.util.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;
import java.util.List;

public class BaseSeleniumTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1366,900");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        if ("true".equalsIgnoreCase(System.getProperty("headless"))) {
            options.addArguments("--headless=new");
        }
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void bukaHalaman(String path) {
        driver.get(TestConfig.BASE_URL + path);

        // Penanganan gerbang Lisensi jika muncul
        LicenseGatePage licenseGatePage = new LicenseGatePage(driver);
        if (licenseGatePage.isDisplayed()) {
            licenseGatePage.activate(TestConfig.LICENSE_KEY);
            driver.get(TestConfig.BASE_URL + path);
        }

        // Penanganan gerbang .env jika muncul
        List<WebElement> envSubmitButtons = driver.findElements(By.cssSelector("[data-testid='env-submit-button']"));
        if (!envSubmitButtons.isEmpty() && envSubmitButtons.get(0).isDisplayed()) {
            List<WebElement> envInputs = driver.findElements(By.cssSelector("[data-testid='env-field-jwtDurationMinutes'] input, [data-testid='env-field-jwtDurationMinutes']"));
            if (!envInputs.isEmpty()) {
                envInputs.get(0).clear();
                envInputs.get(0).sendKeys("60");
            }
            envSubmitButtons.get(0).click();
            driver.get(TestConfig.BASE_URL + path);
        }

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains(path));
    }

    protected void bukaHalamanLogin() {
        bukaHalaman("/login");
    }

    protected void loginSebagai(String username, String password) throws InterruptedException {
        bukaHalamanLogin();
        new LoginPage(driver).loginAs(username, password);
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("/dashboard"));
    }
}
