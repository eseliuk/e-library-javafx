package com.stormnet.net.client.ui.users.create;

import com.stormnet.net.client.ui.common.BaseWindow;
import javafx.scene.Scene;
import javafx.stage.Modality;
import java.io.IOException;

public class CreateUserWindow extends BaseWindow {

    public CreateUserWindow() throws IOException {
        super("create-user-view.fxml");

        setTitle("Create User");
        initModality(Modality.APPLICATION_MODAL);
        setScene(new Scene(getRootUI(), 500, 400));
    }
}
