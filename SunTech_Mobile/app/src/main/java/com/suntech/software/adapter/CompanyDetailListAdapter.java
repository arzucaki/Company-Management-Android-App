package com.suntech.software.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.suntech.software.R;
import com.suntech.software.model.HelpDeskRecord;

import java.util.ArrayList;

/**
 * Created by Ramazan on 11/8/2016.
 */
public class CompanyDetailListAdapter extends ArrayAdapter<HelpDeskRecord> {

    private final ArrayList<HelpDeskRecord> list;
    private final Activity context;
    private int[] colors = new int[]{0x34a4a4a4, 0x3fafafa};

    public CompanyDetailListAdapter(Activity context, ArrayList<HelpDeskRecord> list) {
        super(context, R.layout.company_detail_list_item, list);
        this.context = context;
        this.list = list;
    }

    static class ViewHolder {
        public TextView txtTarihSaat;
        public TextView txtOncelik;
        public TextView txtGrubuTipi;
        public TextView txtErrorCode;
        public TextView txtDurumu;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.company_detail_list_item, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.txtTarihSaat = (TextView) view.findViewById(R.id.txtTarihSaat);
            viewHolder.txtOncelik = (TextView) view.findViewById(R.id.txtOncelik);
            viewHolder.txtGrubuTipi = (TextView) view.findViewById(R.id.txtGrubuTipi);
            viewHolder.txtErrorCode = (TextView) view.findViewById(R.id.txtErrorCode);
            viewHolder.txtDurumu = (TextView) view.findViewById(R.id.txtDurumu);
            view.setTag(viewHolder);
            int colorPos = position % colors.length;
            view.setBackgroundColor(colors[colorPos]);
        } else {
            view = convertView;
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        String tarih = list.get(position).getTarih().substring(0, 10);
        tarih = tarih.substring(8, 10) + "." + tarih.substring(5, 7) + "." + tarih.substring(0, 4);
        String saat = list.get(position).getSaat().substring(0, 5);
        holder.txtTarihSaat.setText(tarih + " - " + saat);
        holder.txtOncelik.setText(list.get(position).getOncelik());
        holder.txtGrubuTipi.setText(list.get(position).getGrubu() + " / " + list.get(position).getTipi());
        holder.txtErrorCode.setText(list.get(position).getError_Code_Description());
        holder.txtDurumu.setText(list.get(position).getDurumu());

        return view;
    }

    @Override
    public int getViewTypeCount() {
        if (getCount() != 0)
            return getCount();

        return 1;
    }
}
