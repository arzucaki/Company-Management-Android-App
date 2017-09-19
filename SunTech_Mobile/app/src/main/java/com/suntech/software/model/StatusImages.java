package com.suntech.software.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ramazan on 16/8/2016.
 */
public class StatusImages {
    private int Kayit_id;
    private int Help_Desk_Kayit_id;
    private String Tarih;
    private String Saat;
    private Bitmap Photo;
    private Uri imagePath;
    private Context context;

    public StatusImages() {

    }

    public StatusImages(Context context, JSONObject jo) {
        this.context = context;
        try {
            setKayit_id(jo.getInt("Kayit_id"));
            setHelp_Desk_Kayit_id(jo.getInt("Help_Desk_Kayit_id"));
            setTarih(jo.getString("Tarih"));
            setSaat(jo.getString("Saat"));
            setPhoto(base64toBitmap(jo.getString("Photo")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private Bitmap base64toBitmap(String base64) {
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public Bitmap getPhoto() {
        return Photo;
    }

    public void setPhoto(Bitmap photo) {
        Photo = photo;
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

    public void setTarih(String Tarih) {
        this.Tarih = Tarih;
    }

    public int getHelp_Desk_Kayit_id() {
        return Help_Desk_Kayit_id;
    }

    public void setHelp_Desk_Kayit_id(int help_Desk_Kayit_id) {
        Help_Desk_Kayit_id = help_Desk_Kayit_id;
    }

    public int getKayit_id() {
        return Kayit_id;
    }

    public void setKayit_id(int kayit_id) {
        Kayit_id = kayit_id;
    }

    public Uri getImagePath() {
        return imagePath;
    }

    public void setImagePath(Uri imagePath) {
        this.imagePath = imagePath;
    }


}
