package com.jet.edu.client;

import org.json.JSONObject;

/**
 * Factory of states of commands
 */
public class Factory {
    HistoryState historyState;
    RegisterState registerState;
    SendState sendState;
    RoomState roomState;

    /**
     * initialize state the entrance to the room
     * @param jsonObject
     * @param connector
     * @return
     */
    public RoomState getRoomState(JSONObject jsonObject, Connector connector) {
        return roomState = new RoomState(jsonObject, connector);
    }

    /**
     * initialize JsonObject and connector
     * @param jsonObject
     * @param connector
     */
    public void setHistoryState(JSONObject jsonObject, Connector connector) {
        this.historyState = new HistoryState(jsonObject, connector);
    }

    /**
     * set State of registration command
     * @param jsonObject
     * @param connector
     */
    public void setRegisterState(JSONObject jsonObject, Connector connector) {
        this.registerState = new RegisterState(jsonObject, connector);
    }



    /**
     *  set State of send command
     * @param jsonObject

     * @param connector
     */
    public void setSendState(JSONObject jsonObject, Connector connector) {
        this.sendState = new SendState(jsonObject, connector);
    }

    /**
     *  get State of History command
     */
    public HistoryState getHistoryState() {
        return historyState;
    }

    /**
     *  get State of Register command
     */
    public RegisterState getRegisterState() {
        return registerState;
    }

    /**
     * get Send state command
     * @return
     */
    public SendState getSendState() {
        return sendState;
    }

    /**
     * get Room state command
     * @return
     */
    public RoomState getRoomState() {
        return roomState;
    }

}
