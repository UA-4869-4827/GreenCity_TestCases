package com.greencity.ui.page.econews;

import com.greencity.ui.component.NewsCardComponent;
import com.greencity.ui.component.ViewModeToggleComponent;
import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.page.BasePage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class EcoNewsPage extends BasePage {

    private static final String ECO_NEWS_HASH = "/#/greenCity/news";

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

    @FindBy(css = "span.search-img")
    private WebElement searchButton;

    @FindBy(css = "img[alt='cancel search']")
    private WebElement clearSearchButton;

    @FindBy(css = "span.bookmark-img")
    private WebElement savedNewsButton;

    @FindBy(css = "h1.main-header")
    private WebElement heading;

    @FindBy(css = "button.tag-button")
    private List<WebElement> filterChips;

    @FindBy(css = "main h2")
    private WebElement itemsFoundLabel;

    @FindBy(css = "div.list-gallery")
    private List<WebElement> newsCards;

    @FindBy(id = "create-button")
    private WebElement createNewsButton;

    @FindBy(xpath = "//span[@aria-label='table view']/parent::*")
    private WebElement viewModeRoot;

    private final ViewModeToggleComponent viewModeToggle;

    public EcoNewsPage(WebDriver driver) {
        super(driver);
        waitUntilUrlContains(ECO_NEWS_HASH);
        this.viewModeToggle = new ViewModeToggleComponent(driver, viewModeRoot);
    }

    public ViewModeToggleComponent getViewModeToggle() {
        return viewModeToggle;
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
        clickBy(By.xpath("//button[.//span[normalize-space()=" + xpathLiteral(tag.getText()) + "]]"));
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

    public EcoNewsPage openSearch() {
        clickElement(searchButton);
        return this;
    }

    public EcoNewsPage searchNews(String text) {
        typeText(locateSearchInput(), text);
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

    public CreateNewsPage createNews() {
        clickElement(createNewsButton);
        return new CreateNewsPage(driver);
    }

    public boolean isCreateNewsButtonDisplayed() {
        return isElementDisplayed(createNewsButton);
    }

    public boolean isOpened() {
        wait.until(driver -> isNewsListHash(getCurrentUrl()));
        return isNewsListHash(getCurrentUrl());
    }

    public String getHeadingText() {
        return getElementText(heading);
    }

    public boolean areFilterChipsDisplayed() {
        wait.until(driver -> filterChips.size() >= NewsTag.values().length);
        return filterChips.stream().allMatch(this::isElementDisplayed);
    }

    public boolean isItemsFoundCounterDisplayed() {
        wait.until(driver -> isElementDisplayed(itemsFoundLabel)
                && itemsFoundLabel.getText().contains(UiMessage.NEWS_ITEMS_FOUND.text()));
        return true;
    }

    public String getItemsFoundText() {
        isItemsFoundCounterDisplayed();
        return getElementText(itemsFoundLabel);
    }

    public boolean isAtLeastOneNewsCardDisplayed() {
        try {
            return isElementDisplayed(getVisibleItem(newsCards, 0));
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public NewsCardComponent getNewsCard(int index) {
        return new NewsCardComponent(driver, getVisibleItem(newsCards, index));
    }

    private WebElement locateSearchInput() {
        By locator = By.xpath("//input[@placeholder="
                + xpathLiteral(UiMessage.NEWS_SEARCH_PLACEHOLDER.text()) + "]");
        waitUntilElementPresent(locator);
        return driver.findElement(locator);
    }

    private static boolean isNewsListHash(String url) {
        int hashIndex = url.indexOf('#');
        if (hashIndex < 0) {
            return false;
        }
        String fragment = url.substring(hashIndex);
        return "#/greenCity/news".equals(fragment) || "#/greenCity/news/".equals(fragment);
    }
}
