package com.suntech.software.utils;

import com.suntech.software.model.Auth_Company;
import com.suntech.software.model.CariKart;
import com.suntech.software.model.CariKartSube;
import com.suntech.software.model.Company;
import com.suntech.software.model.CompanyList;
import com.suntech.software.model.Department;
import com.suntech.software.model.EmailDetail;
import com.suntech.software.model.Envanter;
import com.suntech.software.model.ErrorCode;
import com.suntech.software.model.EventDetail;
import com.suntech.software.model.HelpDeskRecord;
import com.suntech.software.model.MenuGroup;
import com.suntech.software.model.Model_AnaMenuList;
import com.suntech.software.model.Priority;
import com.suntech.software.model.RecordType;
import com.suntech.software.model.Source;
import com.suntech.software.model.StatusImages;
import com.suntech.software.model.StatusList;
import com.suntech.software.model.User;

import java.util.ArrayList;

/**
 * Created by Ramazan on 11/8/2016.
 */
public class Utils {

    public static User user;
    public static ArrayList<Auth_Company> Auth_companyArrayList = new ArrayList<>();
    public static ArrayList<Model_AnaMenuList> AnaMenu_ArrayList = new ArrayList<>();
    public static ArrayList<Company> companyArrayList = new ArrayList<>();
    public static ArrayList<StatusList> statusArrayList = new ArrayList<>();
    public static ArrayList<HelpDeskRecord> helpDeskRecordsArrayList = new ArrayList<>();
    public static HelpDeskRecord selectedHelpDeskRecord = new HelpDeskRecord();
    public static ArrayList<EventDetail> eventDetailArrayList = new ArrayList<>();
    public static ArrayList<EmailDetail> emailDetailArrayList = new ArrayList<>();
    public static ArrayList<StatusImages> statusImagesArrayListOnceki = new ArrayList<>();
    public static ArrayList<StatusImages> statusImagesArrayListSonraki = new ArrayList<>();
    public static int cariKartId;
    public static int isletmeId;
    public static int departmentId;
    public static int selectedStatus;
    public static int Company_id_;
    public static int Group_id=3;
    public static int AnaMenu_id;
    public static String selectedMenu;
    public static ArrayList<Source> sourceArrayList = new ArrayList<>();
    public static ArrayList<RecordType> recordTypeArrayList = new ArrayList<>();
    public static ArrayList<Priority> priorityArrayList = new ArrayList<>();
    public static ArrayList<CompanyList> companyListArrayList = new ArrayList<>();
    public static ArrayList<CariKart> cariKartArrayList = new ArrayList<>();
    public static ArrayList<CariKartSube> cariKartSubeArrayList = new ArrayList<>();
    public static ArrayList<Envanter> envanterArrayList = new ArrayList<>();
    public static ArrayList<ErrorCode> errorCodeArrayList = new ArrayList<>();
    public static ArrayList<Department> departmentArrayList = new ArrayList<>();
    public static ArrayList<MenuGroup> MenuGroupNames = new ArrayList<>();

    public static String getDevamEdenIslerStatus() {
        String response = "";
        if (statusArrayList.size() > 0) {
            for (int i = 0; i < statusArrayList.size(); i++) {
                if (statusArrayList.get(i).getStatus_Type() == 0) {
                    response += "" + statusArrayList.get(i).getHD_Event_Status_id() + ",";
                }
            }
            response = response.substring(0, response.length() - 1);
        }
        return response;
    }

    public static String getTamamlananIslerStatus() {
        String response = "";
        if (statusArrayList.size() > 0) {
            for (int i = 0; i < statusArrayList.size(); i++) {
                if (statusArrayList.get(i).getStatus_Type() == 1) {
                    response += "" + statusArrayList.get(i).getHD_Event_Status_id() + ",";
                }
            }
            response = response.substring(0, response.length() - 1);
        }
        return response;
    }

}
