package com.stormnet.net.client.ui.common;
import com.stormnet.net.client.ui.authors.AllAuthorsWindow;
import com.stormnet.net.client.ui.authors.create.CreateAuthorWindow;
import com.stormnet.net.client.ui.authors.readOnly.AuthorsForUserWindow;
import com.stormnet.net.client.ui.books.readOnly.BooksForUserWindow;
import com.stormnet.net.client.ui.books.AllBooksWindow;
import com.stormnet.net.client.ui.books.create.CreateBookWindow;
import com.stormnet.net.client.ui.description.FullDescriptionWindow;

import com.stormnet.net.client.ui.users.AllUsersWindow;
import com.stormnet.net.client.ui.users.create.CreateUserWindow;

import com.stormnet.net.client.ui.users.registration.RegistrationWindow;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

public class AppWindowsFactory {

    private static final AppWindowsFactory factory = new AppWindowsFactory();

    public static AppWindowsFactory getFactory() {
        return factory;
    }

    private Stage mainWindow;

    private Map<String, BaseWindow> allWindows = new HashMap<>();

    private AppWindowsFactory() {
        try {
            RegistrationWindow registrationWindow = new RegistrationWindow();
            allWindows.put("registrationWindow", registrationWindow);

            AllUsersWindow allUsersWindow = new AllUsersWindow();
            allWindows.put("allUsersWindow", allUsersWindow);

            CreateUserWindow createUserWindow = new CreateUserWindow();
            allWindows.put("createUserWindow", createUserWindow);

            AllBooksWindow allBooksWindow = new AllBooksWindow();
            allWindows.put("allBooksWindow", allBooksWindow);

            CreateBookWindow createBookWindow = new CreateBookWindow();
            allWindows.put("createBookWindow", createBookWindow);

            BooksForUserWindow booksForUserWindow = new BooksForUserWindow();
            allWindows.put("booksForUserWindow", booksForUserWindow);

            FullDescriptionWindow fullDescriptionWindow = new FullDescriptionWindow();
            allWindows.put("fullDescriptionWindow", fullDescriptionWindow);

            AllAuthorsWindow allAuthorsWindow = new AllAuthorsWindow();
            allWindows.put("allAuthorsWindow", allAuthorsWindow);

            AuthorsForUserWindow authorsForUserWindow = new AuthorsForUserWindow();
            allWindows.put("authorsForUserWindow", authorsForUserWindow);

            CreateAuthorWindow createAuthorWindow = new CreateAuthorWindow();
            allWindows.put("createAuthorWindow", createAuthorWindow);


        } catch (Exception e) {
            throw new CreateWindowException(e);
        }
    }

    public void setMainWindow(Stage mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void showMainWindow() {
        mainWindow.show();
    }

    public void hideMainWindow() {
        mainWindow.hide();
    }

    public void showWindow(String windowName) {
        Stage stage = allWindows.get(windowName);

        if (stage != null) {
            stage.show();
        }
    }

    public void hideWindow(String windowName) {
        Stage stage = allWindows.get(windowName);
        if (stage != null) {
            stage.hide();
        }
    }

    public Object getWindowController(String windowName) {
        BaseWindow baseWindow = allWindows.get(windowName);
        Object controller = baseWindow.getController();
        return controller;
    }

}

