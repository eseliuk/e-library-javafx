package com.stormnet.net.server.commands.description;

import com.stormnet.net.data.description.Description;
import com.stormnet.net.server.commands.ServerCommand;
import com.stormnet.net.server.dao.DaoFactory;
import com.stormnet.net.server.dao.DescriptionDao;
import org.json.JSONObject;
import org.json.JSONWriter;

public class ReadDescriptionByIdCommand implements ServerCommand {
    @Override
    public void processCommand(JSONObject object, JSONWriter jsonWriter) {
        Long descriptionId = object.getLong("descriptionId");

        DescriptionDao descriptionDao = DaoFactory.getCurrentDaoFactory().getDescriptionDao();
        Description description = descriptionDao.readDescription(descriptionId);

        jsonWriter.object();
        jsonWriter.key("response-code").value(200);
        jsonWriter.key("response-message").value("OK");
        jsonWriter.key("response-data").object();
        jsonWriter.key("id").value(description.getId());
        jsonWriter.key("fullDescription").value(description.getFullDescription());
        jsonWriter.endObject();
        jsonWriter.endObject();

    }

}
