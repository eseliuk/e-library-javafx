package com.stormnet.net.client.ui.authors.create;

import com.stormnet.net.client.ui.common.BaseWindow;
import javafx.scene.Scene;
import javafx.stage.Modality;

import java.io.IOException;

public class CreateAuthorWindow extends BaseWindow {

    public CreateAuthorWindow() throws IOException {
        super("create-author-view.fxml");

        setTitle("Create Author");
        initModality(Modality.APPLICATION_MODAL);
        setScene(new Scene(getRootUI(), 800, 400));
    }
}
