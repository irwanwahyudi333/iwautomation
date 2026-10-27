package id.co.juaracoding.selenium;

import id.co.juaracoding.selenium.pages.ForgotPasswordPage;
import id.co.juaracoding.util.TestConfig;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class LupaPasswordTest extends BaseSeleniumTest {

    @Test
    public void should_trigger_reset_password_when_email_registered() throws InterruptedException {
        bukaHalaman("/forgot-password");

        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        forgotPasswordPage.submitForgotPassword(TestConfig.SEED_CUSTOMER_EMAIL);

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("/check-email"));

        Assert.assertTrue(driver.getCurrentUrl().contains("/check-email"),
                "Setelah submit email terdaftar, harus diarahkan ke /check-email. URL aktual: " + driver.getCurrentUrl());
        Assert.assertTrue(forgotPasswordPage.isMagicLinkDisplayed(),
                "Tautan magic link reset password harus muncul untuk email yang terdaftar");
    }

    @Test
    public void should_show_error_toast_when_captcha_wrong() throws InterruptedException {
        bukaHalaman("/forgot-password");

        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        forgotPasswordPage.submitForgotPasswordWithCaptcha(TestConfig.SEED_CUSTOMER_EMAIL, "wrongcaptcha");

        Thread.sleep(1500);

        Assert.assertTrue(forgotPasswordPage.isErrorToastShown(),
                "Toast error harus tampil saat captcha salah pada form lupa password");
        Assert.assertTrue(driver.getCurrentUrl().contains("/forgot-password"),
                "Browser harus tetap berada di /forgot-password saat proses gagal");
    }
}
