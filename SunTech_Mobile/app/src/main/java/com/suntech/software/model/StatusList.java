package com.suntech.software.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ramazan on 12/8/2016.
 */
public class StatusList {

    private int HD_Event_Status_id;
    private String Event_Status;
    private int Status_Type;

    public int getStatus_Type() {
        return Status_Type;
    }

    public void setStatus_Type(int status_Type) {
        Status_Type = status_Type;
    }

    public String getEvent_Status() {
        return Event_Status;
    }

    public void setEvent_Status(String event_Status) {
        Event_Status = event_Status;
    }

    public int getHD_Event_Status_id() {
        return HD_Event_Status_id;
    }

    public void setHD_Event_Status_id(int HD_Event_Status_id) {
        this.HD_Event_Status_id = HD_Event_Status_id;
    }

    public StatusList(JSONObject jo) {
        try {
            setHD_Event_Status_id(jo.getInt("HD_Event_Status_id"));
            setEvent_Status(jo.getString("Event_Status"));
            setStatus_Type(jo.getInt("Status_Type"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
