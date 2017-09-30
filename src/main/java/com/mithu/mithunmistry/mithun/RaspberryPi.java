package com.mithu.mithunmistry.mithun;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

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

public class RaspberryPi extends AppCompatActivity {
    Socket socket;
    static final int SERVERPORT = 6000;
    String SERVER_IP = "192.168.1.100";
    String message = "hello";
    TextView tempDisplay, voicePi;
    Timer timer;
    TimerTask timerTask;
    Boolean i = true;
    Boolean j = false;
    Button userChoiceTemp;
    Button b1,b2,b3;
    String[] all_messages = {"ledone", "ledtwo", "ledthree"};
    String backup;
    TextView forLed1, forLed2, forLed3;
    ImageButton vCommand;
    EditText ipAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raspberry_pi);
        tempDisplay = (TextView)findViewById(R.id.tempDisplay);
        //voicePi = (TextView)findViewById(R.id.voicePi);
        userChoiceTemp = (Button)findViewById(R.id.button12);
        b1 = (Button)findViewById(R.id.buttonLED1);
        b2 = (Button)findViewById(R.id.buttonLED2);
        b3 = (Button)findViewById(R.id.buttonLED3);
        forLed1 = (TextView)findViewById(R.id.textView27);
        forLed2 = (TextView)findViewById(R.id.textView28);
        forLed3 = (TextView)findViewById(R.id.textView29);
        ipAddress = (EditText)findViewById(R.id.ipAddress);
        //vCommand = (ImageButton) findViewById(R.id.voiceCommand);
        startTimer();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Made by Mithun", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
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

    public void led1(View view) {
        if (b1.getText().equals("LED 1 ON")) {
            //j = true;
            message = "ledone";
            b1.setText("LED 1 OFF");
            forLed1.setText("LED 1 is On");
            forLed1.setTextColor(Color.GREEN);
            //if(timer == null){
            //    startTimer();
            //}
       }
       else{
            //j = true;
            message = "oledone";
            b1.setText("LED 1 ON");
            forLed1.setText("LED 1 is Off");
            forLed1.setTextColor(Color.RED);
            //if(timer == null){
            //    startTimer();
            }
       // }
    }
    public void led2(View view) {
        if (b2.getText().equals("LED 2 ON")) {
            message = "ledtwo";
            b2.setText("LED 2 OFF");
            forLed2.setText("LED 2 is On");
            forLed2.setTextColor(Color.GREEN);
        }

        else{
            message = "oledtwo";
            b2.setText("LED 2 ON");
            forLed2.setText("LED 2 is Off");
            forLed2.setTextColor(Color.RED);

        }
    }
    public void led3(View view) {
        if (b3.getText().equals("LED 3 ON")) {
            message = "ledthree";
            b3.setText("LED 3 OFF");
            forLed3.setText("LED 3 is On");
            forLed3.setTextColor(Color.GREEN);
        } else {
           message = "oledthree";
           b3.setText("LED 3 ON");
            forLed3.setText("LED 3 is Off");
            forLed3.setTextColor(Color.RED);

        }
    }

    public void getTemp(View view){
        if(i){
            i = false;
            message = "gettemp";
            userChoiceTemp.setText("STOP");
            if(timer == null) {
                startTimer();
            }
        }
        else{
            i = true;
            userChoiceTemp.setText("START");
            message = "hello";
        }
    }

    public void setIP(View view){
        SERVER_IP = ipAddress.getText().toString();
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
                if (i) {
                    message = "hello";
                } else {
                    message = "gettemp";
                    tempDisplay.setText(responce + " °C");
                    backup = responce;

                }
            }
            else{
                tempDisplay.setText(backup + " °C");
            }

            /*if (responce != null) {
                if (responce.equals("successone")) {
                    if (b1.getText().equals("LED 1 ON")) {
                        b1.setText("LED 1 OFF");
                    } else {
                        b1.setText("LED 1 ON");
                    }

                } else {
                    tempDisplay.setText(responce + " °C");
                    backup = tempDisplay.getText().toString();
                }
                if (responce.equals("successtwo")) {
                    if (b2.getText().equals("LED 2 ON")) {
                        b2.setText("LED 2 OFF");
                    } else {
                        b2.setText("LED 2 ON");
                    }

                } else {
                    tempDisplay.setText(responce + " °C");
                    backup = tempDisplay.getText().toString();
                }
                if (responce.equals("successthree")) {
                    if (b3.getText().equals("LED 3 ON")) {
                        b3.setText("LED 3 OFF");
                    } else {
                        b3.setText("LED 3 ON");
                    }

                } else {
                    tempDisplay.setText(responce + " °C");
                    backup = tempDisplay.getText().toString();
                }

            if (responce != null && responce.equals("successone") && i.equals(false)) {
                message = "gettemp";
            }
            if (responce != null && responce.equals("successone") && i.equals(true)) {
                message = "hello";
            }
            if (responce != null && responce.equals("successtwo") && i.equals(false)) {
                message = "gettemp";
            }
            if (responce != null && responce.equals("successtwo") && i.equals(true)) {
                message = "hello";
            }
            if (responce != null && responce.equals("successthree") && i.equals(false)) {
                message = "gettemp";
            }
            if (responce != null && responce.equals("successthree") && i.equals(true)) {
                message = "hello";
            }
        }
            else{
                tempDisplay.setText(backup);
            }*/
            }
            //tempDisplay.setText(responce);
           // if (responce != null) {
            //    if (responce.equals("success_2")) {
             //       message = "hello";
            //    }
        //    }
            //new Thread(new ClientThread()).start();
            /*try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/


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

        }

    }

}
