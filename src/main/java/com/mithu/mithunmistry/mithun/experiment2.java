package com.mithu.mithunmistry.mithun;

        import org.achartengine.GraphicalView;

        import android.app.Activity;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.preference.PreferenceManager;
        import android.view.View;
        import android.view.WindowManager;

        import java.io.BufferedWriter;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.OutputStreamWriter;
        import java.io.PrintWriter;
        import java.net.InetAddress;
        import java.net.Socket;
        import java.net.UnknownHostException;
        import java.util.Calendar;
        import java.util.Timer;
        import java.util.TimerTask;

public class experiment2 extends Activity {

    private static GraphicalView view;
    private LineGraph line = new LineGraph();
    private static Thread thread;
    Timer timer;
    TimerTask timerTask;
    int SERVERPORT = 6000;
    String SERVER_IP = "192.168.1.100";
    Socket socket;
    Integer x = 1;
    Integer ic = 1;
    String message = "graphtemp";
    static int c = 30;
    Calendar cl;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment2);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        SharedPreferences portip = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ipr = portip.getString("IP", "192.168.1.100");
        int portr = portip.getInt("Port", 6000);
        //Log.d("test", test1);
        SERVER_IP = ipr;
        SERVERPORT = portr;
        ic = 1;
        startTimer();

        thread = new Thread() {
            public void run() {
                for (int i = 0; ; i++) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Point p = getdata(i); // We got new data!
                    line.addNewPoints(p); // Add it to our graph
                    view.repaint();
                }
            }
        };
        thread.start();
    }

    @Override
    public void onBackPressed() {
        ic = 0;
        Intent intent = new Intent(this, TemperatureActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        view = line.getView(this);
        setContentView(view);
    }
    public void startTimer() {
        //set a new Timer
        timer = new Timer();
        //initialize the TimerTask's job
        initializeTimerTask();
        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 100, 4000); //
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
            if (ic.intValue() == x.intValue()) {

                if (responce != null) {
                    c = Integer.parseInt(responce);


                } else {
                    //c = 30;
                    //message = "graphtemp";
                }


            }
            else{

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

    public static Point getdata(int x)
    {
        return new Point(x, c);
    }


}