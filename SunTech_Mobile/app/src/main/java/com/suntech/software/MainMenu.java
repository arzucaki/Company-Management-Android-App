package com.suntech.software;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.CursorJoiner;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;


import com.suntech.software.fragment.FragmentMain;
import com.suntech.software.model.MenuGroup;
import com.suntech.software.model.Model_AnaMenuList;
import com.suntech.software.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;

public class MainMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<PropertyInfo> properties = new ArrayList<>();
    NavigationView navigationView;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentMain hello = new FragmentMain();
        fragmentTransaction.add(R.id.fragment1, hello, "HELLO");
        fragmentTransaction.commit();


        PropertyInfo propertyInfo1 = new PropertyInfo();
        properties.clear();

        propertyInfo1.setName("Connection_String");
        propertyInfo1.setType(String.class);
        propertyInfo1.setValue(WebService.CONNECTION_STRING);
        properties.add(propertyInfo1);


        menu = navigationView.getMenu();

        new MainMenu.AsyncTaskService().execute(new ServiceParams("Get_MenuGroupList_JSON", properties));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Utils.Group_id=id;

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentMain fragment_main = new FragmentMain();
        fragmentTransaction.add(R.id.fragment1, fragment_main, "HELLO");
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public class AsyncTaskService extends AsyncTask<ServiceParams, Void, Void> {
        String resp = "";

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
                Utils.MenuGroupNames.clear();
                for (int i = 0; i < ja.length(); i++) {
                    Utils.MenuGroupNames.add(new MenuGroup(ja.getJSONObject(i)));
                }

                try{
                    for (int i = 1; i <= Utils.MenuGroupNames.size(); i++) {
                        menu.add(Utils.MenuGroupNames.get(i).getMenuGroup_id(), Utils.MenuGroupNames.get(i).getMenuGroup_id(), i, Utils.MenuGroupNames.get(i).getMenuGroup_Name());
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        public void restartActivity(){
            Intent mIntent = getIntent();
            finish();
            startActivity(mIntent);
        }

        @Override
        protected void onPreExecute() {


        }

        protected void onProgressUpdate(Integer... progress) {

        }

    }
}
