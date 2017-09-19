package com.suntech.software.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ramazangunes on 26.05.2017.
 */

public class Auth_Company {


    public String getCompany_Name() {
        return Company_Name;
    }

    public void setCompany_Name(String company_Name) {
        Company_Name = company_Name;
    }

    public int getCompany_id() {
        return Company_id;
    }

    public void setCompany_id(int company_id) {
        Company_id = company_id;
    }

    private String Company_Name;
    private int Company_id;

    public Auth_Company(JSONObject jo) {
        try {
            setCompany_Name(jo.getString("Company_Name"));
            setCompany_id(jo.getInt("Company_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
