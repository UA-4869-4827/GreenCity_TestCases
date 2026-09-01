package com.greencity.ui.modal;

import org.openqa.selenium.WebDriver;

public class AddPlaceModal extends BaseModal<AddPlaceModal> {
    public AddPlaceModal(WebDriver driver) {
        super(driver);
    }

    @Override
    protected AddPlaceModal self() {
        return this;
    }
}
