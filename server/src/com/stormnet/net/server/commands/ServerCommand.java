package com.stormnet.net.server.commands;

import org.json.JSONObject;
import org.json.JSONWriter;

public interface ServerCommand {

    void processCommand(JSONObject clientData, JSONWriter jsonWriter);

}
