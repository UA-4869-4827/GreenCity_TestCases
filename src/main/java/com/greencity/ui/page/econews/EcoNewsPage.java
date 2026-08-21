package com.greencity.ui.page.econews;

import com.greencity.ui.component.NewsCardComponent;
import com.greencity.ui.component.ViewModeToggleComponent;
import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.page.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class EcoNewsPage extends BasePage {

    private static final String ECO_NEWS_HASH = "/#/greenCity/news";

    @FindBy(xpath = "//button[.//span[normalize-space()='News']]")
    private WebElement newsFilter;

    @FindBy(xpath = "//button[.//span[normalize-space()='Events']]")
    private WebElement eventsFilter;

    @FindBy(xpath = "//button[.//span[normalize-space()='Education']]")
    private WebElement educationFilter;

    @FindBy(xpath = "//button[.//span[normalize-space()='Initiatives']]")
    private WebElement initiativesFilter;

    @FindBy(xpath = "//button[.//span[normalize-space()='Ads']]")
    private WebElement adsFilter;

    @FindBy(css = "span.search-img")
    private WebElement searchButton;

    @FindBy(css = "input[placeholder='Search']")
    private WebElement searchInput;

    @FindBy(css = "img[alt='cancel search']")
    private WebElement clearSearchButton;

    @FindBy(css = "span.bookmark-img")
    private WebElement savedNewsButton;

    @FindBy(css = "div.list-gallery")
    private List<WebElement> newsCards;

    @FindBy(xpath = "//span[@aria-label='table view']/parent::*")
    private WebElement viewModeRoot;

    private ViewModeToggleComponent viewModeToggle;

    public EcoNewsPage(WebDriver driver) {
        super(driver);
        this.viewModeToggle = new ViewModeToggleComponent(driver, viewModeRoot);
    }

    public EcoNewsPage open() {
        String currentUrl = driver.getCurrentUrl();
        String origin = currentUrl.contains("#")
                ? currentUrl.substring(0, currentUrl.indexOf('#'))
                : currentUrl;
        origin = origin.replaceAll("/$", "");
        driver.get(origin + ECO_NEWS_HASH);
        waitForPageToLoad(10);
        return this;
    }

    public ViewModeToggleComponent getViewModeToggle() {
        return viewModeToggle;
    }

    public EcoNewsPage filterByNews() {
        clickElement(newsFilter);
        return this;
    }

    public EcoNewsPage filterByEvents() {
        clickElement(eventsFilter);
        return this;
    }

    public EcoNewsPage filterByEducation() {
        clickElement(educationFilter);
        return this;
    }

    public EcoNewsPage filterByInitiatives() {
        clickElement(initiativesFilter);
        return this;
    }

    public EcoNewsPage filterByAds() {
        clickElement(adsFilter);
        return this;
    }

    public EcoNewsPage openSearch() {
        clickElement(searchButton);
        return this;
    }

    public EcoNewsPage searchNews(String text) {
        typeText(searchInput, text);
        return this;
    }

    public EcoNewsPage clearSearch() {
        clickElement(clearSearchButton);
        return this;
    }

    public SignInModal openSavedNews() {
        clickElement(savedNewsButton);
        return new SignInModal(driver);
    }

    public NewsCardComponent getNewsCard(int index) {
        waitUntilAllElementsVisible(newsCards);
        return new NewsCardComponent(driver, newsCards.get(index));
    }

    public NewsDetailsPage openNewsByIndex(int index) {
        return getNewsCard(index).openNews();
    }
}
