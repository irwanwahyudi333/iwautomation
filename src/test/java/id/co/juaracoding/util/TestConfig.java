package id.co.juaracoding.util;

/**
 * Konfigurasi pengujian Selenium.
 * Seluruh nilai konfigurasi (URL, kredensial, license key) dipusatkan di sini
 * sesuai spesifikasi praktikum.
 */
public class TestConfig {

    public static final String BASE_URL = "http://localhost:8080";
    public static final String LICENSE_KEY = "d461265dd7323fef9755bb3257275d67";

    // Kredensial akun valid (seed README §4 / §5)
    public static final String VALID_USERNAME = "admin1";
    public static final String VALID_PASSWORD = "Admin1@123";
    public static final String VALID_USER_FULL_NAME = "Admin Satu";

    // Akun customer seed (untuk uji Lupa Password)
    public static final String SEED_CUSTOMER_EMAIL = "customer1@simpleapps.test";
    public static final String SEED_CUSTOMER_USERNAME = "customer1";

    // Nilai untuk skenario negatif / gagal
    public static final String INVALID_PASSWORD = "PasswordSalah123!";
    public static final String UNREGISTERED_EMAIL = "tidak_terdaftar_test@example.com";
    public static final String INVALID_KTP_SHORT = "12345";
    public static final String INVALID_NPWP_SHORT = "12345";
}
