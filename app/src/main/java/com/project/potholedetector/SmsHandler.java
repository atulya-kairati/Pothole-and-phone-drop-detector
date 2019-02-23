package com.project.potholedetector;

import android.location.Location;
import android.telephony.SmsManager;
import android.widget.Toast;

public class SmsHandler {

    public void send(Location currentloc) {
        MainActivity mObj = new MainActivity();

        String phoneNo = "8881526407";
        String sms = "You droped your phone at : \nLatitude :" + String.valueOf(currentloc.getLatitude()) + "\nLongitude : "+String.valueOf(currentloc.getLongitude());
        try {
            SmsManager smsMan = SmsManager.getDefault();
            smsMan.sendTextMessage(phoneNo, null, sms, null, null);
            Toast.makeText(mObj.getApplicationContext(),"SMS SENT",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(mObj.getApplicationContext(),"AAhhhhhhh SHIT !",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

}