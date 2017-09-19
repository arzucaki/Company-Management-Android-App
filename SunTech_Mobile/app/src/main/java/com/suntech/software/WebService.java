package com.suntech.software;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

/**
 * Created by Ramazan on 1/8/2016.
 */
public class WebService {

    private static String NAMESPACE = "http://tempuri.org/";
    private static String URL = "http://service.ceylaner.com.tr/suntech/RG_HelpDesk_Services.asmx";
    private static String SOAP_ACTION = "http://tempuri.org/";
    public static String CONNECTION_STRING = "Data Source=10.0.0.179;Initial Catalog=SunTech_Main;Persist Security Info=True;User ID=SunTech;Password=Metehan923259;Connect Timeout=350000";

  //  private static String NAMESPACE = "http://tempuri.org/";
  //  private static String URL = "http://service.ramazangunes.com/service/RG_HelpDesk_Services.asmx";
  //  private static String SOAP_ACTION = "http://tempuri.org/";
  //  public static String CONNECTION_STRING = "Data Source=mssql.ramazangunes.com;Initial Catalog=SunTech_Main;Persist Security Info=True;User ID=SunTech;Password=Metehan923259;Connect Timeout=350000";


    public static String invoke(String webMethName) {
        String resTxt = null;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL, 5000);

        try {
            // Invole web service
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to fahren static variable
            resTxt = response.toString();

        } catch (SocketTimeoutException e) {
            //When timeout occurs handles this....
            Log.w("SocketTimeoutException", "TIMEOUT");

        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "Error";
            Log.w("WebService Connection", "Connection Failed");
        }
        return resTxt;
    }

    public static String invoke(ArrayList<PropertyInfo> properties, String webMethName) {
        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        for (int i = 0; i < properties.size(); i++) {
            request.addProperty(properties.get(i));
        }

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);


        try {
            // Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL, 300000);
            // Invole web service
            androidHttpTransport.debug = true;
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            String xml = androidHttpTransport.responseDump;
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to fahren static variable
            resTxt = response.toString();

        } catch (SocketTimeoutException e) {
            //When timeout occurs handles this....
            Log.w("SocketTimeout Exception", "" + e.getMessage());
            resTxt = "SocketTimeout Exception";
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "Error occured";
        }
        Log.i("SERVICE RESPONSE", resTxt);
        return resTxt;
    }

}
