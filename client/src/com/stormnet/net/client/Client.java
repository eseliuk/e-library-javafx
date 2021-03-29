package com.stormnet.net.client;

import com.stormnet.net.client.ui.common.AppWindowsFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ui/auth/login-view.fxml"));

        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 500, 300));

        AppWindowsFactory factory = AppWindowsFactory.getFactory();
        factory.setMainWindow(primaryStage);
        factory.showMainWindow();
    }
    public static void main(String[] args) {
        launch(args);
    }

}

