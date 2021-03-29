package com.stormnet.net.client.maintenance.impl;

import com.stormnet.net.client.exceptions.ServiceException;
import com.stormnet.net.client.maintenance.DescriptionService;
import com.stormnet.net.client.services.SocketConnection;
import com.stormnet.net.data.description.Description;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;
import java.util.ArrayList;
import java.util.List;


public class DescriptionServiceImpl implements DescriptionService {

    @Override
    public Description readDescriptionById(Long descriptionId) {
        SocketConnection connection = null;
        try {
            connection = new SocketConnection();
            JSONWriter jsonWriter = connection.getJsonWriter();

            jsonWriter.object();
            jsonWriter.key("request-header").object();
            jsonWriter.key("command-name").value("read-description-by-id-command");
            jsonWriter.endObject();

            jsonWriter.key("request-data").object();
            jsonWriter.key("descriptionId").value(descriptionId);
            jsonWriter.endObject();
            jsonWriter.endObject();


            connection.flush();

            JSONTokener tokener = connection.getTokener();
            JSONObject responseJson = (JSONObject) tokener.nextValue();

            List<Description> allDescriptions = new ArrayList<>();

            int responseCode = responseJson.getInt("response-code");

            Description description = null;
            if (responseCode == 200) {
                JSONObject fullDescriptionJson = responseJson.getJSONObject("response-data");

                Long id = fullDescriptionJson.getLong("id");
                String fullDescription = fullDescriptionJson.getString("fullDescription");


                description = new Description(fullDescription);
                description.setId(id);

                allDescriptions.add(description);

            }

            connection.close();

            return description;

        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

}
