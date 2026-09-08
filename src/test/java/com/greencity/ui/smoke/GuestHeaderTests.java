package com.greencity.ui.smoke;

import com.greencity.ui.component.footer.FooterComponent;
import com.greencity.ui.component.header.HeaderComponent;
import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.modal.SignUpModal;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.page.homepage.HomePage;
import com.greencity.ui.testrunners.BaseTestRunner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

        header.scrollBy(0, 2000);

        Assertions.assertTrue(
                header.getLogo().isDisplayed(),
                "Header hidden after scrolling down"
        );

        header.scrollBy(0, -2000);

        Assertions.assertTrue(
                header.getLogo().isDisplayed(),
                "Header hidden after scrolling back up"
        );
    }

    @Test
    @DisplayName("TC-P0-HDR-02 – Language switch updates header En – Uk")
    void testLanguageSwitchUpdatesChromeEnToUk() throws InterruptedException {
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();
        HeaderComponent header = ecoNewsPage.getHeader();
        FooterComponent footer = ecoNewsPage.getFooter();

        header.selectLanguage(HeaderComponent.Language.UKRAINIAN);

        Assertions.assertFalse(
                header.containsRawI18nKey(),
                "Header contains raw i18n key"
        );

        Assertions.assertEquals(
                "Зареєструватися",
                header.getSignUpText()
        );

        Assertions.assertEquals(
                "Еко новини",
                ecoNewsPage.getPageHeadingText()
        );

        List<String> filterLabels = ecoNewsPage.getFilterLabels();

        Assertions.assertTrue(
                filterLabels.contains("Новини"),
                "Ukrainian filter 'Новини' is missing"
        );

        Assertions.assertTrue(
                filterLabels.contains("Події"),
                "Ukrainian filter 'Події' is missing"
        );

        Assertions.assertTrue(
                filterLabels.contains("Освіта"),
                "Ukrainian filter 'Освіта' is missing"
        );

        Assertions.assertEquals(
                "Еко новини",
                footer.getEcoNewsLinkText(),
                "Footer Eco news link did not translate to Ukrainian"
        );

        header.selectLanguage(HeaderComponent.Language.ENGLISH);

        ecoNewsPage.waitForHeading("Eco news");

        Assertions.assertEquals(
                "Sign up",
                header.getSignUpText(),
                "Sign up text did not translate back to English"
        );

        Assertions.assertEquals(
                "Eco news",
                ecoNewsPage.getPageHeadingText(),
                "Eco News heading did not translate back to English"
        );

        Assertions.assertEquals(
                "Eco news",
                footer.getEcoNewsLinkText(),
                "Footer Eco news link did not translate back to English"
        );
    }

}
