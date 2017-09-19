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
import android.support.annotation.NonNull;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.suntech.software.R;
import com.suntech.software.ServiceParams;
import com.suntech.software.WebService;
import com.suntech.software.adapter.CropOption;
import com.suntech.software.adapter.CropOptionAdapter;
import com.suntech.software.adapter.ExpandableListViewAdapter;
import com.suntech.software.adapter.ImageListAdapter;
import com.suntech.software.model.EmailDetail;
import com.suntech.software.model.EventDetail;
import com.suntech.software.model.StatusImages;
import com.suntech.software.utils.Utils;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.ksoap2.serialization.PropertyInfo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ramazan on 11/8/2016.
 */
public class FragmentHDRecordDetail extends Fragment {

    private ArrayList<PropertyInfo> properties = new ArrayList<>();
    public TextView txtTarihSaat;
    public TextView txtOncelik;
    public TextView txtGrubuTipi;
    public TextView txtErrorCode;
    public TextView txtDurumu;
    Spinner spinner;
    List<String> categories = new ArrayList<String>();
    EditText etNotes;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;

    public HashMap<String, List<String>> list_child;
    public List<String> list_parent;

    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int CAMERA_REQUEST_CODE = 3;
    private Uri mImageCaptureUri;
    private Uri mImageCaptureUri2;
    Button btnFotografEkle, btnFotografEkleSonraki;
    Button btnGuncelle;
    ArrayList<Uri> imageUriListOnceki = new ArrayList<>();
    ArrayList<Uri> imageUriListSonraki = new ArrayList<>();
    private RecyclerView recyclerViewOnceki, recyclerViewSonraki;
    private ImageListAdapter mAdapterOnceki, mAdapterSonraki;
    ArrayList<String> base64imagesOnceki = new ArrayList<>();
    ArrayList<String> base64imagesSonraki = new ArrayList<>();

    String task;

    private ExpandableLayout expandableLayout;
    private ExpandableLayout expandableLayoutSonraki;

    private static int IMAGE_STATUS = 0;
    private Button btnOnceki, btnSonraki;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hdrecord_detail, container, false);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        Utils.statusImagesArrayListOnceki.clear();
        Utils.statusImagesArrayListSonraki.clear();

        expandableLayout = (ExpandableLayout) view.findViewById(R.id.expandable_layout);
        btnOnceki = (Button) view.findViewById(R.id.button_onceki);
        btnOnceki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.statusImagesArrayListOnceki.size() < 1)
                    getStatusImages(0);
                expandableLayout.toggle();
            }
        });

        expandableLayoutSonraki = (ExpandableLayout) view.findViewById(R.id.expandable_layout_sonraki);
        btnSonraki = (Button) view.findViewById(R.id.button_sonraki);
        btnSonraki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.statusImagesArrayListSonraki.size() < 1)
                    getStatusImages(1);
                expandableLayoutSonraki.toggle();
            }
        });

        txtTarihSaat = (TextView) view.findViewById(R.id.txtTarihSaat);
        txtOncelik = (TextView) view.findViewById(R.id.txtOncelik);
        txtGrubuTipi = (TextView) view.findViewById(R.id.txtGrubuTipi);
        txtErrorCode = (TextView) view.findViewById(R.id.txtErrorCode);
        txtDurumu = (TextView) view.findViewById(R.id.txtDurumu);
        etNotes = (EditText) view.findViewById(R.id.etNotes);
        expListView = (ExpandableListView) view.findViewById(R.id.expandList);
        btnFotografEkle = (Button) view.findViewById(R.id.btnFotografEkle);
        btnFotografEkleSonraki = (Button) view.findViewById(R.id.btnFotografEkle_sonraki);
        btnFotografEkle.setOnClickListener(new CaptureImageClickListener());
        btnFotografEkleSonraki.setOnClickListener(new CaptureImageClickListener());
        btnGuncelle = (Button) view.findViewById(R.id.btnGuncelle);
        btnGuncelle.setOnClickListener(new StatusGuncelleClickListener());

        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                setListViewHeight(expandableListView, i);
                return false;
            }
        });

        setHDRecords();
        // Spinner element
        spinner = (Spinner) view.findViewById(R.id.spinner);
        categories.clear();
        categories.add("Durum seçiniz");
        for (int i = 0; i < Utils.statusArrayList.size(); i++) {
            categories.add(Utils.statusArrayList.get(i).getEvent_Status());
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        recyclerViewOnceki = (RecyclerView) view.findViewById(R.id.recyclerList);
        recyclerViewSonraki = (RecyclerView) view.findViewById(R.id.recyclerList_sonraki);
        mAdapterOnceki = new ImageListAdapter(getActivity(), Utils.statusImagesArrayListOnceki);
        mAdapterSonraki = new ImageListAdapter(getActivity(), Utils.statusImagesArrayListSonraki);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewOnceki.setLayoutManager(layoutManager);
        recyclerViewOnceki.setItemAnimator(new DefaultItemAnimator());
        recyclerViewOnceki.setAdapter(mAdapterOnceki);
        LinearLayoutManager layoutManagerSonraki = new LinearLayoutManager(getActivity());
        layoutManagerSonraki.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewSonraki.setLayoutManager(layoutManagerSonraki);
        recyclerViewSonraki.setItemAnimator(new DefaultItemAnimator());
        recyclerViewSonraki.setAdapter(mAdapterSonraki);

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
        recyclerViewSonraki.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerViewSonraki, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Toast.makeText(getActivity(), position + " is clicked!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                File file = new File(Utils.statusImagesArrayListSonraki.get(position).getImagePath().getPath());
                intent.setDataAndType(Uri.fromFile(file), "image/*");
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                //Toast.makeText(getActivity(), position + " is long-clicked!", Toast.LENGTH_SHORT).show();
            }
        }));

        return view;

    }

    private class CaptureImageClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            if (view.getId() == R.id.btnFotografEkle)
                IMAGE_STATUS = 0;
            else if (view.getId() == R.id.btnFotografEkle_sonraki)
                IMAGE_STATUS = 1;

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Now user should be able to use camera
            } else {
                // Your app will not have this permission. Turn off all functions
                // that require this permission or it will force close like your
                // original question
            }
        }
    }

    private void setListViewHeight(ExpandableListView listView,
                                   int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }


    public void setHDRecords() {
        String tarih = Utils.selectedHelpDeskRecord.getTarih().substring(0, 10);
        String saat = Utils.selectedHelpDeskRecord.getSaat().substring(0, 5);
        txtTarihSaat.setText(tarih + " - " + saat);
        txtOncelik.setText(Utils.selectedHelpDeskRecord.getOncelik());
        txtGrubuTipi.setText(Utils.selectedHelpDeskRecord.getGrubu() + " / " + Utils.selectedHelpDeskRecord.getTipi());
        txtErrorCode.setText(Utils.selectedHelpDeskRecord.getError_Code_Description());
        txtDurumu.setText(Utils.selectedHelpDeskRecord.getDurumu());
        btnOnceki.setText(btnOnceki.getText() + " (" + Utils.selectedHelpDeskRecord.getKayitOnceki() + ")");
        btnSonraki.setText(btnSonraki.getText() + " (" + Utils.selectedHelpDeskRecord.getKayitSonraki() + ")");

        PropertyInfo propertyInfo1 = new PropertyInfo();
        properties.clear();


        propertyInfo1.setName("Connection_String");
        propertyInfo1.setType(String.class);
        propertyInfo1.setValue(WebService.CONNECTION_STRING);
        properties.add(propertyInfo1);

        propertyInfo1 = new PropertyInfo();
        propertyInfo1.setName("Event_Card_id");
        propertyInfo1.setType(Integer.class);
        propertyInfo1.setValue(Utils.selectedHelpDeskRecord.getKayit_id());
        properties.add(propertyInfo1);

        task = "records";
        new AsyncTaskService().execute(new ServiceParams("GetHDMainRecordDetail_JSON", properties));
    }

    public void getStatusImages(int imageStatus) {
        PropertyInfo propertyInfo1 = new PropertyInfo();
        properties.clear();

        propertyInfo1.setName("Connection_String");
        propertyInfo1.setType(String.class);
        propertyInfo1.setValue(WebService.CONNECTION_STRING);
        properties.add(propertyInfo1);

        propertyInfo1 = new PropertyInfo();
        propertyInfo1.setName("IslemDurumu");
        propertyInfo1.setType(Integer.class);
        propertyInfo1.setValue(imageStatus);
        properties.add(propertyInfo1);

        propertyInfo1 = new PropertyInfo();
        propertyInfo1.setName("Help_Desk_Kayit_id");
        propertyInfo1.setType(Integer.class);
        propertyInfo1.setValue(Utils.selectedHelpDeskRecord.getKayit_id());
        properties.add(propertyInfo1);

        IMAGE_STATUS = imageStatus;
        task = "images";
        new AsyncTaskService().execute(new ServiceParams("GetProcessStatusImage_JSON", properties));


    }

    public void Hazirla() {
        List<String> gs_list;
        List<String> fb_list;
        list_parent = new ArrayList<String>();
        list_child = new HashMap<String, List<String>>();

        list_parent.add("İşlem Kayıtları" + " (" + Utils.eventDetailArrayList.size() + ")");
        list_parent.add("E-mail Kayıtları" + " (" + Utils.emailDetailArrayList.size() + ")");

        gs_list = new ArrayList<String>();
        fb_list = new ArrayList<String>();

        list_child.put(list_parent.get(0), gs_list);
        list_child.put(list_parent.get(1), fb_list);

        listAdapter = new ExpandableListViewAdapter(getActivity(), list_parent, list_child);
        expListView.setAdapter(listAdapter);
        expListView.setClickable(true);
        setListViewHeight(expListView, 110);

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

            if (task.equals("records")) {
                try {
                    JSONArray ja = new JSONArray(resp);
                    Utils.eventDetailArrayList.clear();
                    Utils.emailDetailArrayList.clear();
                    for (int i = 0; i < ja.length(); i++) {
                        if (!ja.getJSONObject(i).isNull("Event_Cards_id") && ja.getJSONObject(i).getInt("Event_Cards_id") > 0) {
                            Utils.eventDetailArrayList.add(new EventDetail(ja.getJSONObject(i)));
                        }
                        if (!ja.getJSONObject(i).isNull("Email_Record_id") && ja.getJSONObject(i).getInt("Email_Record_id") > 0) {
                            Utils.emailDetailArrayList.add(new EmailDetail(ja.getJSONObject(i)));
                        }
                    }


                    Hazirla();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (task.equals("images")) {
                try {
                    if (IMAGE_STATUS == 0) {
                        JSONArray ja = new JSONArray(resp);
                        Utils.statusImagesArrayListOnceki.clear();
                        for (int i = 0; i < ja.length(); i++) {
                            Utils.statusImagesArrayListOnceki.add(new StatusImages(getActivity(), ja.getJSONObject(i)));
                        }
                        mAdapterOnceki.notifyDataSetChanged();
                        recyclerViewOnceki.invalidate();

                        for (int i = 0; i < Utils.statusImagesArrayListOnceki.size(); i++) {
                            Utils.statusImagesArrayListOnceki.get(i).setImagePath(storeImage(Utils.statusImagesArrayListOnceki.get(i).getPhoto()));
                        }
                    } else if (IMAGE_STATUS == 1) {
                        JSONArray ja = new JSONArray(resp);
                        Utils.statusImagesArrayListSonraki.clear();
                        for (int i = 0; i < ja.length(); i++) {
                            Utils.statusImagesArrayListSonraki.add(new StatusImages(getActivity(), ja.getJSONObject(i)));
                        }
                        mAdapterSonraki.notifyDataSetChanged();
                        recyclerViewSonraki.invalidate();

                        for (int i = 0; i < Utils.statusImagesArrayListSonraki.size(); i++) {
                            Utils.statusImagesArrayListSonraki.get(i).setImagePath(storeImage(Utils.statusImagesArrayListSonraki.get(i).getPhoto()));
                        }
                    }

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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

                        if (IMAGE_STATUS == 0) {
                            Utils.statusImagesArrayListOnceki.add(statusImage);
                            imageUriListOnceki.add(imageUri);
                            base64imagesOnceki.add(encodeTobase64(bitmap));
                            mAdapterOnceki.notifyDataSetChanged();
                            recyclerViewOnceki.invalidate();
                        } else if (IMAGE_STATUS == 1) {
                            Utils.statusImagesArrayListSonraki.add(statusImage);
                            imageUriListSonraki.add(imageUri);
                            base64imagesSonraki.add(encodeTobase64(bitmap));
                            mAdapterSonraki.notifyDataSetChanged();
                            recyclerViewSonraki.invalidate();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }


                File f = new File(mImageCaptureUri.getPath());

                if (f.exists()) f.delete();

                break;

        }
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

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
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

    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.NO_WRAP);
        return imageEncoded;
    }

    public Uri getImageUri(Context context, Bitmap image) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), image, "title.jpg", null);
        return Uri.parse(path);
    }

    private Uri storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d("TAG",
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return null;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("TAG", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("TAG", "Error accessing file: " + e.getMessage());
        }

        return Uri.fromFile(pictureFile);
    }

    private File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getActivity().getPackageName()
                + "/Files");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        File mediaFile;
        String mImageName = "MI_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    private class StatusGuncelleClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (spinner.getSelectedItemPosition() == 0)
                Toast.makeText(getActivity(), "Lütfen bir durum güncellemesi seçiniz.", Toast.LENGTH_SHORT).show();
            else
                setRecordStatus();
        }
    }

    public void setRecordStatus() {

        PropertyInfo propertyInfo1 = new PropertyInfo();
        properties.clear();

        propertyInfo1.setName("Connection_String");
        propertyInfo1.setType(String.class);
        propertyInfo1.setValue(WebService.CONNECTION_STRING);
        properties.add(propertyInfo1);

        propertyInfo1 = new PropertyInfo();
        propertyInfo1.setName("Event_Card_id");
        propertyInfo1.setType(Integer.class);
        propertyInfo1.setValue(Utils.selectedHelpDeskRecord.getKayit_id());
        properties.add(propertyInfo1);

        propertyInfo1 = new PropertyInfo();
        propertyInfo1.setName("Kayit_Status_id");
        propertyInfo1.setType(Integer.class);
        propertyInfo1.setValue(Utils.statusArrayList.get(spinner.getSelectedItemPosition() - 1).getHD_Event_Status_id());
        properties.add(propertyInfo1);

        propertyInfo1 = new PropertyInfo();
        propertyInfo1.setName("Kayit_Tipi_id");
        propertyInfo1.setType(Integer.class);
        propertyInfo1.setValue(Utils.statusArrayList.get(spinner.getSelectedItemPosition() - 1).getStatus_Type());
        properties.add(propertyInfo1);

        propertyInfo1 = new PropertyInfo();
        propertyInfo1.setName("Kayda_Atanan_Personel_id");
        propertyInfo1.setType(Integer.class);
        propertyInfo1.setValue(Utils.user.getUser_id());
        properties.add(propertyInfo1);

        propertyInfo1 = new PropertyInfo();
        propertyInfo1.setName("Kayit_Kapatan_Personel_id");
        propertyInfo1.setType(Integer.class);
        propertyInfo1.setValue(Utils.user.getUser_id());
        properties.add(propertyInfo1);

        propertyInfo1 = new PropertyInfo();
        propertyInfo1.setName("User_id");
        propertyInfo1.setType(Integer.class);
        propertyInfo1.setValue(Utils.user.getUser_id());
        properties.add(propertyInfo1);

        propertyInfo1 = new PropertyInfo();
        propertyInfo1.setName("Islem_Notu");
        propertyInfo1.setType(String.class);
        propertyInfo1.setValue(etNotes.getText().toString());
        properties.add(propertyInfo1);

        task = "status";
        new SetStatusAsyncTaskService().execute(new ServiceParams("SetMainRecordStatus", properties));
    }

    public ArrayList<PropertyInfo> setStatusImages(int status, int position) {

        PropertyInfo propertyInfo1 = new PropertyInfo();
        properties.clear();

        propertyInfo1.setName("Connection_String");
        propertyInfo1.setType(String.class);
        propertyInfo1.setValue(WebService.CONNECTION_STRING);
        properties.add(propertyInfo1);

        propertyInfo1 = new PropertyInfo();
        propertyInfo1.setName("Help_Desk_Kayit_id");
        propertyInfo1.setType(Integer.class);
        propertyInfo1.setValue(Utils.selectedHelpDeskRecord.getKayit_id());
        properties.add(propertyInfo1);

        propertyInfo1 = new PropertyInfo();
        propertyInfo1.setName("IslemDurumu");
        propertyInfo1.setType(Integer.class);
        propertyInfo1.setValue(status);
        properties.add(propertyInfo1);

        propertyInfo1 = new PropertyInfo();
        propertyInfo1.setName("Photo");
        propertyInfo1.setType(String.class);
        propertyInfo1.setValue(status == 0 ? base64imagesOnceki.get(position) : base64imagesSonraki.get(position));
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

    public class SetStatusAsyncTaskService extends AsyncTask<ServiceParams, Void, Void> {
        String resp = "";
        ProgressDialog progressDialog;

        @Override
        protected Void doInBackground(ServiceParams... params) {

            if (task.equals("status")) {
                resp = WebService.invoke(params[0].properties, params[0].methodName);
            }
            if (task.equals("image")) {
                for (int i = 0; i < base64imagesOnceki.size(); i++) {
                    resp = WebService.invoke(setStatusImages(0, i), "SetProcessStatusImage");
                }
                for (int i = 0; i < base64imagesSonraki.size(); i++) {
                    resp = WebService.invoke(setStatusImages(1, i), "SetProcessStatusImage");
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            Log.w("WEBSERVICE RESPONSE===", resp);

            if (task.equals("status")) {
                task = "image";
                new SetStatusAsyncTaskService().execute();
            }
            if (task.equals("image")) {
                if(getActivity()!=null) {
                    getActivity().getFragmentManager().popBackStack();
                    Toast.makeText(getActivity(), "Güncelleme gönderildi!", Toast.LENGTH_SHORT).show();
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

}
