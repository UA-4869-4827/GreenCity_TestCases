package com.greencity.ui.smoke;

import com.greencity.ui.component.header.HeaderComponent;
import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.modal.SignUpModal;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.page.homepage.HomePage;
import com.greencity.ui.testrunners.BaseTestRunner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;

import java.util.List;

public class GuestHeaderTests extends BaseTestRunner {
    @Test
    @DisplayName("TC-P0-HDR-01 – Guest header exposes auth and language controls")
    void testGuestHeaderExposesAuthAndLanguageControls() {
        HeaderComponent header = homePage.getHeader();

        Assertions.assertTrue(header.isLogoDisplayed(), "GREENCITY logo is not visible");
        Assertions.assertTrue(header.isSignInDisplayed(), "Sign In link is not visible");
        Assertions.assertTrue(header.isSignUpDisplayed(), "Sign Up link is not visible");
        Assertions.assertTrue(header.isLanguageSwitcherDisplayed(), "Language switcher is not visible");

        header.openLanguageSwitcher();
        List<String> languages = header.getAvailableLanguagesText();

        Assertions.assertTrue(languages.contains("En"),
                "Language switcher dropdown doesn't contain 'En'");
        Assertions.assertTrue(languages.contains("Uk"),
                "Language switcher dropdown doesn't contain 'Uk'");
        Assertions.assertFalse(languages.contains("Ru"),
                "Language switcher contains forbidden 'Ru' locale!");

        header.selectLanguage(HeaderComponent.Language.ENGLISH);

        SignInModal signInModal = header.clickSignIn();

        Assertions.assertEquals(
                "Welcome back!",
                signInModal.getModalTitleText(),
                "Incorrect Sign In modal title"
        );

        homePage = signInModal.close(HomePage.class);

        SignUpModal signUpModal = header.clickSignUp();
        Assertions.assertEquals(
                "Hello!",
                signUpModal.getModalTitleText(),
                "Incorrect Sign Up modal title"
        );

        signUpModal.close(HomePage.class);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("window.scrollBy(0, 2000);");
        Assertions.assertTrue(
                header.getLogo().isDisplayed(),
                "Header hidden after scrolling down"
        );

        js.executeScript("window.scrollTo(0, 0);");
        Assertions.assertTrue(
                header.getLogo().isDisplayed(),
                "Header hidden after scrolling back up"
        );
    }

    /*@Test
    @DisplayName("TC-P0-HDR-02 – Language switch updates chrome En – Uk")
    void testLanguageSwitchUpdatesChromeEnToUk() {
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        ecoNewsPage.getHeader().clickLanguageSwitcher();

        Assertions.assertEquals("Зареєструватися", ecoNewsPage.getHeader().getSignUpLink().getText().trim(),
                "Button 'Sign up' in the Header did not translate in Ukrainian");

        Assertions.assertEquals("Еко новини", ecoNewsPage.getPageHeadingText(),
                "Heading Eco News page did not translate in Ukrainian");

        List<String> filterLabels = ecoNewsPage.getFilterLabels();
        Assertions.assertTrue(filterLabels.contains("Новини"), "The filter 'Новини' does not exist");
        Assertions.assertTrue(filterLabels.contains("Події"), "The filter 'Події' does not exist");
        Assertions.assertTrue(filterLabels.contains("Освіта"), "The filter 'Освіта' does not exist");

        Assertions.assertFalse(ecoNewsPage.getPageHeadingText().contains("user.warning.button"),
                "External raw localization key in the header!");

        ecoNewsPage.getHeader().clickLanguageSwitcher();

        Assertions.assertEquals("Sign up", ecoNewsPage.getHeader().getSignUpLink().getText().trim(),
                "Button 'Sign up' did not translate in English");
        Assertions.assertEquals("Eco news", ecoNewsPage.getPageHeadingText(),
                "The page title has not returned to English.");
    }*/

}
