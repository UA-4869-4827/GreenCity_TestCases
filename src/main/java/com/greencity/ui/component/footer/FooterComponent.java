package com.greencity.ui.component.footer;

import com.greencity.ui.component.BaseComponent;
import com.greencity.ui.page.aboutus.AboutUsPage;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.page.events.EventsPage;
import com.greencity.ui.page.places.PlacesPage;
import com.greencity.ui.page.profile.ProfilePage;
import com.greencity.ui.page.ubscourier.UbsCourierPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FooterComponent extends BaseComponent {

    @FindBy(css = "a[href*='/greenCity/about']")
    private WebElement aboutUsLink;

    @FindBy(css = "a[href*='/greenCity/news']")
    private WebElement ecoNewsLink;

    @FindBy(css = "a[href*='/greenCity/places']")
    private WebElement placesLink;

    @FindBy(css = "a[href*='/greenCity/events']")
    private WebElement eventsLink;

    @FindBy(css = "a[href*='/greenCity/profile']")
    private WebElement mySpaceLink;

    @FindBy(css = "a[href*='ubs']")
    private WebElement ubsCourierLink;

    public FooterComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public boolean isDisplayed() {
        return isElementDisplayed(rootElement);
    }

    public AboutUsPage openAboutUs() {
        clickElement(aboutUsLink);
        return new AboutUsPage(driver);
    }

    public EcoNewsPage openEcoNews() {
        clickElement(ecoNewsLink);
        return new EcoNewsPage(driver);
    }

    public PlacesPage openPlaces() {
        clickElement(placesLink);
        return new PlacesPage(driver);
    }

    public EventsPage openEvents() {
        clickElement(eventsLink);
        return new EventsPage(driver);
    }

    public ProfilePage openMySpace() {
        clickElement(mySpaceLink);
        return new ProfilePage(driver);
    }

    public UbsCourierPage openUbsCourier() {
        clickElement(ubsCourierLink);
        return new UbsCourierPage(driver);
    }
}
