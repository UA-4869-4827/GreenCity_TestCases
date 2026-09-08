package com.greencity.ui.component;

import com.greencity.ui.Base;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class BaseComponent extends Base {

    // Annotated with a no-match selector so PageFactory does not try to
    // locate it by field name ("rootElement") via the nested element factory.
    @Getter
    @FindBy(xpath = "self::*")
    protected WebElement rootElement;

    public BaseComponent(WebDriver driver, WebElement rootElement) {
        super(driver);
        this.rootElement = rootElement;
        initNestedElements(rootElement);
    }
}
