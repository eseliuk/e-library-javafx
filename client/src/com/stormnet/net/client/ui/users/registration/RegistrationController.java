package com.stormnet.net.client.ui.users.registration;
import com.stormnet.net.client.exceptions.ServiceException;
import com.stormnet.net.client.services.SocketConnection;
import com.stormnet.net.client.ui.common.AppWindowsFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;
import java.io.*;
import java.time.LocalDate;
import java.util.function.Consumer;

public class RegistrationController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField repeatPasswordField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private DatePicker dateOfBirthField;

    public void newUserRegistrationBtnPressed(ActionEvent actionEvent) throws IOException {
        String email = emailField.getText();
        String password = passwordField.getText();
        String repeatPassword = repeatPasswordField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        LocalDate dateOfBirth = dateOfBirthField.getValue();

        SocketConnection connection = null;

        try {
            connection = new SocketConnection();
            JSONWriter jsonWriter = connection.getJsonWriter();

            jsonWriter.object();
            jsonWriter.key("request-header").object();
            jsonWriter.key("command-name").value("registration-command");
            jsonWriter.endObject();

            jsonWriter.key("request-data").object();
            jsonWriter.key("email").value(email);
            jsonWriter.key("password").value(password);
            jsonWriter.key("repeatPassword").value(repeatPassword);
            jsonWriter.key("firstName").value(firstName);
            jsonWriter.key("lastName").value(lastName);
            jsonWriter.key("dateOfBirth").value(dateOfBirth);
            jsonWriter.endObject();
            jsonWriter.endObject();

            connection.flush();

            JSONTokener tokener = connection.getTokener();
            JSONObject responseJson = (JSONObject) tokener.nextValue();

            int responseCode = responseJson.getInt("response-code");

            connection.close();

            if (responseCode == 200) {
                AppWindowsFactory factory =  AppWindowsFactory.getFactory();
                factory.hideWindow("registrationWindow");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Completed");

                alert.setContentText("Registration completed successfully! Please sign in");

                alert.showAndWait().ifPresent(new Consumer<ButtonType>() {
                    @Override
                    public void accept(ButtonType buttonType) {
                        if (buttonType == ButtonType.OK) {
                            System.out.println("Pressed OK.");
                        }
                    }
                });
                clearUserRegisterForm();
                factory.showMainWindow();
            } else {
                AppWindowsFactory factory =  AppWindowsFactory.getFactory();
                factory.hideWindow("registrationWindow");

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Something wrong!");

                alert.setContentText("Password and repeat password must be the same! Please try again!");

                alert.showAndWait().ifPresent(new Consumer<ButtonType>() {
                    @Override
                    public void accept(ButtonType buttonType) {
                        if (buttonType == ButtonType.OK) {
                            System.out.println("Pressed OK.");
                        }
                    }
                });
                factory.showWindow("registrationWindow");
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public void cancelBtnPressed(ActionEvent actionEvent) throws IOException {
        AppWindowsFactory factory = AppWindowsFactory.getFactory();
        factory.hideWindow("registrationWindow");
        factory.showMainWindow();
    }

    public void clearUserRegisterForm() {
        emailField.setText("");
        passwordField.setText("");
        repeatPasswordField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        dateOfBirthField.setValue(null);
    }
}
