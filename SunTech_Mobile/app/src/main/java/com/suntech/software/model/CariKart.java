package com.suntech.software.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ramazan on 18/8/2016.
 */
public class CariKart {
    private int Cari_Kart_id;
    private String Cari_Adi;

    public CariKart(JSONObject jo) {
        try {
            setCari_Kart_id(jo.getInt("Cari_Kart_id"));
            setCari_Adi(jo.getString("Cari_Adi"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getCari_Kart_id() {
        return Cari_Kart_id;
    }

    public void setCari_Kart_id(int cari_Kart_id) {
        Cari_Kart_id = cari_Kart_id;
    }

    public String getCari_Adi() {
        return Cari_Adi;
    }

    public void setCari_Adi(String cari_Adi) {
        Cari_Adi = cari_Adi;
    }
}
