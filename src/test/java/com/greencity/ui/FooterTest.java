package com.greencity.ui;

import com.greencity.ui.component.footer.FooterComponent;
import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.page.homepage.HomePage;
import com.greencity.ui.testrunners.BaseTestRunner;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("smoke")
public class FooterTest extends BaseTestRunner {

    @Test
    void footerShowsAllSectionLinksAndCopyright() {
        FooterComponent footer = homePage.getFooter();

        assertAll("Footer elements",
                () -> assertTrue(footer.isLogoDisplayed(), "Footer logo is missing"),
                () -> assertTrue(footer.isEcoNewsDisplayed(), "Eco news link is missing"),
                () -> assertTrue(footer.isEventsDisplayed(), "Events link is missing"),
                () -> assertTrue(footer.isPlacesDisplayed(), "Places link is missing"),
                () -> assertTrue(footer.isAboutUsDisplayed(), "About Us link is missing"),
                () -> assertTrue(footer.isMySpaceDisplayed(), "My Space link is missing"),
                () -> assertTrue(footer.isUbsCourierDisplayed(), "UBS Courier link is missing"),
                () -> assertTrue(footer.isFollowUsDisplayed(), "'Follow us' label is missing"));

        assertAll("Footer link hashes",
                () -> assertEquals("#/greenCity/news", footer.getEcoNewsHref(),
                        "Eco news href is wrong"),
                () -> assertEquals("#/greenCity/events", footer.getEventsHref(),
                        "Events href is wrong"),
                () -> assertEquals("#/greenCity/places", footer.getPlacesHref(),
                        "Places href is wrong"),
                () -> assertEquals("#/greenCity/about", footer.getAboutUsHref(),
                        "About Us href is wrong"),
                () -> assertTrue(footer.getMySpaceHref().contains("#/greenCity/profile"),
                        "My Space href does not point to profile"),
                () -> assertEquals("#/ubs", footer.getUbsCourierHref(),
                        "UBS Courier href is wrong"),
                () -> assertEquals("#/greenCity", footer.getLogoHref(),
                        "Logo href is wrong"));

        String currentYear = String.valueOf(LocalDate.now().getYear());
        String copyright = footer.getCopyrightLabelText();

        assertAll("Copyright",
                () -> assertTrue(copyright.contains(currentYear),
                        "Copyright does not contain the current year: " + copyright),
                () -> assertTrue(copyright.contains("Green City"),
                        "Copyright does not contain 'Green City': " + copyright));


        SignInModal signInModal = footer.openMySpaceAsGuest();
        assertEquals("Welcome back!", signInModal.getModalTitleText(),
                "Sign in modal did not open from footer 'My Space'");

        HomePage home = signInModal.close(HomePage.class);
        FooterComponent footerAfterClose = home.getFooter();

        footerAfterClose.clickLogo();
        assertTrue(driver.getCurrentUrl().matches(".*/greenCity/?$"),
                "Footer logo did not open the home page");
    }

    @Test
    void footerSocialIconsPointToHash() {
        FooterComponent footer = homePage.getFooter();
        List<String> socialHrefs = footer.getSocialHrefs();

        assertEquals(5, socialHrefs.size(),
                "Footer should contain five social icons");

        assertAll("Social icon hrefs",
                socialHrefs.stream()
                        .map(href -> () -> assertEquals("#", href,
                                "Social icon href is not '#'")));
    }
}