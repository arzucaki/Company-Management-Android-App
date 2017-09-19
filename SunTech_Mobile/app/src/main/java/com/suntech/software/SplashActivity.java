package com.suntech.software;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.suntech.software.model.StatusList;
import com.suntech.software.utils.Utils;

import org.json.JSONArray;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    private ArrayList<PropertyInfo> properties = new ArrayList<>();
    private static final int REQUEST_WRITE_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        boolean hasPermission = (ContextCompat.checkSelfPermission(SplashActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(SplashActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        } else {


            PropertyInfo propertyInfo1 = new PropertyInfo();
            properties.clear();


            propertyInfo1.setName("Connection_String");
            propertyInfo1.setType(String.class);
            propertyInfo1.setValue(WebService.CONNECTION_STRING);
            properties.add(propertyInfo1);

            propertyInfo1 = new PropertyInfo();
            propertyInfo1.setName("Status_Type");
            propertyInfo1.setType(String.class);
            propertyInfo1.setValue("0,1");
            properties.add(propertyInfo1);


            new AsyncTaskService().execute(new ServiceParams("GetHDRecordStatusList_JSON", properties));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PropertyInfo propertyInfo1 = new PropertyInfo();
                    properties.clear();


                    propertyInfo1.setName("Connection_String");
                    propertyInfo1.setType(String.class);
                    propertyInfo1.setValue(WebService.CONNECTION_STRING);
                    properties.add(propertyInfo1);

                    propertyInfo1 = new PropertyInfo();
                    propertyInfo1.setName("Status_Type");
                    propertyInfo1.setType(String.class);
                    propertyInfo1.setValue("0,1");
                    properties.add(propertyInfo1);


                    new AsyncTaskService().execute(new ServiceParams("GetHDRecordStatusList_JSON", properties));
                } else {
                    Toast.makeText(SplashActivity.this, "Uygulamayı kullanabilmeniz için yazma izni gereklidir.", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }

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

            if (resp.equals("Error occured")) {
                Toast.makeText(getApplicationContext(), "Bir hata meydana geldi!", Toast.LENGTH_SHORT).show();
                finish();
            }

            try {
                JSONArray jsonArray = new JSONArray(resp);
                Utils.statusArrayList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    Utils.statusArrayList.add(new StatusList(jsonArray.getJSONObject(i)));
                }
                finish();
                startActivityForResult(new Intent(SplashActivity.this, LoginActivity.class), 0);
            } catch (Exception e) {
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
