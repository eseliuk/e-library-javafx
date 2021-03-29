package com.stormnet.net.client.ui.books.create;

import com.stormnet.net.client.ui.common.BaseWindow;
import javafx.scene.Scene;
import javafx.stage.Modality;

import java.io.IOException;

public class CreateBookWindow extends BaseWindow {

    public CreateBookWindow() throws IOException {
        super("create-book-view.fxml");

        setTitle("Create Book");
        initModality(Modality.APPLICATION_MODAL);
        setScene(new Scene(getRootUI(), 500, 400));
    }
}
