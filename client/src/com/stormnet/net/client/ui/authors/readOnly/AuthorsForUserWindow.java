package com.stormnet.net.client.ui.authors.readOnly;

import com.stormnet.net.client.ui.common.BaseWindow;
import javafx.scene.Scene;

import java.io.IOException;

public class AuthorsForUserWindow extends BaseWindow {

    public AuthorsForUserWindow() throws IOException {
        super("authors-for-user-view.fxml");

        setTitle("All Authors");
        setScene(new Scene(getRootUI(), 1100, 700));
    }
}
