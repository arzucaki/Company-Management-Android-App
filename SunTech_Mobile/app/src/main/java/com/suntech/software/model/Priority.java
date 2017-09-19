package com.suntech.software.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ramazan on 17/8/2016.
 */
public class Priority {
    private int Priority_id;
    private String Priority;

    public Priority(JSONObject jo) {
        try {
            setPriority_id(jo.getInt("Priority_id"));
            setPriority(jo.getString("Priority"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }

    public int getPriority_id() {
        return Priority_id;
    }

    public void setPriority_id(int priority_id) {
        Priority_id = priority_id;
    }


}
