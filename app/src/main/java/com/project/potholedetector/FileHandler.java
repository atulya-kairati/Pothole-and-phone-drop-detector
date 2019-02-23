package com.project.potholedetector;

import android.location.Location;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileHandler {

    private final String FILE_NAME = "PotHoleDataBase.txt";
    private final String FILE1 = "latData.txt";
    private final String FILE2 = "lonData.txt";


    public void saveFile(Location currentloc){

        MainActivity mObj = new MainActivity();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat dateformatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String d = String.valueOf(formatter.format(date)) ;
        String data1 = String.valueOf(currentloc.getLatitude())+"\n";
        String data2 = String.valueOf(currentloc.getLongitude())+"\n";
        String data = "Latitude :  " + String.valueOf(currentloc.getLatitude()) + "\n Longitude :  "+String.valueOf(currentloc.getLongitude());
        data = d + " \n " + data + "\n-------------------------------------------------------\n";
        d = String.valueOf(dateformatter.format(date));;



        //file
        String state = Environment.getExternalStorageState();

        if(Environment.MEDIA_MOUNTED.equals(state)){
            File root = Environment.getExternalStorageDirectory();
            File dir = new File(root.getAbsolutePath()+"/Pothole");

            if( ! dir.exists())
                dir.mkdir();

            File file =new File(dir,FILE_NAME);
            File file1 =new File(dir,FILE1);
            File file2 =new File(dir,FILE2);
            FileOutputStream fos = null,fos1=null,fos2=null;
            try {
                fos = new FileOutputStream(file,true);          //mObj.openFileOutput(FILE_NAME, Context.MODE_APPEND);
                fos.write(data.getBytes());
                fos1 = new FileOutputStream(file1,true);
                fos1.write(data1.getBytes());
                fos2 = new FileOutputStream(file2,true);
                fos2.write(data2.getBytes());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if(fos != null){
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else
            Toast.makeText(mObj.getApplicationContext(), "Failed to Load SD card", Toast.LENGTH_SHORT).show();

    }

    public String read(){
        String msg= "";
        StringBuffer sb= null;

        File root = Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath()+"/Pothole");
        File file =new File(dir,FILE_NAME);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            sb = new StringBuffer();

            while ((msg=br.readLine()) != null){
                sb.append(msg + "\n");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (sb.toString());
    }

    public void delData(){

        MainActivity mObj = new MainActivity();
        String data ="\n";

        String state = Environment.getExternalStorageState();

        if(Environment.MEDIA_MOUNTED.equals(state)){
            File root = Environment.getExternalStorageDirectory();
            File dir = new File(root.getAbsolutePath()+"/Pothole");

            if( ! dir.exists())
                dir.mkdir();

            File file =new File(dir,FILE_NAME);
            FileOutputStream fos = null;
            try {
                //mObj.openFileOutput(FILE_NAME, Context.MODE_APPEND);
                fos = new FileOutputStream(file,false);
                fos.write(data.getBytes());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if(fos != null){
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else
            Toast.makeText(mObj.getApplicationContext(), "Failed to Load SD card", Toast.LENGTH_SHORT).show();

    }

}

