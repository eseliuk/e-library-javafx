package com.stormnet.net.client.ui.auth;

import com.stormnet.net.client.exceptions.ServiceException;
import com.stormnet.net.client.services.SocketConnection;
import com.stormnet.net.client.ui.common.AppWindowsFactory;
import com.stormnet.net.data.users.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;
import java.io.*;
import java.util.function.Consumer;

public class LoginController {

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    public void signInBtnPressed(ActionEvent actionEvent) throws IOException {
        String userName = loginField.getText();
        String password = passwordField.getText();

        Account account = new Account(userName, password);

        SocketConnection connection = null;

        try {
            connection = new SocketConnection();
            JSONWriter jsonWriter = connection.getJsonWriter();

            jsonWriter.object();
                jsonWriter.key("request-header").object();
                    jsonWriter.key("command-name").value("auth-command");
                jsonWriter.endObject();

                jsonWriter.key("request-data").object();
                    jsonWriter.key("login").value(account.getLogin());
                    jsonWriter.key("password").value(account.getPassword());
                jsonWriter.endObject();
            jsonWriter.endObject();

            connection.flush();

            JSONTokener tokener = connection.getTokener();
            JSONObject responseJson = (JSONObject) tokener.nextValue();

            int responseCode = responseJson.getInt("response-code");

            connection.close();

            clearLoginForm();

            if(responseCode == 200) {
                AppWindowsFactory factory = AppWindowsFactory.getFactory();
                factory.hideMainWindow();
                factory.showWindow("booksForUserWindow");
            } else if(responseCode == 100) {
                AppWindowsFactory factory = AppWindowsFactory.getFactory();
                factory.hideMainWindow();
                factory.showWindow("allUsersWindow");
            } else  if(responseCode == 300) {
                AppWindowsFactory factory = AppWindowsFactory.getFactory();
                factory.hideMainWindow();
                factory.showWindow("allBooksWindow");
            } else if(responseCode == 401) {
                loginFailed("You entered incorrect data! Please try again!");
            } else {
                loginFailed("Server error! Please try later!");
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
    public void newUserRegistration(ActionEvent actionEvent) throws IOException {
        AppWindowsFactory factory = AppWindowsFactory.getFactory();
        factory.hideMainWindow();
        factory.showWindow("registrationWindow");

    }

    private  void loginFailed(String errorText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login failed!");

        alert.setContentText(errorText);

        alert.showAndWait().ifPresent(new Consumer<ButtonType>() {
            @Override
            public void accept(ButtonType buttonType) {
                if (buttonType == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            }
        });
    }

    public void clearLoginForm() {
        loginField.setText("");
        passwordField.setText("");
    }
}
