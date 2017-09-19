package com.suntech.software.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ramazan on 17/8/2016.
 */
public class RecordType {
    private int HD_Record_Type_id;
    private String Record_Type;

    public RecordType(JSONObject jo) {
        try {
            setHD_Record_Type_id(jo.getInt("HD_Record_Type_id"));
            setRecord_Type(jo.getString("Record_Type"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getHD_Record_Type_id() {
        return HD_Record_Type_id;
    }

    public void setHD_Record_Type_id(int HD_Record_Type_id) {
        this.HD_Record_Type_id = HD_Record_Type_id;
    }

    public String getRecord_Type() {
        return Record_Type;
    }

    public void setRecord_Type(String record_Type) {
        Record_Type = record_Type;
    }
}
