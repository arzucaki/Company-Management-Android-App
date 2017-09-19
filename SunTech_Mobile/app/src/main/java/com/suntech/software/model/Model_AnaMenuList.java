package com.suntech.software.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ramazangunes on 30.05.2017.
 */

public class Model_AnaMenuList {

    public int getAna_Menu_id() {
        return Ana_Menu_id;
    }

    public void setAna_Menu_id(int ana_Menu_id) {
        Ana_Menu_id = ana_Menu_id;
    }

    public String getAna_Menu() {
        return Ana_Menu;
    }

    public void setAna_Menu(String ana_Menu) {
        Ana_Menu = ana_Menu;
    }
    public int getGroup_id() {
        return Group_id;
    }

    public void setGroup_id(int group_id) {
        Group_id = group_id;
    }


    public String Ana_Menu;

    public int Ana_Menu_id;

    public int Group_id;

    public Model_AnaMenuList(JSONObject jo) {
        try {
            setAna_Menu(jo.getString("TileItem_Name"));
            setAna_Menu_id(jo.getInt("TileItem"));
            setGroup_id(jo.getInt("TileGroup_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
