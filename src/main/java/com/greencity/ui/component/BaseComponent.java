package com.greencity.ui.component;

import com.greencity.ui.Base;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class BaseComponent extends Base {

    @Getter
    protected WebElement rootElement;

    public BaseComponent(WebDriver driver, WebElement rootElement) {
        super(driver);
        initNestedElements(rootElement);
        // Keep the real root after PageFactory so it is not replaced by a name=rootElement proxy.
        this.rootElement = rootElement;
    }
}
