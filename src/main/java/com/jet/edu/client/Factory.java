package com.jet.edu.client;

import org.json.JSONObject;

/**
 * Created by Павел on 13.11.2015.
 */
public class Factory {
    HistoryState historyState;
    RegisterState registerState;
    SendState sendState;

    public void setHistoryState(JSONObject jsonObject, Connector connector) {
        this.historyState = new HistoryState(jsonObject, connector);
    }

    public void setRegisterState(JSONObject jsonObject, Connector connector) {
        this.registerState = new RegisterState(jsonObject, connector);
    }

    public void setSendState(JSONObject jsonObject, Connector connector) {
        this.sendState = new SendState(jsonObject, connector);
    }

    public HistoryState getHistoryState() {
        return historyState;
    }

    public RegisterState getRegisterState() {
        return registerState;
    }

    public SendState getSendState() {
        return sendState;
    }
}
