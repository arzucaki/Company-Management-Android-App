package com.suntech.software.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.suntech.software.R;
import com.suntech.software.model.Auth_Company;

import java.util.ArrayList;

/**
 * Created by ramazangunes on 26.05.2017.
 */

public class Auth_CompanyListAdapter extends ArrayAdapter<Auth_Company>  {

    private final ArrayList<Auth_Company> list;
    private final Activity context;
    private int[] colors = new int[]{0x34a4a4a4, 0x34fafafa};

    public Auth_CompanyListAdapter(Activity context, ArrayList<Auth_Company> list) {
        super(context, R.layout.auth_company_list_item, list);
        this.context = context;
        this.list = list;
    }
    static class ViewHolder {
        public TextView txtCompany;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.auth_company_list_item, null);
            final Auth_CompanyListAdapter.ViewHolder viewHolder = new Auth_CompanyListAdapter.ViewHolder();
            viewHolder.txtCompany = (TextView) view.findViewById(R.id.txtCompany);
            view.setTag(viewHolder);
            int colorPos = position % colors.length;
            view.setBackgroundColor(colors[colorPos]);
        } else {
            view = convertView;
        }
        Auth_CompanyListAdapter.ViewHolder holder = (Auth_CompanyListAdapter.ViewHolder) view.getTag();
        holder.txtCompany.setText(list.get(position).getCompany_Name());


        return view;
    }

    @Override
    public int getViewTypeCount() {
        if (getCount() != 0)
            return getCount();

        return 1;
    }
}
