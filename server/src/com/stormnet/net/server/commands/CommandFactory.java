package com.stormnet.net.server.commands;

import com.stormnet.net.server.commands.auth.AuthenticationCommand;
import com.stormnet.net.server.commands.auth.RegistrationCommand;
import com.stormnet.net.server.commands.author.DeleteAuthorCommand;
import com.stormnet.net.server.commands.author.ReadAllAuthorsCommand;
import com.stormnet.net.server.commands.author.ReadAuthorByIdCommand;
import com.stormnet.net.server.commands.author.SaveAuthorCommand;
import com.stormnet.net.server.commands.book.DeleteBookCommand;
import com.stormnet.net.server.commands.book.ReadAllBooksCommand;
import com.stormnet.net.server.commands.book.ReadBookByIdCommand;
import com.stormnet.net.server.commands.book.SaveBookCommand;
import com.stormnet.net.server.commands.description.ReadDescriptionByIdCommand;
import com.stormnet.net.server.commands.impl.*;
import com.stormnet.net.server.commands.user.*;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    private Map<String, ServerCommand > allCommands =  new HashMap();

    public CommandFactory() {
        allCommands.put("auth-command", new AuthenticationCommand());
        allCommands.put("registration-command", new RegistrationCommand());

        allCommands.put("read-all-users-command", new ReadAllUsersCommand());
        allCommands.put("read-users-by-id-command", new ReadUserByIdCommand());
        allCommands.put("save-user-command", new SaveUserCommand());
        allCommands.put("delete-user-command", new DeleteUserCommand());

        allCommands.put("read-all-books-command", new ReadAllBooksCommand());
        allCommands.put("read-book-by-id-command", new ReadBookByIdCommand());
        allCommands.put("save-book-command", new SaveBookCommand());
        allCommands.put("delete-book-command", new DeleteBookCommand());

        allCommands.put("read-description-by-id-command", new ReadDescriptionByIdCommand());

        allCommands.put("read-all-authors-command", new ReadAllAuthorsCommand());
        allCommands.put("read-author-by-id-command", new ReadAuthorByIdCommand());
        allCommands.put("save-author-command", new SaveAuthorCommand());
        allCommands.put("delete-author-command", new DeleteAuthorCommand());

    }
    public ServerCommand getCommandByName(String commandName) {
        ServerCommand command = allCommands.get(commandName);
        if(command == null) {
            return  new UnknownCommand();
        }
        return  command;
    }
}
