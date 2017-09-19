package com.suntech.software.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ramazan on 17/8/2016.
 */
public class CompanyList {
    private int Company_id;
    private String Company_Name;

    public CompanyList(JSONObject jo) {
        try {
            setCompany_id(jo.getInt("Company_id"));
            setCompany_Name(jo.getString("Company_Name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public int getCompany_id() {
        return Company_id;
    }

    public void setCompany_id(int company_id) {
        Company_id = company_id;
    }

    public String getCompany_Name() {
        return Company_Name;
    }

    public void setCompany_Name(String company_Name) {
        Company_Name = company_Name;
    }
}
