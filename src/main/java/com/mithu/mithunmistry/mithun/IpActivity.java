package com.mithu.mithunmistry.mithun;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

//import pl.droidsonroids.gif.GifImageView;

public class IpActivity extends AppCompatActivity {
    EditText ip;
    EditText port;
    int port_address;
    String ip_address;
    Integer ic = 1;
    Socket socket;
    String message = "portip";
    Timer timer;
    TimerTask timerTask;
    Integer x = 1;
    //GifImageView mickey2, mickey1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ic = 1;
        SharedPreferences logactivity = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //Bundle bundle = getIntent().getExtras();
        SharedPreferences logactivity1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (logactivity.getBoolean("Login Check", true) && logactivity1.getBoolean("Login Check Transient", true)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_ip);
            SharedPreferences.Editor editor = logactivity1.edit();
            editor.putBoolean("Login Check Transient", true);
            editor.commit();
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            ip = (EditText)findViewById(R.id.ip);
            port = (EditText)findViewById(R.id.port);
            //mickey2 = (GifImageView)findViewById(R.id.mickey2);
            //mickey1 = (GifImageView)findViewById(R.id.mickey1);
            SharedPreferences portip = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            //int SERVERPORT = portip.getInt("Port", 6000);
            //mickey1.setVisibility(View.VISIBLE);
            //alt = 1;

            String SERVER_IP = portip.getString("IP", "192.168.1.100").toString();
            ip.setText(SERVER_IP);
            //port.setText(SERVERPORT);


            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Use 'mithun' in IP to bypass this screen", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder logoutConfirm = new AlertDialog.Builder(this);
        logoutConfirm.setTitle("Logout?");
        logoutConfirm.setMessage("Confirm Logout from mithunjmistry?");
        logoutConfirm.setCancelable(true);


        logoutConfirm.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences logactivity = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = logactivity.edit();
                        //editor.putBoolean("Login Check", true);
                        editor.clear();
                        editor.commit();
                        Intent positiveactivity = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(positiveactivity);
                    }
                });
        logoutConfirm.setNegativeButton("No", null);
        logoutConfirm.create().show();
    }

    public void setIp(View view){
        final ProgressDialog dialog = new ProgressDialog(IpActivity.this);
        dialog.setTitle("Authenticating...");
        dialog.setMessage("Please wait..");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
        port_address = Integer.parseInt(port.getText().toString());
        ip_address = ip.getText().toString();
        if(ip_address.equalsIgnoreCase("mithun")){
            ic = 0;
            Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
            startActivity(intent);
            finish();
        }
        else {
            startTimer();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // do something
                    if (ic.intValue() == x.intValue()) {
                        dialog.hide();
                        AlertDialog alertDialog = new AlertDialog.Builder(IpActivity.this).create();
                        alertDialog.setTitle("Authentication failed!");
                        alertDialog.setMessage("Please try again with correct IP and Port number.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }
            }, 3300);

        /*SharedPreferences portip = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = portip.edit();
        editor.putString("IP", ip_address);
        editor.putInt("Port", port_address);
        editor.commit();
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
        finish();*/
        }
    }

    public void startTimer() {
        //set a new Timer
        timer = new Timer();
        //initialize the TimerTask's job
        initializeTimerTask();
        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 100, 1500); //
    }

    public void stoptimertask(View v) {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                //use a handler to run a toast that shows the current timestamp
                new Thread(new ClientThread()).start();
                try {
                    String str = message;

                    PrintWriter out = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream())), true);
                    out.println(str);
                    out.flush();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new ConnectionTask().execute();

            }
        };
    }
    class ConnectionTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... params) {
            String responce = null;
            try {

                InputStream input = socket.getInputStream();
                int lockSeconds = 10 * 1000;

                long lockThreadCheckpoint = System.currentTimeMillis();
                int availableBytes = input.available();
                while (availableBytes <= 0 && (System.currentTimeMillis() < lockThreadCheckpoint + lockSeconds)) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                    availableBytes = input.available();
                }

                byte[] buffer = new byte[availableBytes];
                input.read(buffer, 0, availableBytes);
                responce = new String(buffer);

                //out.close();
                input.close();
                socket.close();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return responce;
        }

        protected void onPostExecute(String responce) {
            if (responce != null) {
                if(responce.equals("allok")){
                    SharedPreferences portip = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = portip.edit();
                    editor.putString("IP", ip_address);
                    editor.putInt("Port", port_address);
                    editor.commit();
                    ic = 0;
                    Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                    startActivity(intent);
                    finish();
                }

            }
            else{
                //message = "hello";

            }


        }
    }

    class ClientThread implements Runnable {

        @Override
        public void run() {
            if (ic.intValue() == x.intValue()) {

                try {
                    InetAddress serverAddr = InetAddress.getByName(ip_address);

                    socket = new Socket(serverAddr, port_address);

                } catch (UnknownHostException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
            else{

            }

        }
    }
}
