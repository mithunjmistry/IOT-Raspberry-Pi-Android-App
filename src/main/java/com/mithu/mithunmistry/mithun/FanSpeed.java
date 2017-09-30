package com.mithu.mithunmistry.mithun;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

public class FanSpeed extends AppCompatActivity {
    SeekBar seekBar2;
    TextView fanSpeedDisplay;
    ScrollView root;
    int percentage_display;
    Double value;
    int v;
    Integer progressvalue;
    String message = "0";
    int SERVERPORT = 6000;
    String SERVER_IP = "192.168.1.100";
    Integer ic = 1;
    Socket socket;
    Integer x = 1;
    ProgressBar progressBar;
    private int i = 0;
    Timer timer;
    TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan_speed);
        ic = 1;
        seekBar2 = (SeekBar)findViewById(R.id.seekBar2);
        fanSpeedDisplay = (TextView)findViewById(R.id.fanSpeedDisplay);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        SharedPreferences portip = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ipr = portip.getString("IP", "192.168.1.100");
        int portr = portip.getInt("Port", 6000);
        //Log.d("test", test1);
        SERVER_IP = ipr;
        SERVERPORT = portr;

        startTimer();
        if(i == 0) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setMax(3);
        }
        else if (i< progressBar.getMax()){
            progressBar.setProgress(i);
            i = i + 1;
        }
        root = (ScrollView)findViewById(R.id.root);
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressvalue = progress;
                percentage_display = (progress/9);
                value = (1.55*percentage_display + 38);
                v = value.intValue();
                root.setBackgroundColor(Color.argb(255,28,v,238));
                fanSpeedDisplay.setText(String.valueOf(percentage_display) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                message = Integer.toString(progressvalue);

            }
        });
    }

    @Override
    public void onBackPressed() {
        message = "fauto";
        if(i == 0) {
            progressBar.setVisibility(View.VISIBLE);
            seekBar2.setVisibility(View.GONE);
            progressBar.setMax(3);
        }
        else if (i< progressBar.getMax()){
            progressBar.setProgress(i);
            i = i + 1;
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // do something
                ic = 0;
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);

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
                if(responce.equals("fmanual")){
                    progressBar.setProgress(0);
                    //i = 0;

                    progressBar.setVisibility(View.GONE);
                    i = 0;
                    seekBar2.setVisibility(View.VISIBLE);
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
                    InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

                    socket = new Socket(serverAddr, SERVERPORT);

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
