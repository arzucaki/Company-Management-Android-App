package com.suntech.software.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.suntech.software.MainActivity;
import com.suntech.software.MainMenu;
import com.suntech.software.R;
import com.suntech.software.ServiceParams;
import com.suntech.software.WebService;
import com.suntech.software.adapter.Auth_CompanyListAdapter;
import com.suntech.software.model.Auth_Company;
import com.suntech.software.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_Auth_Company.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_Auth_Company#newInstance} factory method to
 * create an instance of this fragment.
 */

/**
 * Created by Ramazan on 11/8/2016.
 */
public class Fragment_Auth_Company  extends android.app.Fragment {

    ListView listCompany;
    private ArrayList<PropertyInfo> properties = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__auth__company, container, false);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        listCompany = (ListView) view.findViewById(R.id.listCompanyAuth);
        listCompany.setAdapter(new Auth_CompanyListAdapter(getActivity(), Utils.Auth_companyArrayList));
        listCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Utils.Company_id_=Utils.Auth_companyArrayList.get(i).getCompany_id();

                startActivityForResult(new Intent(getActivity(), MainMenu.class), 0);

            }
        });


        PropertyInfo propertyInfo1 = new PropertyInfo();
        properties.clear();

        Calendar cal = Calendar.getInstance();

        propertyInfo1.setName("Connection_String");
        propertyInfo1.setType(String.class);
        propertyInfo1.setValue(WebService.CONNECTION_STRING);
        properties.add(propertyInfo1);

        propertyInfo1 = new PropertyInfo();
        propertyInfo1.setName("User_id");
        propertyInfo1.setType(Integer.class);
        propertyInfo1.setValue(Utils.user.getUser_id());
        properties.add(propertyInfo1);

        new AsyncTaskService().execute(new ServiceParams("Get_AuthCompany_List", properties));

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
                Utils.Auth_companyArrayList.clear();
                for (int i = 0; i < ja.length(); i++) {
                    Utils.Auth_companyArrayList.add(new Auth_Company(ja.getJSONObject(i)));
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listCompany.setAdapter(new Auth_CompanyListAdapter(getActivity(), Utils.Auth_companyArrayList));
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

}