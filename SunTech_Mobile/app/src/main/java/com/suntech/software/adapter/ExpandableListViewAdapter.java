package com.suntech.software.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.suntech.software.R;
import com.suntech.software.utils.Utils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Ramazan on 13/8/2016.
 */
public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    public List<String> list_parent;
    public HashMap<String, List<String>> list_child;
    public Context context;
    public TextView txt;
    public TextView txtTarihSaat;
    public TextView txtKayitTipi;
    public TextView txtKayitDetay;
    public LayoutInflater inflater;
    public TextView txtGonderen;
    public TextView txtAlici;
    public TextView txtKonu;
    public TextView txtIcerik;

    @Override
    public int getGroupCount() {

        return list_parent.size();
    }

    public ExpandableListViewAdapter(Context context, List<String> list_parent, HashMap<String, List<String>> list_child) {
        this.context = context;
        this.list_parent = list_parent;
        this.list_child = list_child;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        if (groupPosition == 0)
            return Utils.eventDetailArrayList.size();
        else
            return Utils.emailDetailArrayList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {

        return list_parent.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return list_child.get(list_parent.get(groupPosition)).get(childPosition);

    }

    @Override
    public long getGroupId(int groupPosition) {

        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {

        return childPosition;
    }

    @Override
    public boolean hasStableIds() {

        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View view, ViewGroup parent) {
        String title_name = (String) getGroup(groupPosition);

        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.expandable_list_header, null);
        }

        txt = (TextView) view.findViewById(R.id.txtParent);
        txt.setText(title_name);

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View view, ViewGroup parent) {

        if (groupPosition == 0 && Utils.eventDetailArrayList.size() > 0) {
            if (view == null) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.expandable_list_event_item, null);
            }
            txtTarihSaat = (TextView) view.findViewById(R.id.txtTarihSaat);
            txtKayitTipi = (TextView) view.findViewById(R.id.txtKayitTipi);
            txtKayitDetay = (TextView) view.findViewById(R.id.txtKayitDetay);

            String tarih = Utils.eventDetailArrayList.get(childPosition).getTarih().substring(0, 10);
            tarih = tarih.substring(8, 10) + "." + tarih.substring(5, 7) + "." + tarih.substring(0, 4);
            String saat = Utils.eventDetailArrayList.get(childPosition).getSaat().substring(0, 5);
            txtTarihSaat.setText(tarih + " - " + saat);
            txtKayitTipi.setText(Utils.eventDetailArrayList.get(childPosition).getEvent_Detail_Type());
            txtKayitDetay.setText(Utils.eventDetailArrayList.get(childPosition).getEvent_Detail());

        } else if (groupPosition == 1 && Utils.emailDetailArrayList.size() > 0) {
            if (view == null) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.expandable_list_email_item, null);
            }
            txtTarihSaat = (TextView) view.findViewById(R.id.txtTarihSaat);
            txtGonderen = (TextView) view.findViewById(R.id.txtGonderen);
            txtAlici = (TextView) view.findViewById(R.id.txtAlici);
            txtKonu = (TextView) view.findViewById(R.id.txtKonu);
            txtIcerik = (TextView) view.findViewById(R.id.txtIcerik);

            String tarih = Utils.eventDetailArrayList.get(childPosition).getTarih().substring(0, 10);
            tarih = tarih.substring(8, 10) + "." + tarih.substring(5, 7) + "." + tarih.substring(0, 4);
            String saat = Utils.eventDetailArrayList.get(childPosition).getSaat().substring(0, 5);
            txtTarihSaat.setText(tarih + " - " + saat);
            txtGonderen.setText(Utils.emailDetailArrayList.get(childPosition).getEmaili_Gonderen_Adresi());
            txtAlici.setText(Utils.emailDetailArrayList.get(childPosition).getEmail_To());
            txtKonu.setText(Utils.emailDetailArrayList.get(childPosition).getEmail_Konusu());
            txtIcerik.setText(Utils.emailDetailArrayList.get(childPosition).getEmail_Icerigi());
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;
    }


}