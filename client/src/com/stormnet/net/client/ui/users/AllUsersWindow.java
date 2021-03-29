package com.stormnet.net.client.ui.users;

import com.stormnet.net.client.ui.common.BaseWindow;
import javafx.scene.Scene;
import java.io.IOException;

public class AllUsersWindow extends BaseWindow {

    public AllUsersWindow() throws IOException {
        super("all-users-view.fxml");

        setTitle("All Users");
        setScene(new Scene(getRootUI(), 1200, 700));
  }

}
