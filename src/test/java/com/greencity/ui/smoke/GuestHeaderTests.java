package com.greencity.ui.smoke;

import com.greencity.ui.component.footer.FooterComponent;
import com.greencity.ui.component.header.HeaderComponent;
import com.greencity.ui.locale.UiLocale;
import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.modal.SignUpModal;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.page.homepage.HomePage;
import com.greencity.ui.testrunners.BaseTestRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GuestHeaderTests extends BaseTestRunner {

    @Test
    @DisplayName("TC-P0-HDR-01 - Guest header exposes auth and language controls")
    void testGuestHeaderExposesAuthAndLanguageControls() {
        HeaderComponent header = homePage.getHeader();

        assertTrue(header.isLogoDisplayed(), "GREENCITY logo is not visible");
        assertTrue(header.isSignInDisplayed(), "Sign In link is not visible");
        assertTrue(header.isSignUpDisplayed(), "Sign Up link is not visible");
        assertTrue(header.isLanguageSwitcherDisplayed(), "Language switcher is not visible");

        header.openLanguageSwitcher();
        List<String> languages = header.getAvailableLanguagesText();

        assertTrue(languages.contains(UiLocale.EN.getHeaderLabel()),
                "Language switcher dropdown doesn't contain 'En'");
        assertTrue(languages.contains(UiLocale.UK.getHeaderLabel()),
                "Language switcher dropdown doesn't contain 'Uk'");
        assertFalse(languages.contains("Ru"),
                "Language switcher contains forbidden 'Ru' locale!");

        header.switchLanguage(UiLocale.EN);

        SignInModal signInModal = header.clickSignIn();
        assertEquals("Welcome back!", signInModal.getModalTitleText(),
                "Incorrect Sign In modal title");

        homePage = signInModal.close(HomePage.class);
        header = homePage.getHeader();

        SignUpModal signUpModal = header.clickSignUp();
        assertEquals("Hello!", signUpModal.getModalTitleText(),
                "Incorrect Sign Up modal title");

        signUpModal.close(HomePage.class);
        header = homePage.getHeader();

        header.scrollBy(0, 2000);
        assertTrue(header.isLogoDisplayed(), "Header hidden after scrolling down");

        header.scrollBy(0, -2000);
        assertTrue(header.isLogoDisplayed(), "Header hidden after scrolling back up");
    }

    @Test
    @DisplayName("TC-P0-HDR-02 - Language switch updates header En - Uk")
    void testLanguageSwitchUpdatesChromeEnToUk() {
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();
        HeaderComponent header = ecoNewsPage.getHeader();
        FooterComponent footer = ecoNewsPage.getFooter();

        header.switchLanguage(UiLocale.UK);

        assertFalse(header.containsRawI18nKey(), "Header contains raw i18n key");
        assertEquals(UiMessage.SIGN_UP.text(), header.getSignUpText());
        assertEquals(UiMessage.NEWS_PAGE_HEADER.text(), ecoNewsPage.getPageHeadingText());

        List<String> filterLabels = ecoNewsPage.getFilterLabels();
        assertTrue(filterLabels.contains(UiMessage.NEWS_TAG_NEWS.text()),
                "Ukrainian filter for news is missing");
        assertTrue(filterLabels.contains(UiMessage.NEWS_TAG_EVENTS.text()),
                "Ukrainian filter for events is missing");
        assertTrue(filterLabels.contains(UiMessage.NEWS_TAG_EDUCATION.text()),
                "Ukrainian filter for education is missing");

        assertEquals(UiMessage.NEWS_PAGE_HEADER.text(), footer.getEcoNewsLinkText(),
                "Footer Eco news link did not translate to Ukrainian");

        header.switchLanguage(UiLocale.EN);
        ecoNewsPage.waitForHeading(UiMessage.NEWS_PAGE_HEADER.text());

        assertEquals(UiMessage.SIGN_UP.text(), header.getSignUpText(),
                "Sign up text did not translate back to English");
        assertEquals(UiMessage.NEWS_PAGE_HEADER.text(), ecoNewsPage.getPageHeadingText(),
                "Eco News heading did not translate back to English");
        assertEquals(UiMessage.NEWS_PAGE_HEADER.text(), footer.getEcoNewsLinkText(),
                "Footer Eco news link did not translate back to English");
    }
}
