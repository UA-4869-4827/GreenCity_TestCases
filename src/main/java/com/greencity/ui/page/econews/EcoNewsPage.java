package com.greencity.ui.page.econews;

import com.greencity.ui.component.NewsCardComponent;
import com.greencity.ui.component.ViewModeToggleComponent;
import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.page.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import java.util.List;
import java.util.stream.Collectors;

public class EcoNewsPage extends BasePage {

<<<<<<< HEAD
    private static final String ECO_NEWS_HASH = "/#/greenCity/news";
=======
    private By newsFilter = By.xpath("//button[.//span[normalize-space()='News']]");
    private By eventsFilter = By.xpath("//button[.//span[normalize-space()='Events']]");
    private By educationFilter = By.xpath("//button[.//span[normalize-space()='Education']]");
    private By initiativesFilter = By.xpath("//button[.//span[normalize-space()='Initiatives']]");
    private By adsFilter = By.xpath("//button[.//span[normalize-space()='Ads']]");
>>>>>>> 412c350 (same fixes)

    @FindBy(xpath = "//button[.//span[normalize-space()='News']]")
    private WebElement newsFilter;

<<<<<<< HEAD
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
=======
    private By savedNewsButton = By.cssSelector("span.bookmark-img");
    private By calendarButton = By.cssSelector("div.container-img");

    private By newsCardList = By.cssSelector("div.list-wrapper");

    private By createNewsButton = By.id("create-button");
>>>>>>> 412c350 (same fixes)

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
<<<<<<< HEAD
        clickElement(searchButton);
=======
        click(searchButton);
>>>>>>> 412c350 (same fixes)
        return this;
    }

    public EcoNewsPage searchNews(String text) {
<<<<<<< HEAD
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
=======
        type(searchInput, text);
        return this;
    }

    public EcoNewsPage clickClearSearch() {
        click(clearSearchButton);
        return this;
    }

    public SignInModal openSavedNewsAsGuest() {
        click(savedNewsButton);
        return new SignInModal(driver);
    }

    public EcoNewsPage openSavedNews() {
        click(savedNewsButton);
        return this;
    }

    public NewsDetailsPage openNewsByIndex(int index) {
        driver.findElements(newsCardList)
                .get(index)
                .click();
        return new NewsDetailsPage(driver);
>>>>>>> 412c350 (same fixes)
    }

    public CreateNewsPage clickCreateNews() {
        click(createNewsButton);
        return new CreateNewsPage(driver);
    }

    public List<NewsCardComponent> getNewsCards() {
        return driver.findElements(newsCardList).stream()
                .map(el -> new NewsCardComponent(driver, el))
                .collect(Collectors.toList());
    }
}
