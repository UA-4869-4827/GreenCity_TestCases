package com.greencity.ui.page;

import com.greencity.ui.Base;
import com.greencity.ui.component.footer.FooterComponent;
import com.greencity.ui.component.header.HeaderComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public abstract class BasePage extends Base {
    protected HeaderComponent header;
    protected FooterComponent footer;

    @FindBy(css = "app-header")
    private WebElement headerRoot;
    @FindBy(css = "footer")
    private WebElement footerRoot;

    public BasePage(WebDriver driver) {
        super(driver);
        initPageElements();
        waitUntilElementVisible(headerRoot);
        header = new HeaderComponent(driver, headerRoot);
        footer = new FooterComponent(driver, footerRoot);
    }

    public HeaderComponent getHeader() {
        return header;
    }

    public FooterComponent getFooter() {
        return footer;
    }
}
