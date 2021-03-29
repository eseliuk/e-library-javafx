package com.stormnet.net.server;
import com.stormnet.net.server.commands.CommandFactory;
import com.stormnet.net.server.commands.ServerCommand;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8848);

        CommandFactory commandFactory = new CommandFactory();

        while (true) {
            Socket clientSocket = serverSocket.accept();

            InputStream clientIs = clientSocket.getInputStream();
            OutputStream clientOs = clientSocket.getOutputStream();
            JSONTokener tokener = new JSONTokener(clientIs);

            OutputStreamWriter osr = new OutputStreamWriter(clientOs);
            BufferedWriter bufferedWriter = new BufferedWriter(osr);
            JSONWriter jsonWriter = new JSONWriter(bufferedWriter);

            Object o = tokener.nextValue();
            JSONObject jsonObject = (JSONObject) o;

            JSONObject header = jsonObject.getJSONObject("request-header");
            JSONObject data = getJsonData(jsonObject);

            String commandName = header.getString("command-name");
            ServerCommand command = commandFactory.getCommandByName(commandName);

            command.processCommand(data, jsonWriter);

            bufferedWriter.flush();
            clientIs.close();
            clientOs.close();
        }
    }

    private static JSONObject getJsonData(JSONObject request) {
        try {
            return request.getJSONObject("request-data");
        } catch (Exception e) {
            return new JSONObject();
        }
    }
}
