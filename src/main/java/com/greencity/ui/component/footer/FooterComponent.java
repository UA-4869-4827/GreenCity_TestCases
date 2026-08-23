package com.greencity.ui.component.footer;

import com.greencity.ui.component.BaseComponent;
import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.page.EcoNews.EcoNewsPage;
import com.greencity.ui.page.aboutus.AboutUsPage;
import com.greencity.ui.page.events.EventsPage;
import com.greencity.ui.page.places.PlacesPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FooterComponent extends BaseComponent {

    private By logo = By.cssSelector("footer img.logo");

    private By ecoNewsLink = By.xpath("//footer//a[normalize-space()='Eco news']");
    private By eventsLink = By.xpath("//footer//a[normalize-space()='Events']");
    private By placesLink = By.xpath("//footer//a[normalize-space()='Places']");
    private By aboutUsLink = By.xpath("//footer//a[normalize-space()='About Us']");
    private By mySpaceLink = By.xpath("//footer//a[normalize-space()='My Space']");
    private By ubsCourierLink = By.xpath("//footer//a[normalize-space()='UBS Courier']");

    private By followUsLabel = By.cssSelector("footer li.footer_follow-us span");
    private By socialNetworkLinks = By.cssSelector("footer a.footer_social-link");
    private By copyrightLabel = By.cssSelector("footer #copyright-label");

    public FooterComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public EcoNewsPage openEcoNews() {
        click(ecoNewsLink);
        return new EcoNewsPage(driver);
    }

    public EventsPage openEvents() {
        click(eventsLink);
        return new EventsPage(driver);
    }

    public PlacesPage openPlaces() {
        click(placesLink);
        return new PlacesPage(driver);
    }

    public AboutUsPage openAboutUs() {
        click(aboutUsLink);
        return new AboutUsPage(driver);
    }

    public SignInModal openMySpace() {
        click(mySpaceLink);
        return new SignInModal(driver);
    }

    public void openSocialNetwork(String name) {
        click(By.xpath(
                "//footer//a[contains(@class,'footer_social-link')][.//img[contains(@alt,'" + name + "')]]"));
    }
}
