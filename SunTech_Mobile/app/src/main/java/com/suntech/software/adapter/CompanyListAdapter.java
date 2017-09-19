package com.suntech.software.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.suntech.software.R;
import com.suntech.software.model.Company;

import java.util.ArrayList;

/**
 * Created by Ramazan on 11/8/2016.
 */
public class CompanyListAdapter extends ArrayAdapter<Company> {

    private final ArrayList<Company> list;
    private final Activity context;
    private int[] colors = new int[]{0x34a4a4a4, 0x34fafafa};

    public CompanyListAdapter(Activity context, ArrayList<Company> list) {
        super(context, R.layout.company_list_item, list);
        this.context = context;
        this.list = list;
    }

    static class ViewHolder {
        public TextView txtSirket;
        public TextView txtIsletme;
        public TextView txtDepartman;
        public TextView txtRec;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.company_list_item, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.txtSirket = (TextView) view.findViewById(R.id.txtSirket);
            viewHolder.txtIsletme = (TextView) view.findViewById(R.id.txtIsletme);
            viewHolder.txtDepartman = (TextView) view.findViewById(R.id.txtDepartman);
            viewHolder.txtRec = (TextView) view.findViewById(R.id.txtRecCount);
            view.setTag(viewHolder);
            int colorPos = position % colors.length;
            view.setBackgroundColor(colors[colorPos]);
        } else {
            view = convertView;
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.txtSirket.setText(list.get(position).getSirket());
        holder.txtIsletme.setText(list.get(position).getIsletme());
        holder.txtDepartman.setText(list.get(position).getJob_Department());
        holder.txtRec.setText("" + list.get(position).getRec_Count());


        return view;
    }

    @Override
    public int getViewTypeCount() {
        if (getCount() != 0)
            return getCount();

        return 1;
    }
}
