package com.stormnet.net.client.ui.users;

import com.stormnet.net.client.exceptions.ServiceException;
import com.stormnet.net.client.maintenance.UserService;
import com.stormnet.net.client.maintenance.impl.UserServiceImpl;
import com.stormnet.net.client.ui.common.AppWindowsFactory;
import com.stormnet.net.client.ui.users.create.CreateUserController;
import com.stormnet.net.data.users.User;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AllUsersController implements Initializable {

    private ObservableList<User> allUsersObserv;


    @FXML
    private TableView<User> allUsersTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn<User, GridPane> actionColumn = new TableColumn<>("Actions");
        actionColumn.setMinWidth(150);
        actionColumn.setCellValueFactory(new ActionsCellFactory());

        try {
        allUsersTable.getColumns().add(actionColumn);
        allUsersObserv = FXCollections.observableArrayList();
        allUsersTable.setItems(allUsersObserv);

        reloadDataList();

        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public void addUserBtnPressed(ActionEvent actionEvent) {

        AppWindowsFactory factory = AppWindowsFactory.getFactory();
        CreateUserController controller = (CreateUserController) factory.getWindowController("createUserWindow");
        controller.clearUserForm();
        factory.showWindow("createUserWindow");
    }

    public void logoutBtnPressed (ActionEvent actionEvent) {
        AppWindowsFactory factory = AppWindowsFactory.getFactory();
        factory.hideWindow("allUsersWindow");
        factory.showMainWindow();

    }


    public void reloadDataList()  {
        UserService userService = new UserServiceImpl();
        List<User> allUsers = userService.getAllUsers();

        allUsersObserv.clear();
        allUsersObserv.addAll(allUsers);

    }

    private class ActionsCellFactory implements Callback<TableColumn.CellDataFeatures<User, GridPane>, ObservableValue<GridPane>> {

        @Override
        public ObservableValue<GridPane> call(TableColumn.CellDataFeatures<User, GridPane> param) {
            User user = param.getValue();

            Button editBtn = new Button("Edit");
            editBtn.setOnAction(new EditUserEvent(user.getId()));

            Button delBtn = new Button("Delete");
            delBtn.setOnAction(new DeleteUserEvent(user.getId()));

            GridPane bar = new GridPane();
            bar.setHgap(10);
            bar.setVgap(10);
            bar.setPadding(new Insets(10, 10, 10, 10));

            bar.add(editBtn, 0, 0);
            bar.add(delBtn, 1, 0);

            return new SimpleObjectProperty<>(bar);
        }
    }

    private class EditUserEvent implements EventHandler<ActionEvent> {

        private Long userId;

        public EditUserEvent(Long userId) {
            this.userId = userId;
        }

        @Override
        public void handle(ActionEvent event) {
            UserService userService = new UserServiceImpl();
            User userById = userService.readUserById(userId);
            Object createUserWindow = AppWindowsFactory.getFactory().getWindowController("createUserWindow");
            CreateUserController controller = (CreateUserController) createUserWindow;
            controller.fillUserForm(userById);

            AppWindowsFactory.getFactory().showWindow("createUserWindow");

        }
    }

    private class DeleteUserEvent implements EventHandler<ActionEvent> {

        private Long userId;

        public DeleteUserEvent(Long userId) {
            this.userId = userId;
        }

        @Override
        public void handle(ActionEvent event) {
            UserService userService = new UserServiceImpl();
            userService.delUser(userId);
            reloadDataList();

        }
    }
}
