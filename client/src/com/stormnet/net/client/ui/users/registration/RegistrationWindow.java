package com.stormnet.net.client.ui.users.registration;

import com.stormnet.net.client.ui.common.BaseWindow;
import javafx.scene.Scene;
import java.io.IOException;

public class RegistrationWindow extends BaseWindow {

    public RegistrationWindow() throws IOException {
        super("registration-view.fxml");

        setTitle("Registration");
        setScene(new Scene(getRootUI(), 500, 400));

    }
}
