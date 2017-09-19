package com.suntech.software.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ramazan on 18/8/2016.
 */
public class ErrorCode {
    private int Error_Code_id;
    private int Error_Code;
    private String Error_Code_Description;

    public ErrorCode(JSONObject jo) {
        try {
            setError_Code_id(jo.getInt("Error_Code_id"));
            setError_Code(jo.getInt("Error_Code"));
            setError_Code_Description(jo.getString("Error_Code_Description"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getError_Code_id() {
        return Error_Code_id;
    }

    public void setError_Code_id(int error_Code_id) {
        Error_Code_id = error_Code_id;
    }

    public int getError_Code() {
        return Error_Code;
    }

    public void setError_Code(int error_Code) {
        Error_Code = error_Code;
    }

    public String getError_Code_Description() {
        return Error_Code_Description;
    }

    public void setError_Code_Description(String error_Code_Description) {
        Error_Code_Description = error_Code_Description;
    }
}
