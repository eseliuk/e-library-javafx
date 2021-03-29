package com.stormnet.net.client.ui.description;

import com.stormnet.net.client.ui.common.BaseWindow;
import javafx.scene.Scene;
import javafx.stage.Modality;

import java.io.IOException;

public class FullDescriptionWindow extends BaseWindow {

    public FullDescriptionWindow() throws IOException {
        super("full-description-view.fxml");

        setTitle("Full Description");
        initModality(Modality.APPLICATION_MODAL);
        setScene(new Scene(getRootUI(), 800, 700));
    }
}
