package com.suntech.software.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ramazan on 11/8/2016.
 */
public class HelpDeskRecord {

    private int Kayit_id;
    private String Tarih;
    private String Saat;
    private String Oncelik;
    private String Durumu;
    private String Grubu;
    private String Tipi;
    private String Error_Code_Description;
    private int KayitOnceki;
    private int KayitSonraki;

    public HelpDeskRecord() {

    }

    public HelpDeskRecord(JSONObject jo) {
        try {
            setKayit_id(jo.getInt("Kayit_id"));
            setTarih(jo.getString("Tarih"));
            setSaat(jo.getString("Saat"));
            setOncelik(jo.getString("Oncelik"));
            setDurumu(jo.getString("Durumu"));
            setGrubu(jo.getString("Grubu"));
            setTipi(jo.getString("Tipi"));
            setError_Code_Description(jo.getString("Error_Code_Description"));
            setKayitOnceki(jo.getInt("KayitOnceki"));
            setKayitSonraki(jo.getInt("KayitSonraki"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getError_Code_Description() {
        return Error_Code_Description;
    }

    public void setError_Code_Description(String error_Code_Description) {
        Error_Code_Description = error_Code_Description;
    }

    public String getTipi() {
        return Tipi;
    }

    public void setTipi(String tipi) {
        Tipi = tipi;
    }

    public String getGrubu() {
        return Grubu;
    }

    public void setGrubu(String grubu) {
        Grubu = grubu;
    }

    public String getDurumu() {
        return Durumu;
    }

    public void setDurumu(String durumu) {
        Durumu = durumu;
    }

    public String getOncelik() {
        return Oncelik;
    }

    public void setOncelik(String oncelik) {
        Oncelik = oncelik;
    }

    public String getSaat() {
        return Saat;
    }

    public void setSaat(String saat) {
        Saat = saat;
    }

    public String getTarih() {
        return Tarih;
    }

    public void setTarih(String tarih) {
        Tarih = tarih;
    }

    public int getKayit_id() {
        return Kayit_id;
    }

    public void setKayit_id(int kayit_id) {
        Kayit_id = kayit_id;
    }

    public int getKayitSonraki() {
        return KayitSonraki;
    }

    public void setKayitSonraki(int kayitSonraki) {
        KayitSonraki = kayitSonraki;
    }

    public int getKayitOnceki() {
        return KayitOnceki;
    }

    public void setKayitOnceki(int kayitOnceki) {
        KayitOnceki = kayitOnceki;
    }


}
