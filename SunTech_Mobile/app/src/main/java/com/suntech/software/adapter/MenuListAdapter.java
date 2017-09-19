package com.suntech.software.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.suntech.software.R;
import com.suntech.software.model.Auth_Company;
import com.suntech.software.model.Model_AnaMenuList;

import java.util.ArrayList;

/**
 * Created by ramazangunes on 26.05.2017.
 */

public class MenuListAdapter extends ArrayAdapter<Model_AnaMenuList>  {

    private final ArrayList<Model_AnaMenuList> list;
    private final Activity context;
    private int[] colors = new int[]{0x34a4a4a4, 0x34fafafa};

    public MenuListAdapter(Activity context, ArrayList<Model_AnaMenuList> list) {
        super(context, R.layout.menu_list_item, list);
        this.context = context;
        this.list = list;
    }
    static class ViewHolder {
        public TextView txtMenuName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.menu_list_item, null);
            final MenuListAdapter.ViewHolder viewHolder = new MenuListAdapter.ViewHolder();
            viewHolder.txtMenuName = (TextView) view.findViewById(R.id.txtMenuName);
            view.setTag(viewHolder);
            int colorPos = position % colors.length;
            view.setBackgroundColor(colors[colorPos]);
        } else {
            view = convertView;
        }
        MenuListAdapter.ViewHolder holder = (MenuListAdapter.ViewHolder) view.getTag();
        holder.txtMenuName.setText(list.get(position).getAna_Menu());


        return view;
    }

    @Override
    public int getViewTypeCount() {
        if (getCount() != 0)
            return getCount();

        return 1;
    }
}
