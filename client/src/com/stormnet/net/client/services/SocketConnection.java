package com.stormnet.net.client.services;

import org.json.JSONTokener;
import org.json.JSONWriter;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class SocketConnection {

    private JSONWriter jsonWriter;

    private JSONTokener tokener;

    private BufferedWriter bufferedWriter;

    private OutputStream serverOs;

    private InputStream serverIs;

    public SocketConnection() throws IOException {
        InetAddress ip = InetAddress.getByName("localhost");
        Socket socket = new Socket(ip, 8848);
        this.serverOs = socket.getOutputStream();
        this.serverIs = socket.getInputStream();

        OutputStreamWriter osr = new OutputStreamWriter(serverOs);
        this.bufferedWriter = new BufferedWriter(osr);
        this.jsonWriter = new JSONWriter(bufferedWriter);

        this.tokener = new JSONTokener(serverIs);

    }

    public void flush () throws IOException {
        bufferedWriter.flush();
    }

    public void close () throws IOException {
        serverOs.close();
        serverIs.close();
    }

    public JSONWriter getJsonWriter() {
        return jsonWriter;
    }

    public JSONTokener getTokener() {
        return tokener;
    }
}
