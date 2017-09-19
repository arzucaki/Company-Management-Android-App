package com.suntech.software.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ramazan on 13/8/2016.
 */
public class EmailDetail {
    private String Tarih;
    private String Saat;
    private String Email_Konusu;
    private String Email_Icerigi;
    private String Emaili_Gonderen_Adresi;
    private String Email_To;

    public EmailDetail(JSONObject jo) {
        try {
            setTarih(jo.getString("Tarih"));
            setSaat(jo.getString("Saat"));
            setEmail_Konusu(jo.getString("Email_Konusu"));
            setEmail_Icerigi(jo.getString("Email_Icerigi"));
            setEmaili_Gonderen_Adresi(jo.getString("Emaili_Gonderen_Adresi"));
            setEmail_To(jo.getString("Email_To"));

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

    public String getEmail_Konusu() {
        return Email_Konusu;
    }

    public void setEmail_Konusu(String email_Konusu) {
        Email_Konusu = email_Konusu;
    }

    public String getEmail_Icerigi() {
        return Email_Icerigi;
    }

    public void setEmail_Icerigi(String email_Icerigi) {
        Email_Icerigi = email_Icerigi;
    }

    public String getEmail_To() {
        return Email_To;
    }

    public void setEmail_To(String email_To) {
        Email_To = email_To;
    }

    public String getEmaili_Gonderen_Adresi() {
        return Emaili_Gonderen_Adresi;
    }

    public void setEmaili_Gonderen_Adresi(String emaili_Gonderen_Adresi) {
        Emaili_Gonderen_Adresi = emaili_Gonderen_Adresi;
    }
}
