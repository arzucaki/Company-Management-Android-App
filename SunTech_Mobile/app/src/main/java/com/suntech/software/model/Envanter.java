package com.suntech.software.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ramazan on 18/8/2016.
 */
public class Envanter {
    private int Kayit_id;
    private int Cihaz_Model_id;
    private String Cihaz_Marka;
    private String Cihaz_Model;
    private String Cihaz_Seri_no;
    private String Barcode_No;

    public Envanter(JSONObject jo) {
        try {
            setKayit_id(jo.getInt("Kayit_id"));
            setModel_id(jo.getInt("Cihaz_Model_id"));
            setCihaz_Marka(jo.getString("Cihaz_Marka"));
            setCihaz_Model(jo.getString("Cihaz_Model"));
            setCihaz_Seri_no(jo.getString("Cihaz_Seri_no"));
            setBarcode_No(jo.getString("Barcode_No"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public int getModel_id() {
        return Cihaz_Model_id;
    }

    public void setModel_id(int model_id) {
        Cihaz_Model_id = model_id;
    }

    public int getKayit_id() {
        return Kayit_id;
    }

    public void setKayit_id(int kayit_id) {
        Kayit_id = kayit_id;
    }

    public String getCihaz_Marka() {
        return Cihaz_Marka;
    }

    public void setCihaz_Marka(String cihaz_Marka) {
        Cihaz_Marka = cihaz_Marka;
    }

    public String getCihaz_Model() {
        return Cihaz_Model;
    }

    public void setCihaz_Model(String cihaz_Model) {
        Cihaz_Model = cihaz_Model;
    }

    public String getCihaz_Seri_no() {
        return Cihaz_Seri_no;
    }

    public void setCihaz_Seri_no(String cihaz_Seri_no) {
        Cihaz_Seri_no = cihaz_Seri_no;
    }

    public String getBarcode_No() {
        return Barcode_No;
    }

    public void setBarcode_No(String barcode_No) {
        Barcode_No = barcode_No;
    }
}
