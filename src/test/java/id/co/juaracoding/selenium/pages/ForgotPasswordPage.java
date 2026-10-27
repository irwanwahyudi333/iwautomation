package id.co.juaracoding.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ForgotPasswordPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By emailInput = By.cssSelector("[data-testid='forgot-password-email-input']");
    private final By captchaValueHint = By.cssSelector("[data-testid='forgot-password-captcha-value']");
    private final By captchaInput = By.cssSelector("[data-testid='forgot-password-captcha-input']");
    private final By submitButton = By.cssSelector("[data-testid='forgot-password-submit-button']");
    private final By refreshCaptchaButton = By.cssSelector("[data-testid='forgot-password-captcha-refresh-button']");
    private final By magicLink = By.cssSelector("[data-testid='check-email-magic-link']");
    private final By checkEmailMessage = By.cssSelector("[data-testid='check-email-message']");
    private final By errorToastIcon = By.cssSelector("[data-testid='toast-icon-error']");

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void fillEmail(String email) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        input.clear();
        input.sendKeys(email);
    }

    public String readCaptchaValue() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(captchaValueHint)).getText().trim();
    }

    public void fillCaptcha(String captcha) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(captchaInput));
        input.clear();
        input.sendKeys(captcha);
    }

    public void clickSubmit() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }

    public void submitForgotPassword(String email) throws InterruptedException {
        fillEmail(email);
        fillCaptcha(readCaptchaValue());
        Thread.sleep(1000);
        clickSubmit();
    }

    public void submitForgotPasswordWithCaptcha(String email, String captcha) throws InterruptedException {
        fillEmail(email);
        fillCaptcha(captcha);
        Thread.sleep(1000);
        clickSubmit();
    }

    public boolean isMagicLinkDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(magicLink)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getCheckEmailMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(checkEmailMessage)).getText();
    }

    public boolean isErrorToastShown() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(errorToastIcon)) != null;
        } catch (Exception e) {
            return false;
        }
    }
}
