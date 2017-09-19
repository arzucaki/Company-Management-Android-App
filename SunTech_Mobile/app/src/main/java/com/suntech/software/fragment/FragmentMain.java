package com.suntech.software.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.suntech.software.MainActivity;
import com.suntech.software.R;
import com.suntech.software.ServiceParams;
import com.suntech.software.WebService;
import com.suntech.software.adapter.Auth_CompanyListAdapter;
import com.suntech.software.adapter.MenuListAdapter;
import com.suntech.software.model.Auth_Company;
import com.suntech.software.model.CompanyList;
import com.suntech.software.model.Department;
import com.suntech.software.model.Model_AnaMenuList;
import com.suntech.software.model.Priority;
import com.suntech.software.model.RecordType;
import com.suntech.software.model.Source;
import com.suntech.software.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Ramazan on 11/8/2016.
 */
public class FragmentMain extends Fragment {


    private ArrayList<PropertyInfo> properties2 = new ArrayList<>();

    ListView listAnaMenu;
    private ArrayList<PropertyInfo> properties = new ArrayList<>();

    public FragmentMain() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        listAnaMenu = (ListView) view.findViewById(R.id.list_AnaMenuList);
        listAnaMenu.setAdapter(new MenuListAdapter(getActivity(), Utils.AnaMenu_ArrayList));
        listAnaMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Utils.AnaMenu_id=Utils.AnaMenu_ArrayList.get(i).getAna_Menu_id();

                if (Utils.AnaMenu_id==5)
                {
                    PropertyInfo propertyInfo1 = new PropertyInfo();
                    properties2.clear();

                    propertyInfo1.setName("Connection_String");
                    propertyInfo1.setType(String.class);
                    propertyInfo1.setValue(WebService.CONNECTION_STRING);
                    properties2.add(propertyInfo1);

                    propertyInfo1 = new PropertyInfo();
                    propertyInfo1.setName("User_id");
                    propertyInfo1.setType(Integer.class);
                    propertyInfo1.setValue(Utils.user.getUser_id());
                    properties2.add(propertyInfo1);

                    new AsyncTaskService2().execute(new ServiceParams("GetHDConstantParameters_JSON", properties2));

                }
                else if (Utils.AnaMenu_id==6)
                {
                    FragmentCompany fragment = new FragmentCompany();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    fragmentTransaction.replace(R.id.fragment1, fragment).addToBackStack(null);
                    fragmentTransaction.commit();
                }

            }
        });


        PropertyInfo propertyInfo1 = new PropertyInfo();
        properties.clear();

        propertyInfo1.setName("Connection_String");
        propertyInfo1.setType(String.class);
        propertyInfo1.setValue(WebService.CONNECTION_STRING);
        properties.add(propertyInfo1);

        propertyInfo1 = new PropertyInfo();
        propertyInfo1.setName("Group_id");
        propertyInfo1.setType(Integer.class);
        propertyInfo1.setValue(Utils.Group_id);
        properties.add(propertyInfo1);

        new FragmentMain.AsyncTaskService().execute(new ServiceParams("Get_MenuList_JSON", properties));

        return view;
    }

    public class AsyncTaskService extends AsyncTask<ServiceParams, Void, Void> {
        String resp = "";
        ProgressDialog progressDialog;

        @Override
        protected Void doInBackground(ServiceParams... params) {
            resp = WebService.invoke(params[0].properties, params[0].methodName);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            Log.w("WEBSERVICE RESPONSE===", resp);

            try {
                JSONArray ja = new JSONArray(resp);
                Utils.AnaMenu_ArrayList.clear();
                for (int i = 0; i < ja.length(); i++) {
                    Utils.AnaMenu_ArrayList.add(new Model_AnaMenuList(ja.getJSONObject(i)));
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listAnaMenu.setAdapter(new MenuListAdapter(getActivity(), Utils.AnaMenu_ArrayList));
                    }
                });


            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (progressDialog != null)
                progressDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(getActivity());
            if (progressDialog != null) {
                progressDialog.setCancelable(false);
                progressDialog.setMessage("İşlem yapılıyor ...");
                progressDialog.show();
            }

        }

        protected void onProgressUpdate(Integer... progress) {
            if (progressDialog != null)
                progressDialog.setProgress(progress[0]);
        }

    }

    public class AsyncTaskService2 extends AsyncTask<ServiceParams, Void, Void> {
        String resp = "";
        ProgressDialog progressDialog;

        @Override
        protected Void doInBackground(ServiceParams... params) {
            resp = WebService.invoke(params[0].properties, params[0].methodName);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            Log.w("WEBSERVICE RESPONSE===", resp);

            try {
                JSONObject jo = new JSONObject(resp);
                Utils.sourceArrayList.clear();
                Utils.recordTypeArrayList.clear();
                Utils.priorityArrayList.clear();
                Utils.companyListArrayList.clear();
                Utils.departmentArrayList.clear();

                for (int i = 0; i < jo.getJSONArray("Source").length(); i++) {
                    Utils.sourceArrayList.add(new Source(jo.getJSONArray("Source").getJSONObject(i)));
                }
                for (int i = 0; i < jo.getJSONArray("RecordType").length(); i++) {
                    Utils.recordTypeArrayList.add(new RecordType(jo.getJSONArray("RecordType").getJSONObject(i)));
                }
                for (int i = 0; i < jo.getJSONArray("Priority").length(); i++) {
                    Utils.priorityArrayList.add(new Priority(jo.getJSONArray("Priority").getJSONObject(i)));
                }
                for (int i = 0; i < jo.getJSONArray("CompanyList").length(); i++) {
                    Utils.companyListArrayList.add(new CompanyList(jo.getJSONArray("CompanyList").getJSONObject(i)));
                }
                for (int i = 0; i < jo.getJSONArray("DepartmentList").length(); i++) {
                    Utils.departmentArrayList.add(new Department(jo.getJSONArray("DepartmentList").getJSONObject(i)));
                }

                FragmentAddNew fragment = new FragmentAddNew();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.replace(R.id.fragment1, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            if (progressDialog != null)
                progressDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {

            if (progressDialog != null)
                progressDialog.setCancelable(false);

        }

        protected void onProgressUpdate(Integer... progress) {
            if (progressDialog != null)
                progressDialog.setProgress(progress[0]);
        }

    }


}
