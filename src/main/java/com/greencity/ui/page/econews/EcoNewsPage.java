package com.greencity.ui.page.EcoNews;

import com.greencity.ui.component.ViewModeToggleComponent;
import com.greencity.ui.page.BasePage;
import com.greencity.ui.page.econews.NewsDetailsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EcoNewsPage extends BasePage {

    private By newsFilter = By.xpath("//button[.//span[normalize-space()='News']]");
    private By eventsFilter = By.xpath("//button[.//span[normalize-space()='Events']]"); 
    private By educationFilter = By.xpath("//button[.//span[normalize-space()='Education']]");
    private By initiativesFilter = By.xpath("//button[.//span[normalize-space()='Initiatives']]"); 
    private By adsFilter = By.xpath("//button[.//span[normalize-space()='Ads']]"); 

    private By searchButton = By.cssSelector("span.search-img");
    private By searchInput = By.cssSelector("input[placeholder='Search']");
    private By clearSearchButton = By.cssSelector("img[alt='cancel search']");

    private By savedNewsButton = By.cssSelector("span.bookmark-img");
    private By calendarButton = By.cssSelector("div.container-img"); 

    private By newsCardList = By.cssSelectorh("div.list-wrapper"); 


    private ViewModeToggleComponent viewModeToggle;

    public EcoNewsPage(WebDriver driver) {
        super(driver);
        this.viewModeToggle = new ViewModeToggleComponent(driver);
    }

    
    public EcoNewsPage filterByNews() {
        click(newsFilter);
        return this;
    }

    public EcoNewsPage filterByEvents() {
        click(eventsFilter);
        return this;
    }

    public EcoNewsPage filterByEducation() {
        click(educationFilter);
        return this;
    }

    public EcoNewsPage filterByInitiatives() {
        click(initiativesFilter);
        return this;
    }

    public EcoNewsPage filterByAds() {
        click(adsFilter);
        return this;
    }


 public EcoNewsPage openSearch() {
    click(searchButton);
    return this;
}

public EcoNewsPage searchNews(String text) {
    type(searchInput, text);
    return this;
}

public EcoNewsPage clickClearSearch() {
    click(clearSearchButton);
    return this;
}

    public SignInModal openSavedNews() {
        click(savedNewsButton);
        return new SignInModal(driver);
    }



      public NewsDetailsPage openNewsByIndex(int index) {
        driver.findElements(newsCardList)
                .get(index)
                .click();
        return new NewsDetailsPage(driver);
    }
}
