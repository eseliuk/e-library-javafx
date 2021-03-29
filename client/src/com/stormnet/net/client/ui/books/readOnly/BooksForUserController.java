package com.stormnet.net.client.ui.books.readOnly;

import com.stormnet.net.client.maintenance.BookService;
import com.stormnet.net.client.maintenance.DescriptionService;
import com.stormnet.net.client.maintenance.impl.BookServiceImpl;
import com.stormnet.net.client.maintenance.impl.DescriptionServiceImpl;
import com.stormnet.net.client.ui.description.FullDescriptionController;
import com.stormnet.net.client.ui.common.AppWindowsFactory;
import com.stormnet.net.data.books.Book;
import com.stormnet.net.data.description.Description;
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

public class BooksForUserController implements Initializable {

    @FXML
    private TableView<Book> allBooksTable;

    private ObservableList<Book> allBooksObserv;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn<Book, GridPane> actionColumn = new TableColumn<>("Details");
        actionColumn.setMinWidth(120);
        actionColumn.setCellValueFactory(new ActionsCellFactory());

        allBooksTable.getColumns().add(actionColumn);
        allBooksObserv = FXCollections.observableArrayList();
        allBooksTable.setItems(allBooksObserv);

        reloadBooksListFromServer();

    }
    public void allAuthorsBtnPressed (ActionEvent actionEvent) {
        AppWindowsFactory factory = AppWindowsFactory.getFactory();
        factory.showWindow("authorsForUserWindow");
    }

    public void logoutBtnPressed (ActionEvent actionEvent) {
        AppWindowsFactory factory = AppWindowsFactory.getFactory();
        factory.hideWindow("booksForUserWindow");
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

            Button moreDescBtn = new Button("See more");
            moreDescBtn.setOnAction(new MoreDescEvent(book.getId()));

            GridPane bar = new GridPane();
            bar.setHgap(10);
            bar.setVgap(10);
            bar.setPadding(new Insets(10, 10, 10, 10));

            bar.add(moreDescBtn, 0, 0);


            return new SimpleObjectProperty<>(bar);
        }
   }

   private class MoreDescEvent implements EventHandler<ActionEvent> {

       private Long descriptionId;

       public MoreDescEvent(Long descriptionId) {
           this.descriptionId = descriptionId;
       }

       @Override
        public void handle(ActionEvent event) {

           DescriptionService descriptionService = new DescriptionServiceImpl();
           Description dBDesc = descriptionService.readDescriptionById(descriptionId);

           Object fullDescriptionWindow = AppWindowsFactory.getFactory().getWindowController("fullDescriptionWindow");

           FullDescriptionController controller = (FullDescriptionController) fullDescriptionWindow;
           controller.fillDescForm(dBDesc);
           AppWindowsFactory.getFactory().showWindow("fullDescriptionWindow");

        }
   }
}
