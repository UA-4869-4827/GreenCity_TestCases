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

    @FindBy(css = "div.list-gallery")
    private List<WebElement> newsCards;

    @FindBy(id = "create-button")
    private WebElement createNewsButton;

    @FindBy(xpath = "//span[@aria-label='table view']/parent::*")
    private WebElement viewModeRoot;

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

    public NewsCardComponent getNewsCard(int index) {
        return new NewsCardComponent(driver, getVisibleItem(newsCards, index));
    }

    private WebElement locateSearchInput() {
        By locator = By.xpath("//input[@placeholder="
                + xpathLiteral(UiMessage.NEWS_SEARCH_PLACEHOLDER.text()) + "]");
        waitUntilElementPresent(locator);
        return driver.findElement(locator);
    }
}
