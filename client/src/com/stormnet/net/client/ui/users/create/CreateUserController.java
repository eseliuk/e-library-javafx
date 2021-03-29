package com.stormnet.net.client.ui.users.create;

import com.stormnet.net.client.maintenance.UserService;
import com.stormnet.net.client.maintenance.impl.UserServiceImpl;
import com.stormnet.net.client.ui.common.AppWindowsFactory;
import com.stormnet.net.client.ui.users.AllUsersController;
import com.stormnet.net.data.users.User;
import com.stormnet.net.utils.date.DateUtils;
import com.stormnet.net.utils.numbers.NumbersUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


public class CreateUserController {


    @FXML
    private TextField idField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private DatePicker dateOfBirthField;

    public void saveUserBtnPressed(ActionEvent actionEvent) throws IOException {

        Long userId = NumbersUtils.parseLong(idField.getText());
        String email = emailField.getText();
        String password = passwordField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        LocalDate localDateOfBirth = dateOfBirthField.getValue();
        Date dateOfBirth = DateUtils.localDateToDate(localDateOfBirth);

        User user = new User(email, password, firstName, lastName, dateOfBirth);
        user.setId(userId);

        UserService userService = new UserServiceImpl();
        userService.saveUser(user);

        AppWindowsFactory factory =  AppWindowsFactory.getFactory();

        Object object = factory.getWindowController("allUsersWindow");
        AllUsersController allUsersController = (AllUsersController) object;
        allUsersController.reloadDataList();
        factory.hideWindow("createUserWindow");

    }
    public void cancelBtnPressed (ActionEvent actionEvent)  {
        AppWindowsFactory factory = AppWindowsFactory.getFactory();
        factory.hideWindow("createUserWindow");

    }

    public void fillUserForm(User user) {
        Long userId = user.getId();
        if(userId == null) {
            idField.setText("");
        } else {
            idField.setText(user.getId().toString());
        }
        emailField.setText(user.getEmail());
        passwordField.setText(user.getPassword());
        firstNameField.setText(user.getFirstName());
        lastNameField.setText(user.getLastName());

        Date dateOfBirth = user.getDateOfBirth();
        LocalDate localDate = dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        dateOfBirthField.setValue(localDate);
    }

    public void clearUserForm() {
        idField.setText("");
        emailField.setText("");
        passwordField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        dateOfBirthField.setValue(null);
    }

}
