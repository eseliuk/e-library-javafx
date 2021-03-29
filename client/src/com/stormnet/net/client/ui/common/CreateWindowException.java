package com.stormnet.net.client.ui.common;

public class CreateWindowException extends RuntimeException{
    public CreateWindowException(Throwable cause) {
        super("Window creation error: " + cause);
    }
}
