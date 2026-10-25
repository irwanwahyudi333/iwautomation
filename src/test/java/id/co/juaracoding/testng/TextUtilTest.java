package id.co.juaracoding.testng;

import id.co.juaracoding.util.TextUtil;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TextUtilTest {

    @BeforeClass
    public void setUpClass() {
        System.out.println("=== Memulai pengujian TextUtilTest ===");
    }

    @BeforeMethod
    public void setUpMethod() {
        System.out.println("Menjalankan test case...");
    }

    @AfterClass
    public void tearDownClass() {
        System.out.println("=== Seluruh pengujian TextUtilTest selesai ===");
    }

    // ==========================================
    // Skenario Pengujian isPalindrome
    // ==========================================

    @Test
    public void is_palindrome_returns_true_for_valid_palindrome() {
        boolean actual = TextUtil.isPalindrome("racecar");
        Assert.assertTrue(actual, "kata 'racecar' seharusnya terdeteksi sebagai palindrome");
    }

    @Test
    public void is_palindrome_returns_false_for_non_palindrome() {
        boolean actual = TextUtil.isPalindrome("juaracoding");
        Assert.assertFalse(actual, "kata 'juaracoding' seharusnya tidak terdeteksi sebagai palindrome");
    }

    @Test
    public void is_palindrome_returns_false_for_null_input() {
        boolean actual = TextUtil.isPalindrome(null);
        Assert.assertFalse(actual, "input null pada isPalindrome seharusnya mengembalikan false");
    }

    // ==========================================
    // Skenario Pengujian countVowels
    // ==========================================

    @Test
    public void count_vowels_returns_correct_count_for_normal_text() {
        int actual = TextUtil.countVowels("automation");
        int expected = 6; // a, u, o, a, i, o -> total 6
        Assert.assertEquals(actual, expected, "jumlah vokal pada kata 'automation' seharusnya 6");
    }

    @Test
    public void count_vowels_returns_zero_for_text_without_vowels() {
        int actual = TextUtil.countVowels("rhythm");
        int expected = 0;
        Assert.assertEquals(actual, expected, "kata tanpa huruf vokal ('rhythm') seharusnya menghasilkan 0");
    }

    @Test
    public void count_vowels_returns_zero_for_null_input() {
        int actual = TextUtil.countVowels(null);
        int expected = 0;
        Assert.assertEquals(actual, expected, "input null pada countVowels seharusnya menghasilkan 0");
    }
}
