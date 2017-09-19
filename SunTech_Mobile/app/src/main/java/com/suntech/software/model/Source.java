package com.suntech.software.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ramazan on 17/8/2016.
 */
public class Source {
    private int Record_Source_id;
    private String Record_Source;

    public Source(JSONObject jo) {
        try {
            setRecord_Source_id(jo.getInt("Record_Source_id"));
            setRecord_Source(jo.getString("Record_Source"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getRecord_Source_id() {
        return Record_Source_id;
    }

    public void setRecord_Source_id(int record_Source_id) {
        Record_Source_id = record_Source_id;
    }

    public String getRecord_Source() {
        return Record_Source;
    }

    public void setRecord_Source(String record_Source) {
        Record_Source = record_Source;
    }
}
