package com.greencity.ui.page.econews;

import com.greencity.ui.component.NewsCardComponent;
import com.greencity.ui.component.ViewModeToggleComponent;
import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.page.BasePage;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class EcoNewsPage extends BasePage {

    private static final String ECO_NEWS_HASH = "/#/greenCity/news";
    private static final String CREATE_NEWS_HASH = "/#/greenCity/news/create-news";

    @Getter
    public enum NewsTag {
        NEWS(UiMessage.NEWS_TAG_NEWS),
        EVENTS(UiMessage.NEWS_TAG_EVENTS),
        EDUCATION(UiMessage.NEWS_TAG_EDUCATION),
        INITIATIVES(UiMessage.NEWS_TAG_INITIATIVES),
        ADS(UiMessage.NEWS_TAG_ADS);

        private final UiMessage message;

        NewsTag(UiMessage message) {
            this.message = message;
        }

        public String getText() {
            return message.text();
        }
    }

    @FindBy(css = "h1.main-header")
    private WebElement pageHeading;

    @FindBy(css = "div[aria-label='filter by items'] button.tag-button span.text")
    private List<WebElement> filterLabels;

    @FindBy(css = "span.search-img")
    private WebElement searchButton;

    @FindBy(css = "img[alt='cancel search']")
    private WebElement clearSearchButton;

    @FindBy(css = "span.bookmark-img")
    private WebElement savedNewsButton;

    @FindBy(css = "div.list-gallery")
    private List<WebElement> newsCards;

    @FindBy(css = "li.list-view-li-active")
    private List<WebElement> newsCardsListView;

    @FindBy(id = "create-button")
    private WebElement createNewsButton;

    @FindBy(xpath = "//span[@aria-label='table view']/parent::*")
    private WebElement viewModeRoot;

    @FindBy(css = "h1")
    private WebElement pageTitle;

    @FindBy(css = "app-remaining-count h2")
    private WebElement itemsFoundCounter;

    @Getter
    private final ViewModeToggleComponent viewModeToggle;

    public EcoNewsPage(WebDriver driver) {
        super(driver);
        this.viewModeToggle = new ViewModeToggleComponent(driver, viewModeRoot);
    }

    public EcoNewsPage open() {
        open(ECO_NEWS_HASH);
        return this;
    }

    public EcoNewsPage openCreateNewsRoute() {
        open(ECO_NEWS_HASH + "/create");
        return this;
    }

    public NewsDetailsPage openNewsById(long newsId) {
        open(ECO_NEWS_HASH + "/" + newsId);
        return new NewsDetailsPage(driver);
    }

    public EcoNewsPage filterBy(NewsTag tag) {
        clickBy(By.xpath(
                "//button[.//span[normalize-space()="
                        + xpathLiteral(tag.getText()) + "]]"));
        return this;
    }

    public EcoNewsPage filterByNews() {
        return filterBy(NewsTag.NEWS);
    }

    public EcoNewsPage filterByEvents() {
        return filterBy(NewsTag.EVENTS);
    }

    public EcoNewsPage filterByEducation() {
        return filterBy(NewsTag.EDUCATION);
    }

    public EcoNewsPage filterByInitiatives() {
        return filterBy(NewsTag.INITIATIVES);
    }

    public EcoNewsPage filterByAds() {
        return filterBy(NewsTag.ADS);
    }

    // public boolean isFilterDisplayed(NewsTag tag) {
    //     By filterLocator = By.xpath(
    //             "//button[.//span[normalize-space()="
    //                     + xpathLiteral(tag.getText()) + "]]");
    //     return isElementDisplayed(filterLocator);
    // }

    // public boolean areAllFiltersDisplayed() {
    //     return List.of(NewsTag.values()).stream()
    //             .allMatch(this::isFilterDisplayed);
    // }

    public EcoNewsPage openSearch() {
        clickElement(searchButton);
        return this;
    }

    public EcoNewsPage searchNews(String text) {
        WebElement input = locateSearchInput();
        waitUntilElementVisible(input);
        js.executeScript(
                "const input = arguments[0];"
                        + "input.focus();"
                        + "input.value = arguments[1];"
                        + "input.dispatchEvent(new Event('input', {bubbles: true}));",
                input,
                text);
        return this;
    }

    public EcoNewsPage clearSearch() {
        clickElement(clearSearchButton);
        return this;
    }

    public SignInModal openSavedNewsAsGuest() {
        clickElement(savedNewsButton);
        return new SignInModal(driver);
    }

    public EcoNewsPage openSavedNews() {
        clickElement(savedNewsButton);
        return this;
    }

    public NewsDetailsPage openNewsByIndex(int index) {
        return getNewsCard(index).openNews();
    }

    @Step("Open news by title {title}")
    public NewsDetailsPage openNewsByTitle(String title) {
        By cardLink = By.xpath(
                "//app-news-list-gallery-view//h3[normalize-space()=" + xpathLiteral(title) + "]");
        waitUntilElementPresent(cardLink);
        WebElement card = driver.findElement(cardLink);
        scrollToElementWithJs(card);
        clickElement(card);
        wait.until(d -> d.getCurrentUrl().matches(".*/greenCity/news/\\d+$"));
        waitForPageToLoad();
        return new NewsDetailsPage(driver);
    }

    public CreateNewsPage createNews() {
        clickElement(createNewsButton);
        return new CreateNewsPage(driver);
    }

    public boolean isCreateNewsButtonDisplayed() {
        return isElementDisplayed(createNewsButton);
    }

    public boolean isNewsListOpened() {
        return driver.getCurrentUrl().matches(".*/greenCity/news/?$");
    }

    public NewsCardComponent getNewsCard(int index) {
        return new NewsCardComponent(driver, getVisibleItem(newsCards, index));
    }

    public String getItemsFoundCount() {
        return getElementText(itemsFoundCounter);
    }

    public String waitForItemsFoundCountToChange(String previousCount) {
        By itemsFoundCountLocator = By.cssSelector("app-remaining-count h2");

        return wait.until(driver -> {
            String currentCount = driver.findElement(itemsFoundCountLocator)
                    .getText()
                    .trim();
            return !currentCount.equals(previousCount) ? currentCount : null;
        });
    }

    public List<NewsCardComponent> getVisibleNewsCards() {
        return newsCards.stream()
                .map(element -> new NewsCardComponent(driver, element))
                .toList();
    }

    public void waitUntilNewsCardsAreDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.list-gallery")));
    }

    public void waitUntilSearchResultIsDisplayed(String searchQuery) {
        By resultTitle = By.xpath(
                "//div[contains(@class,'list-gallery')]"
                        + "//h3[contains(normalize-space(), "
                        + xpathLiteral(searchQuery)
                        + ")]");

        waitUntilElementPresent(resultTitle);
    }

    private WebElement locateSearchInput() {
        By locator = By.xpath(
                "//input[@placeholder="
                        + xpathLiteral(
                                UiMessage.NEWS_SEARCH_PLACEHOLDER.text())
                        + "]");

        waitUntilElementPresent(locator);
        return driver.findElements(locator).stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .orElseGet(() -> driver.findElement(locator));
    }

    public List<String> getFilterLabels() {
        waitUntilAllElementsVisible(filterLabels);
        return filterLabels.stream()
                .map(this::getElementText)
                .toList();
    }

    public String getPageHeadingText() {
        return getElementText(pageHeading);
    }

    public boolean isPageHeadingDisplayed() {
        return isElementDisplayed(pageHeading);
    }

    public void waitForHeading(String expectedText) {
        wait.until(ExpectedConditions.textToBePresentInElement(pageHeading, expectedText));
    }

    public String getPageTitleText() {
        return getElementText(pageTitle);
    }

    public boolean isItemsFoundCounterDisplayed() {
        return isElementDisplayed(itemsFoundCounter);
    }

    public boolean isNewsCardDisplayed() {
        waitUntilAllElementsVisible(newsCards);
        return !newsCards.isEmpty();
    }

    public String getPageUrl() {
        return getCurrentUrl();
    }

    public boolean isGalleryViewSelected() {
        return viewModeToggle.isTableViewPressed();
    }

    public boolean isListViewSelected() {
        return viewModeToggle.isListViewPressed();
    }

    public int getNewsCardsCount() {
        wait.until(d -> !newsCards.isEmpty());
        return newsCards.size();
    }

    public int getNewsCardsCountInListView() {
        wait.until(d -> !newsCardsListView.isEmpty());
        return newsCardsListView.size();
    }
}
