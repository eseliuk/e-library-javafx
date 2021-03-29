package com.stormnet.net.client.ui.books;

import com.stormnet.net.client.ui.common.BaseWindow;
import javafx.scene.Scene;

import java.io.IOException;

public class AllBooksWindow extends BaseWindow {

    public AllBooksWindow() throws IOException {
        super("all-books-view.fxml");

        setTitle("All Books");
        setScene(new Scene(getRootUI(), 1200, 800));
    }
}
