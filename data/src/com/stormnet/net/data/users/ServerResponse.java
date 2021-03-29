package com.stormnet.net.data.users;

import java.util.Objects;

public class ServerResponse {

    private Integer responseCode;

    private String responseMessage;

    public ServerResponse(Integer responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerResponse that = (ServerResponse) o;
        return responseCode.equals(that.responseCode) &&
                responseMessage.equals(that.responseMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(responseCode, responseMessage);
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "responseCode=" + responseCode +
                ", responseMessage='" + responseMessage + '\'' +
                '}';
    }
}
