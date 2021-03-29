package com.stormnet.net.server.commands.impl;
import com.stormnet.net.data.users.ServerResponse;
import com.stormnet.net.server.commands.ServerCommand;
import org.json.JSONObject;
import org.json.JSONWriter;

public class UnknownCommand implements ServerCommand {

    @Override
    public void processCommand(JSONObject object, JSONWriter jsonWriter) {

        ServerResponse response = new ServerResponse(404, "Unknown Command!");

        jsonWriter.object();
        jsonWriter.key("response-code").value(response.getResponseCode());
        jsonWriter.key("response-message").value(response.getResponseMessage());
        jsonWriter.endObject();
    }
}
