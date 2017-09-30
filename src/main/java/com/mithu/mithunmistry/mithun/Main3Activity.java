package com.mithu.mithunmistry.mithun;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
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

public class Main3Activity extends AppCompatActivity {
    //SharedPreferences portip = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

    EditText doorpassword;
    String doorpwd = "12345";
    Button entrybtn;
    int SERVERPORT = 6000;
    String SERVER_IP = "192.168.1.100";
    String message = "door";
    Timer timer;
    TimerTask timerTask;
    Socket socket;
    Integer ic = 1;
    Integer x = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        SharedPreferences portip = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ipr = portip.getString("IP", "192.168.1.100");
        int portr = portip.getInt("Port", 6000);
        //Log.d("test", test1);
        SERVER_IP = ipr;
        SERVERPORT = portr;
        entrybtn = (Button) findViewById(R.id.doorpwdbtn);
        doorpassword = (EditText) findViewById(R.id.doorpwdin);
        doorpassword.setFocusable(true);
        doorpassword.setFocusableInTouchMode(true);
        doorpassword.requestFocus();
        ic = 1;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        startTimer();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Developed by Mithun", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void doorControl(final View view) {

        String entereddoorpwd = doorpassword.getText().toString();
        if (entrybtn.getText().toString().equals("OPEN DOOR")) {

            if (doorpwd.equals(entereddoorpwd)) {
                message = "odoor";
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
                doorpassword.setText("");

                dlgAlert.setMessage("Welcome");
                dlgAlert.setTitle("Correct password");
                dlgAlert.setPositiveButton("OK", null);
                entrybtn.setText("CLOSE DOOR");
                dlgAlert.create().show();

                dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which){

                            }
                        });
            } else {
                AlertDialog.Builder dlgAlert1 = new AlertDialog.Builder(this);
                doorpassword.setText("");

                dlgAlert1.setMessage("Please try again!");
                dlgAlert1.setTitle("Incorrect password");
                dlgAlert1.setPositiveButton("Ok", null);
                dlgAlert1.setCancelable(true);
                dlgAlert1.create().show();

                dlgAlert1.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
            }
        } else {
            doorpassword.setText("");
            message = "cdoor";
            entrybtn.setText("OPEN DOOR");

        }

    }

    @Override
    public void onBackPressed() {
        ic = 0;
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
        finish();
    }

    public void startTimer() {
        //set a new Timer
        timer = new Timer();
        //initialize the TimerTask's job
        initializeTimerTask();
        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 100, 2000); //
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
                if(responce.equals("aopen")){
                    entrybtn.setText("CLOSE DOOR");
                    message = "door";
                }
                else if(responce.equals("aclose")){
                    entrybtn.setText("OPEN DOOR");
                    message = "door";
                }
                else{
                    message = "door";
                }
            }
            else{
                //message = "door";
            }


        }
    }

    class ClientThread implements Runnable {

        @Override
        public void run() {
            if (ic.intValue() == x.intValue()) {

                try {
                    InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

                    socket = new Socket(serverAddr, SERVERPORT);

                } catch (UnknownHostException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
            else {

            }

        }
    }

}

