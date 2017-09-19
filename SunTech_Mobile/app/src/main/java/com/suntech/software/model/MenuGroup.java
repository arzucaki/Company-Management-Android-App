package com.suntech.software.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ramazangunes on 26.05.2017.
 */

public class MenuGroup {


    public String getMenuGroup_Name() {
        return MenuGroup_Name;
    }

    public void setMenuGroup_Name(String menuGroup_Name) {
        MenuGroup_Name = menuGroup_Name;
    }

    public int getMenuGroup_id() {
        return MenuGroup_id;
    }

    public void setMenuGroup_id(int menuGroup_id) {
        MenuGroup_id = menuGroup_id;
    }

    private String MenuGroup_Name;
    private int MenuGroup_id;

    public MenuGroup(JSONObject jo) {
        try {
            setMenuGroup_Name(jo.getString("TileGroup_Name"));
            setMenuGroup_id(jo.getInt("TileGroup_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
