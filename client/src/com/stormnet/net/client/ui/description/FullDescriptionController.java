package com.stormnet.net.client.ui.description;

import com.stormnet.net.client.ui.common.AppWindowsFactory;
import com.stormnet.net.data.description.Description;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FullDescriptionController {

    public TextArea fullDescriptionArea;

    public TextField idField;


    public void closeBtnPressed (ActionEvent actionEvent) {
        AppWindowsFactory factory = AppWindowsFactory.getFactory();
        factory.hideWindow("fullDescriptionWindow");
    }

    public void fillDescForm(Description description) {
        Long descriptionId = description.getId();
        if (descriptionId == null) {
            idField.setText("");
        } else {
            fullDescriptionArea.setText(description.getId().toString());
        }

        fullDescriptionArea.setText(description.getFullDescription());

    }

}
