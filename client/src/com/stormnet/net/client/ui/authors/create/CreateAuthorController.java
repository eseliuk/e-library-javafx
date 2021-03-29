package com.stormnet.net.client.ui.authors.create;

import com.stormnet.net.client.maintenance.AuthorService;
import com.stormnet.net.client.maintenance.impl.AuthorServiceImpl;
import com.stormnet.net.client.ui.authors.AllAuthorsController;
import com.stormnet.net.client.ui.common.AppWindowsFactory;
import com.stormnet.net.data.author.Author;
import com.stormnet.net.utils.numbers.NumbersUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CreateAuthorController {

    @FXML
    private TextField idField;

    @FXML
    private TextField fullNameField;

    @FXML
    private TextArea profileArea;

    public void saveAuthorBtnPressed (ActionEvent actionEvent) {

        Long authorId = NumbersUtils.parseLong(idField.getText());
        String fullName = fullNameField.getText();
        String profile = profileArea.getText();

        Author author = new Author( fullName, profile);
        author.setId(authorId);

        AuthorService authorService = new AuthorServiceImpl();
        authorService.saveAuthor(author);

        AppWindowsFactory factory = AppWindowsFactory.getFactory();

        Object object = factory.getWindowController("allAuthorsWindow");
        AllAuthorsController allAuthorsController = (AllAuthorsController) object;
        allAuthorsController.reloadAuthorsListFromServer();
        factory.hideWindow("createAuthorWindow");
    }

    public void cancelBtnPressed (ActionEvent actionEvent)  {
        AppWindowsFactory factory = AppWindowsFactory.getFactory();
        factory.hideWindow("createAuthorWindow");

    }

    public void fillAuthorForm (Author author) {
        Long authorId = author.getId();
        if(authorId == null) {
            idField.setText("");
        } else {
            idField.setText(author.getId().toString());
        }
        fullNameField.setText(author.getFullName());
        profileArea.setText(author.getProfile());
    }

    public void clearAuthorForm() {
        idField.setText("");
        fullNameField.setText("");
        profileArea.setText("");
    }
}
