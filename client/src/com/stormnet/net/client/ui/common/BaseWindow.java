package com.stormnet.net.client.ui.common;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class BaseWindow extends Stage {

    private FXMLLoader fxmlLoader;
    private Parent rootUI;

    public BaseWindow(String fxmlPath) throws IOException {
        this.fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
        this.rootUI = fxmlLoader.load();
    }

    public Object getController() {
        return fxmlLoader.getController();
    }

    protected Parent getRootUI() {
        return rootUI;
    }
}
