package com.mithu.mithunmistry.mithun;

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

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

public class Main22Activity extends Activity {

    private Socket socket;

    private static final int SERVERPORT = 6000;
    private static final String SERVER_IP = "192.168.1.100";
    TextView risp;
    Timer timer;
    TimerTask timerTask;
    String message = "hello";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main22);
        risp = (TextView) findViewById(R.id.display);
        //new Thread(new ClientThread()).start();
           // new ConnectionTask().execute();
        startTimer();


    }

    public void startTimer() {
        //set a new Timer
        timer = new Timer();
        //initialize the TimerTask's job
        initializeTimerTask();
        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 5000, 3000); //
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

            public void onClick(View view) {
                //new ConnectionTask().execute();
                new Thread(new ClientThread()).start();
                try {
                    String str = "hello";

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

            public void clear22(View view) {
                //new ConnectionTask().execute();
               message = "hi";

//                if(risp.getText().equals("success_2")){
//                    startTimer();
//                }
//                else{
//                    clear22(view);
//                }

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
                    risp.setText(responce);
                    if (responce != null) {
                        if (responce.equals("success_2")) {
                            message = "hello";
                        }
                    }
                    //new Thread(new ClientThread()).start();
            /*try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/

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

                }

            }
        }