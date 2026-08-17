package com.greencity.ui.page;

import com.greencity.ui.Base;
import com.greencity.ui.component.footer.FooterComponent;
import com.greencity.ui.component.header.HeaderComponent;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;



public abstract class BasePage extends Base {
    @Getter
    protected HeaderComponent header;
    @Getter
    protected FooterComponent footer;

    @FindBy(xpath = "//app-header")
    private WebElement headerRoot;
    @FindBy(xpath = "//footer")
    private WebElement footerRoot;

    public BasePage(WebDriver driver) {
        super(driver);
        header = new HeaderComponent(driver, headerRoot);
        footer = new FooterComponent(driver, footerRoot);
    }




}