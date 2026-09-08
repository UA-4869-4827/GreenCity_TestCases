package com.greencity.ui.component.footer;

import com.greencity.ui.component.BaseComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FooterComponent extends BaseComponent {
    @FindBy(css = "footer a.footer_link-item[href='#/greenCity/news']")
    private WebElement ecoNewsLink;

    public FooterComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public String getEcoNewsLinkText() {
        return getElementText(ecoNewsLink);
    }
}
