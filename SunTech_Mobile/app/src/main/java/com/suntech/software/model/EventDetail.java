package com.suntech.software.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ramazan on 13/8/2016.
 */
public class EventDetail {
    private String Tarih;
    private String Saat;
    private String Event_Detail_Type;
    private String Event_Detail;

    public EventDetail(JSONObject jo) {
        try {
            setTarih(jo.getString("Tarih"));
            setSaat(jo.getString("Saat"));
            setEvent_Detail_Type(jo.getString("Event_Detail_Type"));
            setEvent_Detail(jo.getString("Event_Detail_Text"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getTarih() {
        return Tarih;
    }

    public void setTarih(String tarih) {
        Tarih = tarih;
    }

    public String getSaat() {
        return Saat;
    }

    public void setSaat(String saat) {
        Saat = saat;
    }

    public String getEvent_Detail_Type() {
        return Event_Detail_Type;
    }

    public void setEvent_Detail_Type(String event_Detail_Type) {
        Event_Detail_Type = event_Detail_Type;
    }

    public String getEvent_Detail() {
        return Event_Detail;
    }

    public void setEvent_Detail(String event_Detail) {
        Event_Detail = event_Detail;
    }
}
