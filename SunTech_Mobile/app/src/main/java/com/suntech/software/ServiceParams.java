package com.suntech.software;

import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;

/**
 * Created by Ramazan on 1/8/2016.
 */
public class ServiceParams {
    public ArrayList<PropertyInfo> properties;
    public String methodName;

    public ServiceParams(String methodName, ArrayList<PropertyInfo> properties) {
        this.properties = properties;
        this.methodName = methodName;
    }
}
