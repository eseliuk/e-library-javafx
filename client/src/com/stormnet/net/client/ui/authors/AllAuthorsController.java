package com.stormnet.net.client.ui.authors;

import com.stormnet.net.client.maintenance.AuthorService;
import com.stormnet.net.client.maintenance.impl.AuthorServiceImpl;
import com.stormnet.net.client.ui.authors.create.CreateAuthorController;
import com.stormnet.net.client.ui.common.AppWindowsFactory;
import com.stormnet.net.data.author.Author;
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

public class AllAuthorsController implements Initializable {

    private ObservableList<Author> allAuthorsObserv;

    @FXML
    private TableView<Author> allAuthorsTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn<Author, GridPane> actionColumn = new TableColumn<>("Actions");
        actionColumn.setMinWidth(150);
        actionColumn.setCellValueFactory(new ActionsCellFactory());

        allAuthorsTable.getColumns().add(actionColumn);
        allAuthorsObserv = FXCollections.observableArrayList();
        allAuthorsTable.setItems(allAuthorsObserv);

        reloadAuthorsListFromServer();

    }

    public void addAuthorBtnPressed (ActionEvent actionEvent) {

        AppWindowsFactory factory = AppWindowsFactory.getFactory();
        CreateAuthorController controller = (CreateAuthorController) factory.getWindowController("createAuthorWindow");
        controller.clearAuthorForm();
        factory.showWindow("createAuthorWindow");

    }

    public void closeBtnPressed (ActionEvent actionEvent) {

        AppWindowsFactory factory = AppWindowsFactory.getFactory();
        factory.hideWindow("allAuthorsWindow");

    }

    public void reloadAuthorsListFromServer () {
        AuthorService authorService = new AuthorServiceImpl();
        List<Author> allAuthors = authorService.getAllAuthors();

        allAuthorsObserv.clear();
        allAuthorsObserv.addAll(allAuthors);
    }

    private class ActionsCellFactory implements Callback<TableColumn.CellDataFeatures<Author, GridPane>, ObservableValue<GridPane>> {

        @Override
        public ObservableValue<GridPane> call(TableColumn.CellDataFeatures<Author, GridPane> param) {
            Author author = param.getValue();

            Button editBtn = new Button("Edit");
            editBtn.setOnAction(new EditAuthorEvent(author.getId()));

            Button delBtn = new Button("Delete");
            delBtn.setOnAction(new DeleteAuthorEvent(author.getId()));

            GridPane bar = new GridPane();
            bar.setHgap(5);
            bar.setVgap(5);
            bar.setPadding(new Insets(10, 10, 10, 10));

            bar.add(editBtn, 0, 0);
            bar.add(delBtn, 1, 0);

            return new SimpleObjectProperty<>(bar);
        }

        private class EditAuthorEvent implements EventHandler<ActionEvent> {

            private Long authorId;

            public EditAuthorEvent(Long authorId) {
                this.authorId = authorId;
            }

            @Override
            public void handle(ActionEvent event) {
                AuthorService authorService = new AuthorServiceImpl();
                Author dbAuthor = authorService.readAuthorById(authorId);

                Object createAuthorWindow = AppWindowsFactory.getFactory().getWindowController("createAuthorWindow");
                CreateAuthorController controller = (CreateAuthorController) createAuthorWindow;
                controller.fillAuthorForm(dbAuthor);
                AppWindowsFactory.getFactory().showWindow("createAuthorWindow");

            }
        }
        private class DeleteAuthorEvent implements EventHandler<ActionEvent> {

            private Long authorId;

            public DeleteAuthorEvent(Long authorId) {
                this.authorId = authorId;
            }

            @Override
            public void handle(ActionEvent event) {
                AuthorService authorService = new AuthorServiceImpl();
                authorService.delAuthor(authorId);
                reloadAuthorsListFromServer();
            }
        }
    }
}
