package com.mithu.mithunmistry.mithun;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import com.flotandroidchart.flot.FlotChartContainer;
import com.flotandroidchart.flot.FlotDraw;
import com.flotandroidchart.flot.Options;
import com.flotandroidchart.flot.data.MarkingData;
import com.flotandroidchart.flot.data.PointData;
import com.flotandroidchart.flot.data.RangeData;
import com.flotandroidchart.flot.data.SeriesData;
import com.flotandroidchart.flot.data.TickData;


public class experiment extends AppCompatActivity {

    EditText et;
    Button sockbtn;
    EditText password1;
    EditText password2;
    private String enteredPassword1;
    private String enteredPassword2;
    private static final int SERVERPORT = 6000;
    private static final String SERVER_IP = "192.168.1.100";
    static final String TAG = "SymmmetricAlgorithmAES";
    private Socket socket;
    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_experiment);

        //sockStatus = (TextView)findViewById(R.id.socketStatusText);
        sockbtn = (Button) findViewById(R.id.socketbtn);
        et = (EditText) findViewById(R.id.editText3);
        new Thread(new ClientThread()).start();
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        getLocalIpAddress();
        //plot();


        /*WifiManager wifiMan = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInf = wifiMan.getConnectionInfo();
        int ipAddress = wifiInf.getIpAddress();
        String ip1 = String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
        Log.d("ip1", ip1);*/
    }

    @Override
    public void onPause() {
        super.onPause();
        //Log.d("minimize", "done");
        while (true) {
            Log.d("minimize", "done");
            if (i == 1) {

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
                mBuilder.setSmallIcon(R.drawable.mappic);
                mBuilder.setAutoCancel(true);
                mBuilder.setContentTitle("Notification Alert, Click Me!");
                mBuilder.setContentText("Hi, This is Android Notification Detail!");

                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

// notificationID allows you to update the notification later on.
                mNotificationManager.notify(1, mBuilder.build());
            }
        }
    }


    public void sockonClick(View view) {
        try {

            String str = et.getText().toString().replaceAll("\\s+$", "");
            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())),
                    true);
            out.println(str);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    class ClientThread implements Runnable {
        @Override
        public void run() {
            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVERPORT);
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {


                InputStream in = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String test = br.readLine();
                test.replaceAll("\\s+$", "");
                if (test.equals("hello")) {
                    Log.i("data is being rec", "the data is being rec");
                } else {
                    Log.i("no data", "no data");
                }



                //in.close();

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void experimentLogin(View view) {
        SharedPreferences firstTimeLogin = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences password = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = firstTimeLogin.edit();
        enteredPassword1 = password1.getText().toString();
        enteredPassword2 = password2.getText().toString();
        if (firstTimeLogin.getBoolean("First time Login", true)) {
            {

                if ((enteredPassword1).equals(enteredPassword2)) {
                    SharedPreferences.Editor editor2 = password.edit();
                    editor2.putString("Password", enteredPassword1);
                    editor2.commit();
                    editor.putBoolean("First time Login", false);
                    editor.commit();
                    password2.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(getApplicationContext(), "The passwords doesn't match", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (enteredPassword1.equals(password.getString("Password", enteredPassword1))) {
            password2.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(this, Main2Activity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Incorrect password", Toast.LENGTH_SHORT).show();
        }
    }

    public void main12(View view) {

        try {
            String str = "hello";
            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())),
                    true);
            out.println(str);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void onBackPressed() {
        try {
            String str = "stop";
            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())),
                    true);
            out.println(str);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();

        }
        Intent intent = new Intent(this, Main4Activity.class);
        startActivity(intent);
    }

    public void outputControl(View view) {
        try {
            String str = "stop";
            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())),
                    true);
            out.println(str);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();

        }
        Intent intent = new Intent(this, OutputcontrolActivity.class);
        startActivity(intent);
    }

    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    System.out.println("ip1--:" + inetAddress);
                    System.out.println("ip2--:" + inetAddress.getHostAddress());

                    // for getting IPV4 format
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {

                        String ip = inetAddress.getHostAddress().toString();
                        System.out.println("ip---::" + ip);
                        et.setText(ip);
                        // return inetAddress.getHostAddress().toString();
                        return ip;
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("IP Address", ex.toString());
        }
        return null;
    }


    }




