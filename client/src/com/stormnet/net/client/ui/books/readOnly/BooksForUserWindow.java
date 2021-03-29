package com.stormnet.net.client.ui.books.readOnly;

import com.stormnet.net.client.ui.common.BaseWindow;
import javafx.scene.Scene;

import java.io.IOException;

public class BooksForUserWindow extends BaseWindow {

    public BooksForUserWindow() throws IOException {
        super("books-for-user-view.fxml");

        setTitle("All Books");
        setScene(new Scene(getRootUI(), 1200, 700));
    }
}
