package id.co.juaracoding.selenium;

import id.co.juaracoding.selenium.pages.DashboardPage;
import id.co.juaracoding.selenium.pages.LoginPage;
import id.co.juaracoding.util.TestConfig;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest extends BaseSeleniumTest {

    @Test
    public void should_redirect_to_dashboard_when_login_valid() throws InterruptedException {
        bukaHalamanLogin();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs(TestConfig.VALID_USERNAME, TestConfig.VALID_PASSWORD);

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("/dashboard"));

        Assert.assertTrue(driver.getCurrentUrl().contains("/dashboard"),
                "Setelah login valid, browser harus pindah ke /dashboard. URL aktual: " + driver.getCurrentUrl());

        DashboardPage dashboardPage = new DashboardPage(driver);
        Assert.assertTrue(dashboardPage.getLoggedInUserName().contains(TestConfig.VALID_USER_FULL_NAME),
                "Nama user yang login harus tampil di navbar. Aktual: " + dashboardPage.getLoggedInUserName());
    }

    @Test
    public void should_show_error_toast_when_password_wrong() throws InterruptedException {
        bukaHalamanLogin();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs(TestConfig.VALID_USERNAME, TestConfig.INVALID_PASSWORD);

        Thread.sleep(1500);
        Assert.assertTrue(loginPage.isErrorToastShown(), "Toast error harus muncul saat password salah");
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"),
                "Login gagal TIDAK boleh pindah dari /login. URL aktual: " + driver.getCurrentUrl());
    }
}
