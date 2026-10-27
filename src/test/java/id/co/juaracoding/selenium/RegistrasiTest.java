package id.co.juaracoding.selenium;

import id.co.juaracoding.selenium.pages.RegisterPage;
import id.co.juaracoding.util.TestConfig;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class RegistrasiTest extends BaseSeleniumTest {

    @Test
    public void should_redirect_to_check_email_when_registrasi_valid() throws InterruptedException {
        bukaHalaman("/register");

        long now = System.currentTimeMillis();
        String username = String.format("sel%07d", now % 10_000_000L);
        String password = "User1@123456";
        String email = username + "@example.com";
        String phoneNumber = String.format("0812%08d", now % 100_000_000L);
        String idCardNumber = String.format("%016d", now);
        String taxIdNumber = String.format("%016d", now + 1);

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.fillWajibFields(username, password, "Pengguna Uji Selenium", email,
                phoneNumber, "Jl. Pengujian Otomasi No. 42", idCardNumber, taxIdNumber);
        registerPage.pilihJenisKelamin("Pria");
        registerPage.pilihTanggalLahir2020Januari15();
        registerPage.isiCaptchaOtomatis();
        registerPage.submit();

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("/check-email"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/check-email"),
                "Registrasi sukses harus mengarahkan ke /check-email. URL aktual: " + driver.getCurrentUrl());
    }

    @Test
    public void should_reject_registration_when_ktp_not_16_digits() throws InterruptedException {
        bukaHalaman("/register");

        long now = System.currentTimeMillis();
        String username = String.format("inv%07d", now % 10_000_000L);
        String password = "User1@123456";
        String email = username + "@example.com";
        String phoneNumber = String.format("0812%08d", now % 100_000_000L);
        String invalidIdCardNumber = TestConfig.INVALID_KTP_SHORT; // Bukan 16 digit
        String taxIdNumber = String.format("%016d", now + 1);

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.fillWajibFields(username, password, "Uji KTP Tidak Valid", email,
                phoneNumber, "Jl. Pengujian Otomasi No. 42", invalidIdCardNumber, taxIdNumber);
        registerPage.pilihJenisKelamin("Pria");
        registerPage.pilihTanggalLahir2020Januari15();
        registerPage.isiCaptchaOtomatis();

        Thread.sleep(1000);

        Assert.assertTrue(registerPage.isKtpErrorDisplayed(),
                "Pesan kesalahan validasi KTP harus muncul ketika KTP bukan 16 digit");
        Assert.assertFalse(registerPage.isSubmitButtonEnabled(),
                "Tombol submit harus disable / form menolak submit jika KTP tidak valid");
        Assert.assertTrue(driver.getCurrentUrl().contains("/register"),
                "Form harus tetap di halaman /register dan tidak submit ke server");
    }
}
