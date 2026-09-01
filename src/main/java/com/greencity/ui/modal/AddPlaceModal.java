package com.greencity.ui.modal;

import org.openqa.selenium.WebDriver;

public class AddPlaceModal extends BaseModal{
    public AddPlaceModal(WebDriver driver) {
        super(driver);
    }

    @Override
    protected BaseModal self() {
        return null;
    }
}
