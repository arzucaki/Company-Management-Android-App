package com.suntech.software.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.suntech.software.R;
import com.suntech.software.ServiceParams;
import com.suntech.software.WebService;
import com.suntech.software.adapter.CompanyDetailListAdapter;
import com.suntech.software.model.HelpDeskRecord;
import com.suntech.software.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Ramazan on 11/8/2016.
 */
public class FragmentCompanyDetail extends Fragment {

    ListView listCompanyDetail;
    private ArrayList<PropertyInfo> properties = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_detail, container, false);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        listCompanyDetail = (ListView) view.findViewById(R.id.listCompanyDetail);
        listCompanyDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Utils.selectedHelpDeskRecord = Utils.helpDeskRecordsArrayList.get(i);
                FragmentHDRecordDetail fragment = new FragmentHDRecordDetail();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.replace(R.id.fragment1, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        setHDRecords();


        return view;

    }


    public void setHDRecords() {
        PropertyInfo propertyInfo1 = new PropertyInfo();
        properties.clear();

        Calendar cal = Calendar.getInstance();

        propertyInfo1.setName("Connection_String");
        propertyInfo1.setType(String.class);
        propertyInfo1.setValue(WebService.CONNECTION_STRING);
        properties.add(propertyInfo1);

        propertyInfo1 = new PropertyInfo();
        propertyInfo1.setName("KayitTarihi_Baslangic1");
        propertyInfo1.setType(String.class);
        propertyInfo1.setValue(cal.get(Calendar.DAY_OF_MONTH) + "." + (cal.get(Calendar.MONTH) + 1) + "." + (cal.get(Calendar.YEAR) - 1));
        properties.add(propertyInfo1);

        propertyInfo1 = new PropertyInfo();
        propertyInfo1.setName("KayitTarihi_Bitis1");
        propertyInfo1.setType(String.class);
        propertyInfo1.setValue(cal.get(Calendar.DAY_OF_MONTH) + "." + (cal.get(Calendar.MONTH) + 1) + "." + (cal.get(Calendar.YEAR)));
        properties.add(propertyInfo1);

        propertyInfo1 = new PropertyInfo();
        propertyInfo1.setName("User_id");
        propertyInfo1.setType(Integer.class);
        propertyInfo1.setValue(Utils.user.getUser_id());
        properties.add(propertyInfo1);

        propertyInfo1 = new PropertyInfo();
        propertyInfo1.setName("Cari_Kart_id");
        propertyInfo1.setType(Integer.class);
        propertyInfo1.setValue(Utils.cariKartId);
        properties.add(propertyInfo1);

        propertyInfo1 = new PropertyInfo();
        propertyInfo1.setName("Isletme_id");
        propertyInfo1.setType(Integer.class);
        propertyInfo1.setValue(Utils.isletmeId);
        properties.add(propertyInfo1);

        propertyInfo1 = new PropertyInfo();
        propertyInfo1.setName("Department_id");
        propertyInfo1.setType(Integer.class);
        propertyInfo1.setValue(Utils.departmentId);
        properties.add(propertyInfo1);

        if (Utils.selectedStatus == 0) {
            propertyInfo1 = new PropertyInfo();
            propertyInfo1.setName("Status_Type");
            propertyInfo1.setType(String.class);
            propertyInfo1.setValue(Utils.getDevamEdenIslerStatus());
            properties.add(propertyInfo1);
        }
        if (Utils.selectedStatus == 1) {
            propertyInfo1 = new PropertyInfo();
            propertyInfo1.setName("Status_Type");
            propertyInfo1.setType(String.class);
            propertyInfo1.setValue(Utils.getTamamlananIslerStatus());
            properties.add(propertyInfo1);
        }
        new AsyncTaskService().execute(new ServiceParams("GetHDMainRecordByCompanyDetail", properties));
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
                Utils.helpDeskRecordsArrayList.clear();
                for (int i = 0; i < ja.length(); i++) {
                    Utils.helpDeskRecordsArrayList.add(new HelpDeskRecord(ja.getJSONObject(i)));
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listCompanyDetail.setAdapter(new CompanyDetailListAdapter(getActivity(), Utils.helpDeskRecordsArrayList));
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
