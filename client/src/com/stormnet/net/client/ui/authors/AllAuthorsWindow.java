package com.stormnet.net.client.ui.authors;

import com.stormnet.net.client.ui.common.BaseWindow;
import javafx.scene.Scene;

import java.io.IOException;

public class AllAuthorsWindow extends BaseWindow {

    public AllAuthorsWindow() throws IOException {
        super("all-authors-view.fxml");

        setTitle("All Authors");
        setScene(new Scene(getRootUI(), 1400, 700));
    }
}
