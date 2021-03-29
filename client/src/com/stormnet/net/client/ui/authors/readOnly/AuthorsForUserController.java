package com.stormnet.net.client.ui.authors.readOnly;

import com.stormnet.net.client.maintenance.AuthorService;
import com.stormnet.net.client.maintenance.impl.AuthorServiceImpl;
import com.stormnet.net.client.ui.common.AppWindowsFactory;
import com.stormnet.net.data.author.Author;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AuthorsForUserController implements Initializable {

    private ObservableList<Author> allAuthorsObserv;

    @FXML
    private TableView<Author> allAuthorsTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        allAuthorsObserv = FXCollections.observableArrayList();
        allAuthorsTable.setItems(allAuthorsObserv);

        reloadAuthorsListFromServer();

    }

    public void closeBtnPressed (ActionEvent actionEvent) {
        AppWindowsFactory factory = AppWindowsFactory.getFactory();
        factory.hideWindow("authorsForUserWindow");

    }

    public void reloadAuthorsListFromServer () {
        AuthorService authorService = new AuthorServiceImpl();
        List<Author> allAuthors = authorService.getAllAuthors();

        allAuthorsObserv.clear();
        allAuthorsObserv.addAll(allAuthors);
    }
}
