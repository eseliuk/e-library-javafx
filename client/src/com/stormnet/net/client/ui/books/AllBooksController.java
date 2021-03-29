package com.stormnet.net.client.ui.books;

import com.stormnet.net.client.maintenance.BookService;
import com.stormnet.net.client.maintenance.impl.BookServiceImpl;
import com.stormnet.net.client.ui.books.create.CreateBookController;
import com.stormnet.net.client.ui.common.AppWindowsFactory;
import com.stormnet.net.data.books.Book;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AllBooksController implements Initializable {


    @FXML
    private TableView<Book> allBooksTable;

    private ObservableList<Book> allBooksObserv;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn<Book, GridPane> actionColumn = new TableColumn<>("Actions");
        actionColumn.setMinWidth(150);
        actionColumn.setCellValueFactory(new ActionsCellFactory());

        allBooksTable.getColumns().add(actionColumn);
        allBooksObserv = FXCollections.observableArrayList();
        allBooksTable.setItems(allBooksObserv);

        reloadBooksListFromServer();

    }

    public void allAuthorsBtnPressed (ActionEvent actionEvent) {
        AppWindowsFactory factory = AppWindowsFactory.getFactory();
        factory.showWindow("allAuthorsWindow");
    }

    public void addBookBtnPressed (ActionEvent actionEvent) {

        AppWindowsFactory factory = AppWindowsFactory.getFactory();
        CreateBookController controller = (CreateBookController) factory.getWindowController("createBookWindow");
        controller.clearBookForm();
        factory.showWindow("createBookWindow");

    }

    public void logoutBtnPressed (ActionEvent actionEvent) {
        AppWindowsFactory factory = AppWindowsFactory.getFactory();
        factory.hideWindow("allBooksWindow");
        factory.showMainWindow();

    }

    public void reloadBooksListFromServer () {
        BookService bookService = new BookServiceImpl();
        List<Book> allBooks = bookService.getAllBooks();

        allBooksObserv.clear();
        allBooksObserv.addAll(allBooks);

    }
    private class ActionsCellFactory implements Callback<TableColumn.CellDataFeatures<Book, GridPane>, ObservableValue<GridPane>> {

        @Override
        public ObservableValue<GridPane> call(TableColumn.CellDataFeatures<Book, GridPane> param) {
            Book book = param.getValue();

            Button editBtn = new Button("Edit");
            editBtn.setOnAction(new EditBookEvent(book.getId()));

            Button delBtn = new Button("Delete");
            delBtn.setOnAction(new DeleteBookEvent(book.getId()));

            GridPane bar = new GridPane();
            bar.setHgap(5);
            bar.setVgap(5);
            bar.setPadding(new Insets(10, 10, 10, 10));

            bar.add(editBtn, 0, 0);
            bar.add(delBtn, 1, 0);

            return new SimpleObjectProperty<>(bar);
        }

    }

    private class EditBookEvent implements EventHandler<ActionEvent> {

        private Long bookId;

        public EditBookEvent(Long bookId) {
            this.bookId = bookId;
        }

        @Override
        public void handle(ActionEvent event) {
            BookService bookService = new BookServiceImpl();
            Book dbBook = bookService.readBookById(bookId);

            Object createBookWindow = AppWindowsFactory.getFactory().getWindowController("createBookWindow");
            CreateBookController controller = (CreateBookController) createBookWindow;
            controller.fillBookForm(dbBook);
            AppWindowsFactory.getFactory().showWindow("createBookWindow");

        }
    }
     private class DeleteBookEvent implements EventHandler<ActionEvent> {

        private Long bookId;

        public DeleteBookEvent(Long bookId) {
            this.bookId = bookId;
        }

        @Override
        public void handle(ActionEvent event) {
            BookService bookService = new BookServiceImpl();
            bookService.delBook(bookId);
            reloadBooksListFromServer();

        }
    }
}
