package com.mithu.mithunmistry.mithun;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class OutputcontrolActivity extends AppCompatActivity {
    private static final int SERVERPORT = 6000;
    private static final String SERVER_IP = "192.168.1.100";
    //static final String TAG = "SymmmetricAlgorithmAES";
    private Socket socket;
    Button b1, b2, b3, b4, b5, b6, b7, b8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outputcontrol);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        b1 = (Button)findViewById(R.id.button3);
        b2 = (Button)findViewById(R.id.button4);
        b3 = (Button)findViewById(R.id.button5);
        b4 = (Button)findViewById(R.id.button6);
        b5 = (Button)findViewById(R.id.button7);
        b6 = (Button)findViewById(R.id.button8);
        b7 = (Button)findViewById(R.id.button9);
        b8 = (Button)findViewById(R.id.button10);
        new Thread(new ClientThread()).start();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
                if (test.equals("hello")){
                    Log.i("data is being rec", "the data is being rec");
                }
                else{
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
    public void onBackPressed(){
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


    public void led1(View view) {
        if (b1.getText().equals("LED 1 ON")) {
            try {
                String str = "ledone";
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
            b1.setText("LED 1 OFF");
        }
        else{
            try {
                String str = "oledone";
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
            b1.setText("LED 1 ON");

        }
    }
    public void led2(View view) {
        if (b2.getText().equals("LED 2 ON")) {
            try {
                String str = "ledtwo";
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
            b2.setText("LED 2 OFF");
        }

        else{
            try {
                String str = "oledtwo";
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
            b2.setText("LED 2 ON");

        }
    }
    public void led3(View view){
        if (b3.getText().equals("LED 3 ON")) {
            try {
                String str = "ledthree";
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
            b3.setText("LED 3 OFF");
        }

        else{
            try {
                String str = "oledthree";
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
            b3.setText("LED 3 ON");

        }
    }
    public void led4(View view){
        if (b4.getText().equals("LED 4 ON")) {
            try {
                String str = "ledfour";
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
            b4.setText("LED 4 OFF");
        }
        else{
            try {
                String str = "oledfour";
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
            b4.setText("LED 4 ON");

        }
    }
    public void led5(View view){
        if (b5.getText().equals("LED 5 ON")) {
            try {
                String str = "ledfive";
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
            b5.setText("LED 5 OFF");
        }
        else{
            try {
                String str = "oledfive";
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
            b5.setText("LED 5 ON");

        }
    }
    public void led6(View view){
        if (b6.getText().equals("LED 6 ON")) {
            try {
                String str = "ledsix";
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
            b6.setText("LED 6 OFF");
        }
        else{
            try {
                String str = "oledsix";
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
            b6.setText("LED 6 ON");

        }
    }
    public void led7(View view){
        if (b7.getText().equals("LED 7 ON")) {
            try {
                String str = "ledseven";
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
            b7.setText("LED 7 OFF");
        }
        else{
            try {
                String str = "oledseven";
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
            b7.setText("LED 7 ON");

        }
    }
    public void allLed(View view){
        if (b8.getText().equals("ALL LED ON")) {
            try {
                String str = "all";
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
            b8.setText("ALL LED OFF");
        }
        else{
            try {
                String str = "oall";
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
            b8.setText("ALL LED ON");

        }
    }

}
