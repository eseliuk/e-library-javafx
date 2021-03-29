package com.stormnet.net.client.ui.books.create;

import com.stormnet.net.client.maintenance.BookService;
import com.stormnet.net.client.maintenance.impl.BookServiceImpl;
import com.stormnet.net.client.ui.books.AllBooksController;
import com.stormnet.net.client.ui.common.AppWindowsFactory;
import com.stormnet.net.data.books.Book;
import com.stormnet.net.utils.numbers.NumbersUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class CreateBookController {

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField genreField;

    @FXML
    private TextField descriptionField;

    public void saveBookBtnPressed (ActionEvent actionEvent) {

        Long bookId = NumbersUtils.parseLong(idField.getText());
        String name = nameField.getText();
        String author = authorField.getText();
        String genre = genreField.getText();
        String description = descriptionField.getText();

        Book book = new Book( name,author, genre, description);
        book.setId(bookId);

        BookService bookService = new BookServiceImpl();
        bookService.saveBook(book);

        AppWindowsFactory factory = AppWindowsFactory.getFactory();

        Object object = factory.getWindowController("allBooksWindow");
        AllBooksController allBooksController = (AllBooksController) object;
        allBooksController.reloadBooksListFromServer();
        factory.hideWindow("createBookWindow");

    }

    public void cancelBtnPressed (ActionEvent actionEvent)  {
        AppWindowsFactory factory = AppWindowsFactory.getFactory();
        factory.hideWindow("createBookWindow");

    }


    public void fillBookForm(Book book) {
        Long bookId = book.getId();
        if(bookId == null) {
            idField.setText("");
        } else {
            idField.setText(book.getId().toString());
        }

        nameField.setText(book.getName());
        authorField.setText(book.getAuthor());
        genreField.setText(book.getGenre());
        descriptionField.setText(book.getDescription());

    }
    public void clearBookForm() {
        idField.setText("");
        nameField.setText("");
        authorField.setText("");
        genreField.setText("");
        descriptionField.setText("");
    }

}
