package com.greencity.ui.modal;

import com.greencity.ui.Base;
import com.greencity.ui.page.places.PlacesPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class AddPlaceModal extends Base {

    @FindBy(css = "div.form-container")
    private WebElement formContainer;

    @FindBy(css = "div.form-container select[formcontrolname='type']")
    private WebElement categorySelect;

    @FindBy(css = "div.form-container input[formcontrolname='name']")
    private WebElement nameInput;

    @FindBy(css = "div.form-container app-address-input input")
    private WebElement addressInput;

    @FindBy(xpath = "//div[contains(@class,'btn-wrapper')]//button[contains(@class,'secondary-global-button')]")
    private WebElement cancelButton;

    @FindBy(xpath = "//div[contains(@class,'btn-wrapper')]//button[contains(@class,'primary-global-button')]")
    private WebElement addButton;

    public AddPlaceModal(WebDriver driver) {
        super(driver);
        waitUntilElementVisible(formContainer);
    }

    public AddPlaceModal selectCategory(String category) {
        waitUntilElementVisible(categorySelect);
        new Select(categorySelect).selectByVisibleText(category);
        return this;
    }

    public AddPlaceModal enterName(String name) {
        typeText(nameInput, name);
        return this;
    }

    public AddPlaceModal enterAddress(String address) {
        typeText(addressInput, address);
        return this;
    }

    public PlacesPage cancel() {
        clickElement(cancelButton);
        waitUntilElementInvisible(formContainer);
        return new PlacesPage(driver);
    }

    public PlacesPage add() {
        clickElement(addButton);
        waitUntilElementInvisible(formContainer);
        return new PlacesPage(driver);
    }
}
