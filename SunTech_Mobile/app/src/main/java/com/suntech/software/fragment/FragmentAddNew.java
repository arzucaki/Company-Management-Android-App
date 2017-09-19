package com.suntech.software.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.suntech.software.R;
import com.suntech.software.ServiceParams;
import com.suntech.software.WebService;
import com.suntech.software.adapter.CropOption;
import com.suntech.software.adapter.CropOptionAdapter;
import com.suntech.software.adapter.ImageListAdapter;
import com.suntech.software.model.CariKart;
import com.suntech.software.model.CariKartSube;
import com.suntech.software.model.Envanter;
import com.suntech.software.model.ErrorCode;
import com.suntech.software.model.StatusImages;
import com.suntech.software.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.ksoap2.serialization.PropertyInfo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ramazan on 11/8/2016.
 */
public class FragmentAddNew extends Fragment {

    Spinner spRecordType, spPriority,
            spCariKart, spCariKartSube, spEnvanter, spErrorCode,
            spDepartman;

    private ArrayList<PropertyInfo> properties = new ArrayList<>();

    //List<String> sourceList = new ArrayList<String>();
    List<String> recordTypeList = new ArrayList<String>();
    List<String> priorityList = new ArrayList<String>();
    // List<String> companyList = new ArrayList<String>();
    List<String> cariKartList = new ArrayList<String>();
    List<String> cariKartSubeList = new ArrayList<String>();
    List<String> envanterList = new ArrayList<String>();
    List<String> errorCodeList = new ArrayList<String>();
    List<String> departmentList = new ArrayList<String>();

    Button btnGuncelle;
    EditText etNotes;

    String task = "";

    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int CAMERA_REQUEST_CODE = 3;
    private Uri mImageCaptureUri;
    private Uri mImageCaptureUri2;
    Button btnFotografEkle;
    ArrayList<Uri> imageUriListOnceki = new ArrayList<>();
    ArrayList<String> base64imagesOnceki = new ArrayList<>();
    private RecyclerView recyclerViewOnceki;
    private ImageListAdapter mAdapterOnceki;
    private int kayit_id = 0;

    public FragmentAddNew() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new, container, false);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        //spSource = (Spinner) view.findViewById(R.id.spSource);
        spRecordType = (Spinner) view.findViewById(R.id.spRecordType);
        spPriority = (Spinner) view.findViewById(R.id.spPriority);
      //  spCompanyList = (Spinner) view.findViewById(R.id.spCompanyList);
        spCariKart = (Spinner) view.findViewById(R.id.spCariKart);
        spCariKartSube = (Spinner) view.findViewById(R.id.spCariKartSube);
        spEnvanter = (Spinner) view.findViewById(R.id.spEnvanter);
        spErrorCode = (Spinner) view.findViewById(R.id.spErrorCode);
        btnGuncelle = (Button) view.findViewById(R.id.btnGuncelle);
        etNotes = (EditText) view.findViewById(R.id.etNotes);
        spDepartman = (Spinner) view.findViewById(R.id.spDepartmen);

        Utils.statusImagesArrayListOnceki.clear();
        btnFotografEkle = (Button) view.findViewById(R.id.btnFotografEkle);
        btnFotografEkle.setOnClickListener(new CaptureImageClickListener());
        recyclerViewOnceki = (RecyclerView) view.findViewById(R.id.recyclerList);
        mAdapterOnceki = new ImageListAdapter(getActivity(), Utils.statusImagesArrayListOnceki);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewOnceki.setLayoutManager(layoutManager);
        recyclerViewOnceki.setItemAnimator(new DefaultItemAnimator());
        recyclerViewOnceki.setAdapter(mAdapterOnceki);

        recyclerViewOnceki.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerViewOnceki, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Toast.makeText(getActivity(), position + " is clicked!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                File file = new File(Utils.statusImagesArrayListOnceki.get(position).getImagePath().getPath());
                intent.setDataAndType(Uri.fromFile(file), "image/*");
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                //Toast.makeText(getActivity(), position + " is long-clicked!", Toast.LENGTH_SHORT).show();
            }
        }));

        //sourceList.clear();
        recordTypeList.clear();
        priorityList.clear();
        // companyList.clear();
        cariKartList.clear();
        cariKartSubeList.clear();
        envanterList.clear();
        errorCodeList.clear();
        departmentList.clear();

        for (int i = 0; i < Utils.departmentArrayList.size(); i++) {
            departmentList.add(Utils.departmentArrayList.get(i).getDepartman_Adi());
        }
        ArrayAdapter<String> dataAdapter0 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, departmentList);
        dataAdapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDepartman.setAdapter(dataAdapter0);

        /*
        for (int i = 0; i < Utils.sourceArrayList.size(); i++) {
            sourceList.add(Utils.sourceArrayList.get(i).getRecord_Source());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, sourceList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSource.setAdapter(dataAdapter);
        */

        for (int i = 0; i < Utils.recordTypeArrayList.size(); i++) {
            recordTypeList.add(Utils.recordTypeArrayList.get(i).getRecord_Type());
        }
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, recordTypeList);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRecordType.setAdapter(dataAdapter2);

        for (int i = 0; i < Utils.priorityArrayList.size(); i++) {
            priorityList.add(Utils.priorityArrayList.get(i).getPriority());
        }
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, priorityList);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPriority.setAdapter(dataAdapter3);

        /*
        for (int i = 0; i < Utils.companyListArrayList.size(); i++) {
            companyList.add(Utils.companyListArrayList.get(i).getCompany_Name());
        }
        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, companyList);
        dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCompanyList.setAdapter(dataAdapter4);
        */

        PropertyInfo propertyInfo1 = new PropertyInfo();
        properties.clear();
        propertyInfo1.setName("Connection_String");
        propertyInfo1.setType(String.class);
        propertyInfo1.setValue(WebService.CONNECTION_STRING);
        properties.add(propertyInfo1);

        propertyInfo1 = new PropertyInfo();
        propertyInfo1.setName("Company_id");
        propertyInfo1.setType(Integer.class);
        propertyInfo1.setValue(Utils.Company_id_);
        properties.add(propertyInfo1);

        propertyInfo1 = new PropertyInfo();
        propertyInfo1.setName("User_id");
        propertyInfo1.setType(Integer.class);
        propertyInfo1.setValue(Utils.user.getUser_id());
        properties.add(propertyInfo1);

        task = "carikart";
        new AsyncTaskService().execute(new ServiceParams("GetHDCariKartlistesi", properties));


        spCariKart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                PropertyInfo propertyInfo1 = new PropertyInfo();
                properties.clear();
                int pos = i - 1;
                if (i != 0 && pos < Utils.cariKartArrayList.size()) {
                    propertyInfo1.setName("Connection_String");
                    propertyInfo1.setType(String.class);
                    propertyInfo1.setValue(WebService.CONNECTION_STRING);
                    properties.add(propertyInfo1);

                    propertyInfo1 = new PropertyInfo();
                    propertyInfo1.setName("CariKart_id");
                    propertyInfo1.setType(Integer.class);
                    propertyInfo1.setValue(Utils.cariKartArrayList.get(pos).getCari_Kart_id());
                    properties.add(propertyInfo1);

                    propertyInfo1 = new PropertyInfo();
                    propertyInfo1.setName("User_id");
                    propertyInfo1.setType(Integer.class);
                    propertyInfo1.setValue(Utils.user.getUser_id());
                    properties.add(propertyInfo1);

                    task = "carikartsube";
                    new AsyncTaskService().execute(new ServiceParams("GetHDCariKartSubelistesi", properties));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spCariKartSube.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                PropertyInfo propertyInfo1 = new PropertyInfo();
                properties.clear();
                int pos = i - 1;
                if (i != 0) {
                    propertyInfo1.setName("Connection_String");
                    propertyInfo1.setType(String.class);
                    propertyInfo1.setValue(WebService.CONNECTION_STRING);
                    properties.add(propertyInfo1);

                    propertyInfo1 = new PropertyInfo();
                    propertyInfo1.setName("CariSube_id");
                    propertyInfo1.setType(Integer.class);
                    propertyInfo1.setValue(Utils.cariKartSubeArrayList.get(pos).getIsletme_id());
                    properties.add(propertyInfo1);

                    task = "envanter";
                    new AsyncTaskService().execute(new ServiceParams("GetHDInventoryList_ByCariSube", properties));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spEnvanter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    PropertyInfo propertyInfo1 = new PropertyInfo();
                    properties.clear();
                    int pos = i - 1;
                    propertyInfo1.setName("Connection_String");
                    propertyInfo1.setType(String.class);
                    propertyInfo1.setValue(WebService.CONNECTION_STRING);
                    properties.add(propertyInfo1);

                    propertyInfo1 = new PropertyInfo();
                    propertyInfo1.setName("Cihaz_Model_id");
                    propertyInfo1.setType(Integer.class);
                    propertyInfo1.setValue(Utils.envanterArrayList.get(pos).getModel_id());
                    properties.add(propertyInfo1);

                    task = "errorcode";
                    new AsyncTaskService().execute(new ServiceParams("GetHDErrorCodesByModelId", properties));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PropertyInfo propertyInfo1 = new PropertyInfo();
                properties.clear();
                propertyInfo1.setName("Connection_String");
                propertyInfo1.setType(String.class);
                propertyInfo1.setValue(WebService.CONNECTION_STRING);
                properties.add(propertyInfo1);

                propertyInfo1 = new PropertyInfo();
                propertyInfo1.setName("Job_Department_id");
                propertyInfo1.setType(Integer.class);
                propertyInfo1.setValue(Utils.departmentArrayList.get(spDepartman.getSelectedItemPosition()).getKayit_id());
                properties.add(propertyInfo1);

                propertyInfo1 = new PropertyInfo();
                propertyInfo1.setName("Kaynak_id");
                propertyInfo1.setType(Integer.class);
                propertyInfo1.setValue(2);
                properties.add(propertyInfo1);

                propertyInfo1 = new PropertyInfo();
                propertyInfo1.setName("Record_Type_id");
                propertyInfo1.setType(Integer.class);
                propertyInfo1.setValue(Utils.recordTypeArrayList.get(spRecordType.getSelectedItemPosition()).getHD_Record_Type_id());
                properties.add(propertyInfo1);

                propertyInfo1 = new PropertyInfo();
                propertyInfo1.setName("CP_Per_Card_id");
                propertyInfo1.setType(Integer.class);
                propertyInfo1.setValue(Utils.user.getUser_id());
                properties.add(propertyInfo1);

                if (spEnvanter.getSelectedItemPosition() > 0) {
                    propertyInfo1 = new PropertyInfo();
                    propertyInfo1.setName("Cihaz_Kart_id");
                    propertyInfo1.setType(Integer.class);
                    propertyInfo1.setValue(Utils.envanterArrayList.get(spEnvanter.getSelectedItemPosition() - 1).getKayit_id());
                    properties.add(propertyInfo1);
                }

                propertyInfo1 = new PropertyInfo();
                propertyInfo1.setName("HD_User_id");
                propertyInfo1.setType(Integer.class);
                propertyInfo1.setValue(Utils.user.getUser_id());
                properties.add(propertyInfo1);

                if (spCariKart.getSelectedItemPosition() > 0) {
                    propertyInfo1 = new PropertyInfo();
                    propertyInfo1.setName("Cari_Kart_id");
                    propertyInfo1.setType(Integer.class);
                    propertyInfo1.setValue(Utils.cariKartArrayList.get(spCariKart.getSelectedItemPosition() - 1).getCari_Kart_id());
                    properties.add(propertyInfo1);
                }

                if (spCariKartSube.getSelectedItemPosition() > 0) {
                    propertyInfo1 = new PropertyInfo();
                    propertyInfo1.setName("Isletme_id");
                    propertyInfo1.setType(Integer.class);
                    propertyInfo1.setValue(Utils.cariKartSubeArrayList.get(spCariKartSube.getSelectedItemPosition() - 1).getIsletme_id());
                    properties.add(propertyInfo1);
                }

                propertyInfo1 = new PropertyInfo();
                propertyInfo1.setName("Priority_id");
                propertyInfo1.setType(Integer.class);
                propertyInfo1.setValue(Utils.priorityArrayList.get(spPriority.getSelectedItemPosition()).getPriority_id());
                properties.add(propertyInfo1);

                propertyInfo1 = new PropertyInfo();
                propertyInfo1.setName("Company_id");
                propertyInfo1.setType(Integer.class);
                propertyInfo1.setValue(Utils.Company_id_);
                properties.add(propertyInfo1);

                if (spErrorCode.getSelectedItemPosition() >= 0) {
                    propertyInfo1 = new PropertyInfo();
                    propertyInfo1.setName("Hata_Kodu_id");
                    propertyInfo1.setType(Integer.class);
                    propertyInfo1.setValue(Utils.errorCodeArrayList.get(spErrorCode.getSelectedItemPosition()).getError_Code_id());
                    properties.add(propertyInfo1);
                }

                propertyInfo1 = new PropertyInfo();
                propertyInfo1.setName("Event_Detail_Text");
                propertyInfo1.setType(String.class);
                propertyInfo1.setValue("" + etNotes.getText().toString());
                properties.add(propertyInfo1);

                new PostRecordAsyncTaskService().execute(new ServiceParams("Add_HDMainRecord", properties));
            }
        });


        return view;

    }
    /*
    ************************************************************************
    * ************** RESİMLER GÖSTERİLİYOR.
    */

    private class CaptureImageClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            mImageCaptureUri = Uri.fromFile(new File(Environment
                    .getExternalStorageDirectory(), "tmp_avatar_"
                    + String.valueOf(System.currentTimeMillis()) + ".jpg"));

            intent.putExtra(
                    android.provider.MediaStore.EXTRA_SCREEN_ORIENTATION,
                    ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
                    mImageCaptureUri);
            try {
                intent.putExtra("outputX", 800);
                intent.putExtra("outputY", 800);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("return-data", true);

                if (android.os.Build.VERSION.SDK_INT >= 23 &&
                        getActivity().checkSelfPermission(Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            CAMERA_REQUEST_CODE);
                } else
                    startActivityForResult(intent, PICK_FROM_CAMERA);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
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

            if (task.equals("carikart")) {
                try {
                    JSONArray ja = new JSONArray(resp);
                    Utils.cariKartArrayList.clear();
                    cariKartList.clear();
                    cariKartList.add("Seçiniz");
                    for (int i = 0; i < ja.length(); i++) {
                        Utils.cariKartArrayList.add(new CariKart(ja.getJSONObject(i)));
                        cariKartList.add(ja.getJSONObject(i).getString("Cari_Adi"));
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, cariKartList);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spCariKart.setAdapter(dataAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (task.equals("carikartsube")) {
                try {
                    JSONArray ja = new JSONArray(resp);
                    Utils.cariKartSubeArrayList.clear();
                    cariKartSubeList.clear();
                    cariKartSubeList.add("Seçiniz");
                    for (int i = 0; i < ja.length(); i++) {
                        Utils.cariKartSubeArrayList.add(new CariKartSube(ja.getJSONObject(i)));
                        cariKartSubeList.add(ja.getJSONObject(i).getString("Isletme_Adi"));
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, cariKartSubeList);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spCariKartSube.setAdapter(dataAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (task.equals("envanter")) {
                try {
                    JSONArray ja = new JSONArray(resp);
                    Utils.envanterArrayList.clear();
                    envanterList.clear();
                    envanterList.add("Seçiniz");
                    for (int i = 0; i < ja.length(); i++) {
                        Utils.envanterArrayList.add(new Envanter(ja.getJSONObject(i)));
                        envanterList.add(ja.getJSONObject(i).getString("Cihaz_Marka") + " " + ja.getJSONObject(i).getString("Cihaz_Model") + " "
                                + ((ja.getJSONObject(i).getString("Barcode_No").length() > 0 && !ja.getJSONObject(i).isNull("Barcode_No")) ? ja.getJSONObject(i).getString("Barcode_No") : ja.getJSONObject(i).getString("Cihaz_Seri_no")));
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, envanterList);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spEnvanter.setAdapter(dataAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (task.equals("errorcode")) {
                try {
                    JSONArray ja = new JSONArray(resp);
                    Utils.errorCodeArrayList.clear();
                    errorCodeList.clear();
                    errorCodeList.add("Seçiniz");
                    for (int i = 0; i < ja.length(); i++) {
                        if (ja.getJSONObject(i).isNull("Error_Code_Description"))
                            continue;
                        Utils.errorCodeArrayList.add(new ErrorCode(ja.getJSONObject(i)));
                        errorCodeList.add(ja.getJSONObject(i).getString("Error_Code_Description"));
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, errorCodeList);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spErrorCode.setAdapter(dataAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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


    public class PostRecordAsyncTaskService extends AsyncTask<ServiceParams, Void, Void> {
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

            if (resp.equals("Error occured"))
                Toast.makeText(getActivity(), "Bir hata meydana geldi!", Toast.LENGTH_SHORT).show();
            else {
                if (base64imagesOnceki.size() > 0) {
                    kayit_id = Integer.valueOf(resp);
                    new PostRecordImagesAsyncTaskService().execute();
                } else {
                    getActivity().getFragmentManager().popBackStack();
                    Toast.makeText(getActivity(), "Kayıt gönderildi!", Toast.LENGTH_SHORT).show();
                }
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

    public class PostRecordImagesAsyncTaskService extends AsyncTask<ServiceParams, Void, Void> {
        String resp = "";
        ProgressDialog progressDialog;

        @Override
        protected Void doInBackground(ServiceParams... params) {
            for (int i = 0; i < base64imagesOnceki.size(); i++) {
                resp = WebService.invoke(setStatusImages(0, i, kayit_id), "SetProcessStatusImage");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            Log.w("WEBSERVICE RESPONSE===", resp);

            if (resp.equals("Error occured"))
                Toast.makeText(getActivity(), "Bir hata meydana geldi!", Toast.LENGTH_SHORT).show();
            else {
                getActivity().getFragmentManager().popBackStack();
                Toast.makeText(getActivity(), "Kayıt gönderildi!", Toast.LENGTH_SHORT).show();
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

    public ArrayList<PropertyInfo> setStatusImages(int status, int position, int kayit_id) {

        PropertyInfo propertyInfo1 = new PropertyInfo();
        properties.clear();

        propertyInfo1.setName("Connection_String");
        propertyInfo1.setType(String.class);
        propertyInfo1.setValue(WebService.CONNECTION_STRING);
        properties.add(propertyInfo1);

        propertyInfo1 = new PropertyInfo();
        propertyInfo1.setName("Help_Desk_Kayit_id");
        propertyInfo1.setType(Integer.class);
        propertyInfo1.setValue(kayit_id);
        properties.add(propertyInfo1);

        propertyInfo1 = new PropertyInfo();
        propertyInfo1.setName("IslemDurumu");
        propertyInfo1.setType(Integer.class);
        propertyInfo1.setValue(status);
        properties.add(propertyInfo1);

        propertyInfo1 = new PropertyInfo();
        propertyInfo1.setName("Photo");
        propertyInfo1.setType(String.class);
        propertyInfo1.setValue(base64imagesOnceki.get(position));
        properties.add(propertyInfo1);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));

        propertyInfo1 = new PropertyInfo();
        propertyInfo1.setName("TarihSaat");
        propertyInfo1.setType(String.class);
        propertyInfo1.setValue(dateFormat.format(date));
        properties.add(propertyInfo1);

        task = "image";
        //new SetStatusAsyncTaskService().execute(new ServiceParams("SetProcessStatusImage", properties));

        return properties;
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != getActivity().RESULT_OK) return;

        switch (requestCode) {
            case PICK_FROM_CAMERA:
                doCrop();
                break;
            case CROP_FROM_CAMERA:
                Bundle extras = data.getExtras();
                if (data.getData() != null) {
                    Uri imageUri = data.getData();
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                        StatusImages statusImage = new StatusImages();
                        statusImage.setImagePath(imageUri);
                        statusImage.setPhoto(bitmap);

                        Utils.statusImagesArrayListOnceki.add(statusImage);
                        imageUriListOnceki.add(imageUri);
                        base64imagesOnceki.add(encodeTobase64(bitmap));
                        mAdapterOnceki.notifyDataSetChanged();
                        recyclerViewOnceki.invalidate();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }


                File f = new File(mImageCaptureUri.getPath());

                if (f.exists()) f.delete();

                break;

        }
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.NO_WRAP);
        return imageEncoded;
    }

    private void doCrop() {
        final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");

        List<ResolveInfo> list = getActivity().getPackageManager().queryIntentActivities(intent, 0);

        int size = list.size();

        if (size == 0) {
            Toast.makeText(getActivity(), "Kırpma uygulaması bulunamadı.", Toast.LENGTH_SHORT).show();

            return;
        } else {
            intent.setData(mImageCaptureUri);
            mImageCaptureUri2 = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                    "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri2);
            intent.putExtra("outputX", 800);
            intent.putExtra("outputY", 800);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);

            if (size == 1) {
                Intent i = new Intent(intent);
                ResolveInfo res = list.get(0);

                i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

                startActivityForResult(i, CROP_FROM_CAMERA);
            } else {
                for (ResolveInfo res : list) {
                    final CropOption co = new CropOption();

                    co.title = getActivity().getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
                    co.icon = getActivity().getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
                    co.appIntent = new Intent(intent);

                    co.appIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

                    cropOptions.add(co);
                }

                CropOptionAdapter adapter = new CropOptionAdapter(getActivity(), cropOptions);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Kırpma uygulaması seçin");
                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        startActivityForResult(cropOptions.get(item).appIntent, CROP_FROM_CAMERA);
                    }
                });

                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                        if (mImageCaptureUri != null) {
                            getActivity().getContentResolver().delete(mImageCaptureUri, null, null);
                            mImageCaptureUri = null;
                        }
                    }
                });

                AlertDialog alert = builder.create();

                alert.show();
            }
        }

    }


}
