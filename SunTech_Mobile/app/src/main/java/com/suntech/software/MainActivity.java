package com.suntech.software;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.widget.Button;

import com.suntech.software.fragment.FragmentMain;

import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private Button btnIsKayitlari, btnYeniIsKaydi;
    private ArrayList<PropertyInfo> properties = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentMain hello = new FragmentMain();
        fragmentTransaction.add(R.id.fragment1, hello, "HELLO");
        fragmentTransaction.commit();

    }

    /*
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentMain hello = new FragmentMain();
        fragmentTransaction.add(R.id.fragment1, hello, "HELLO");
        fragmentTransaction.commit();


    }
    */


}
