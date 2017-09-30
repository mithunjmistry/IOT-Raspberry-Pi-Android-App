package com.mithu.mithunmistry.mithun;

import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity {


    TextView fanStatus;
    TextView lightStatus;
    TextView vView;
    ImageButton vCommand;
    Button lightbtn, raspberryPi;
    Button fanbtn;
    Boolean background = false;
    Integer i = 0;
    Integer j = 0;
    Integer y = 0;
    Integer x = 1;
    Integer m = 0;
    Integer l = 3;
    Integer ic = 1;
    Socket socket;
    int SERVERPORT = 6000;
    String SERVER_IP = "192.168.1.100";
    String message = "hello";
    Timer timer;
    TimerTask timerTask;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    protected String[] responsesDoor = new String[4];
    protected String[] responsesFanON = {"on fan", "switch on fan", "activate fan", "fan on", "switch fan on",
            "initialize fan", "initialise fan", "turn on fan", "fan turn on", "turn fan on", "start fan", "switch on the fan",
    "switch the fan on", "turn on the fan", "turn the fan on"};
    protected String[] responsesFanOff = {"off fan", "switch off fan", "deactivate fan", "fan off", "switch fan off",
            "deinitialize fan", "deinitialise fan", "turn off fan", "fan turn off", "turn fan off", "stop fan", "switch off the fan",
            "switch the fan off", "turn off the fan", "turn the fan off"};
    protected String[] responsesLightON = {"on light", "switch on light", "activate light", "light on", "switch light on",
            "initialize light", "initialise light", "turn on light", "light turn on", "turn light on", "start light",
            "on lights", "switch on lights",
            "activate lights", "lights on", "switch lights on",
            "initialize lights", "initialise lights", "turn on lights", "lights turn on", "turn lights on", "start lights",
    "turn on the lights", "turn the lights on", "switch on the lights", "switch the lights on",
            "turn on the light", "turn the light on", "switch on the light", "switch the light on"};
    protected String[] responsesLightOff = {"off light", "switch off light", "deactivate light", "light off", "switch light off",
            "deinitialize light", "deinitialise light", "turn off light", "light turn off", "turn light off", "stop light",
            "off lights", "switch off lights", "deactivate lights", "lights off", "switch lights off",
            "deinitialize lights", "deinitialise lights", "turn off lights", "lights turn off", "turn lights off", "stop lights",
            "turn off the lights", "turn the lights off", "switch off the lights", "switch the lights off",
            "turn off the light", "turn the light off", "switch off the light", "switch the light off"};
    protected String[] vDeveloper = {"about developer", "developer", "made by", "developed by", "developer name",
    "developer's mail", "developer's name", "developer mail", "developer email", "developer e-mail", "developers", "developer team",
    "developers name", "developers email", "developers mail", "developers team"};
    protected String[] vHelp = {"help", "voice command help", "assist", "assistance", "please help"};
    protected String[] vLogout = {"logout", "log out", "sign out", "sign off", "bye", "goodbye", "see ya", "see yaa"};
    protected String[] extraFan = {"fan control", "extra fan settings", "fan extra settings","extra fan setting", "fan extra setting",
    "speed setting", "fan speed setting", "speed settings", "fan speed settings","speed extra settings", "speed extra setting",
            "fan speed extra settings", "fan speed extra setting", "speed control", "extra speed settings", "extra speed setting"};
    protected String[] extraLight = {"light control", "extra light settings", "light extra settings","extra light setting", "light extra setting",
            "intensity setting", "light intensity setting", "intensity settings", "light intensity settings","intensity extra settings", "intensity extra setting",
            "light intensity extra settings", "light intensity extra setting", "intensity control", "extra intensity setting", "extra intensity settings"};
    protected String[] changeIp = {"change ip", "change port", "change ip and port", "change ip & port", "change port and ip", "change port & ip",
    "ip change", "port change", "port and ip change", "port & ip change", "ip and port change", "ip & port change", "settings change",
    "setting change", "change settings", "change setting", "change the settings", "change the setting", "setting", "settings", "change ip address",
    "change port address", "change port number", "change the ip address", "change the port address", "change the port number", "change the ip address and port number",
            "change the ip address and port address","change the ip address & port number", "change the ip address & port address"};
    protected String[] getParameters = {"get other parameters", "other parameters", "other", "get the other parameters", "other parameter",
    "get other parameter", "list other parameters", "list other parameter", "view other parameters", "view other parameter", "view parameters",
    "view parameter", "others", "parameter", "parameters", "other quantities", "other quantity", "other things", "other thing","other things available",
    "other thing available", "extra parameters", "extra parameter", "more parameters", "more parameter", "more quantities", "more quantity",
    "more thing", "more things"};
    protected String[] temperature = {"get temperature", "what is the temperature", "what's current temperature", "what's temperature",
    "tell me current temperature", "open temperature", "temperature", "tell me how cool it is", "tell me how hot it is","tell me how cool is it", "tell me how hot is it",
    "current temperature", "temperature now", "temperature currently", "how cool it is", "how hot it is", "how cool is it", "how hot is it",
            "how much cool it is", "how much hot it is", "how much is the temperature", "how much is temperature", "what is temperature",
    "temperature get", "what is the temperature currently", "what is current temperature", "tell me the temperature", "tell me temperature",
    "tell me current temperature", "tell me the current temperature", "tell temperature", "temperature status"};
    protected String[] humidity = {"get humidity", "what is the humidity", "what's current humidity", "what's humidity",
            "tell me current humidity", "open humidity", "humidity", "tell me how humid it is", "tell me how humid is it",
            "current humidity", "humidity now", "humidity currently", "how humid it is", "how humid is it",
            "how much humid it is", "how much is the humidity", "how much is humidity", "what is humidity",
            "humidity get", "what is the humidity currently", "what is current humidity", "tell me the humidity", "tell me humidity",
            "tell me current humidity", "tell me the current humidity", "tell humidity"};
    protected String[] level = {"level", "water level", "water tank level", "how much water is available in the tank", "how much water is available in tank",
    "water available in tank", "water in tank", "water available", "water present", "water present in tank", "water present in the tank", "water tank",
    "available water", "present water", "water currently available", "available water currently", "current tank status", "tank status",
    "water tank status", "level status", "current water tank status", "water status", "water status currently", "water status presently",
    "tank level", "water availability"};
    protected String[] moisture = {"moisture", "soil moisture", "moisture soil", "soil moisture percentage", "my soil condition", "soil condition",
    "condition of my soil", "what is my soil condition", "what is condition of my soil", "is my soil dry", "is my soil wet", "is my soil alright",
    "soil status", "status of my soil", "moisture status", "soil moisture status", "current moisture", "moisture currently", "how is my farm", "soil", "what is the condition of my soil"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main2);
        SharedPreferences portip = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ipr = portip.getString("IP", "192.168.1.100");
        int portr = portip.getInt("Port", 6000);
        //Log.d("test", test1);
        SERVER_IP = ipr;
        SERVERPORT = portr;

            fanStatus = (TextView) findViewById(R.id.fanStatus);
            lightStatus = (TextView) findViewById(R.id.lightStatus);
            vCommand = (ImageButton) findViewById(R.id.voiceCommand);
            vView = (TextView) findViewById(R.id.voiceView);
            lightbtn = (Button)findViewById(R.id.lightbtn);
            fanbtn = (Button)findViewById(R.id.fanbtn);
        ic = 1;
            startTimer();


            responsesDoor[0] = "open the door";
            responsesDoor[1] = "please open the door";
            responsesDoor[2] = "open door";
            responsesDoor[3] = "door open";

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            lightbtn.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (j.intValue() == x.intValue()) {
                        ic = 0;
                        Intent intent = new Intent(getApplicationContext(), brightness_activity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Switch on the LED first!", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            });

            fanbtn.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View view){
                    if (i.intValue() == x.intValue()) {
                        ic = 0;
                        Intent intent = new Intent(getApplicationContext(), FanSpeed.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Switch on the fan first!", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            });

            vCommand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vView.setText("");
                    promptSpeechInput();
                }
            });

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Developed by Mithun", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }

    @Override
    public void onPause() {
        super.onPause();
        background = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        background = false;
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
                        ic = 0;
                        Intent positiveactivity = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(positiveactivity);
                        finish();
                    }
                });
        logoutConfirm.setNegativeButton("No", null);
        logoutConfirm.create().show();
        }

    public void doorControl(View view){
        //stoptimertask(view);
        ic = 0;
        Intent intent = new Intent(this, Main3Activity.class);
        startActivity(intent);
        finish();
    }

    public void changeIp(View view){
        //stoptimertask(view);
        ic = 0;
        Intent intent = new Intent(this, IpActivity.class);
        startActivity(intent);
        finish();
    }

    public void parameters(View view){
        //stoptimertask(view);
        ic = 0;
        Intent intent = new Intent(this, ParameterActivity.class);
        startActivity(intent);
        finish();
    }

    public void piPage(View view){
        Intent intent = new Intent(this, RaspberryPi.class);
        startActivity(intent);
    }


    public void fanControl(View v) {
        if (i.intValue() == y.intValue()) {
            fanStatus.setText("The fan is On with automatic control");
            message = "onfan";
            i = 1;
        }
        else {
            fanStatus.setText("");
            message = "offfan";
            i = 0;
        }
    }

    public void lightControl(View v) {
        if (j.intValue() == y.intValue()) {
            lightStatus.setText("The light is On with intensity control");
            message = "onlight";
            j = 1;
        }
        else {
            lightStatus.setText("");
            message = "offlight";
            j = 0;
        }
    }

    public void about(View view){
        //stoptimertask(view);
        ic = 0;
        Intent intent = new Intent(this, Main4Activity.class);
        startActivity(intent);
        finish();
    }

    public void voiceHelp(View view){
        //stoptimertask(view);
        ic = 0;
        Intent intent2 = new Intent(this, VoiceHelp.class);
        startActivity(intent2);
        finish();
    }

    public void pid(View view){
        //stoptimertask(view);
        ic = 0;
        Intent intent2 = new Intent(this, PidActivity.class);
        startActivity(intent2);
        finish();
    }

    public void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (
                ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), getString(R.string.speech_not_supported), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    vView.setText(result.get(0));
                }
                break;
            }
        }
        String speechRecvText = vView.getText().toString().toLowerCase().replaceAll("\\s+$", "");
        if (Arrays.asList(responsesDoor).contains(speechRecvText)) {
            m = 0;
            Intent intentdoor = new Intent(this, Main3Activity.class);
            startActivity(intentdoor);
            finish();
        }
        else if ((Arrays.asList(responsesFanON).contains(speechRecvText)) && (i.intValue()== y.intValue())){
            m = 0;
            fanStatus.setText("The fan is On with automatic control");
            message = "onfan";
            i = 1;
        }
        else if ((Arrays.asList(responsesFanON).contains(speechRecvText)) && (i.intValue()== x.intValue())){
            m = 0;
            Toast.makeText(getApplicationContext(), "The fan is already On!", Toast.LENGTH_SHORT).show();
        }
        else if ((Arrays.asList(responsesFanOff).contains(speechRecvText)) && (i.intValue() == x.intValue())){
            m = 0;
            fanStatus.setText("");
            message = "offfan";
            i = 0;
        }
        else if ((Arrays.asList(responsesFanOff).contains(speechRecvText)) && (i.intValue() == y.intValue())){
            m = 0;
            Toast.makeText(getApplicationContext(), "The fan is already Off!", Toast.LENGTH_SHORT).show();
        }
        else if ((Arrays.asList(responsesLightON).contains(speechRecvText)) && (j.intValue()== y.intValue())){
            m = 0;
            lightStatus.setText("The light is On with intensity control");
            message = "onlight";
            j = 1;
        }
        else if ((Arrays.asList(responsesLightON).contains(speechRecvText)) && (j.intValue()== x.intValue())){
            m = 0;
            Toast.makeText(getApplicationContext(), "The light is already On!", Toast.LENGTH_SHORT).show();
        }
        else if ((Arrays.asList(responsesLightOff).contains(speechRecvText)) && (j.intValue() == x.intValue())){
            m = 0;
            lightStatus.setText("");
            message = "offlight";
            j = 0;
        }
        else if ((Arrays.asList(responsesLightOff).contains(speechRecvText)) && (j.intValue() == y.intValue())){
            m = 0;
            Toast.makeText(getApplicationContext(), "The light is already Off!", Toast.LENGTH_SHORT).show();
        }
        else if (Arrays.asList(vDeveloper).contains(speechRecvText)) {
            m = 0;
            Intent intentdoor = new Intent(this, Main4Activity.class);
            startActivity(intentdoor);
            finish();
        }
        else if(speechRecvText.equals("")){

        }
        else if (Arrays.asList(vHelp).contains(speechRecvText)) {
            m = 0;
            Intent intentHelp = new Intent(this, VoiceHelp.class);
            startActivity(intentHelp);
            finish();
        }
        else if (m.intValue() == l.intValue()){
            m = 0;
            Intent intentHelpFalse = new Intent(this, VoiceHelp.class);
            startActivity(intentHelpFalse);
            finish();
        }
        else if (Arrays.asList(vLogout).contains(speechRecvText)){
            m = 0;
            onBackPressed();
            /*Intent intentLogout = new Intent(this, MainActivity.class);
            startActivity(intentLogout);*/
        }
        else if (Arrays.asList(extraFan).contains(speechRecvText)){
            m = 0;
            if (i.intValue() == x.intValue()) {
                ic = 0;
                Intent intent = new Intent(this, FanSpeed.class);
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(getApplicationContext(), "Switch on the fan first!", Toast.LENGTH_SHORT).show();
            }
        }
        else if (Arrays.asList(extraLight).contains(speechRecvText)){
            m = 0;
            if (j.intValue() == x.intValue()) {
                ic = 0;
                Intent intent = new Intent(this, brightness_activity.class);
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(getApplicationContext(), "Switch on the LED first!", Toast.LENGTH_SHORT).show();
            }
        }
        else if (Arrays.asList(changeIp).contains(speechRecvText)){
            m = 0;
            Intent intent = new Intent(this, IpActivity.class);
            startActivity(intent);
            finish();
        }
        else if (Arrays.asList(getParameters).contains(speechRecvText)){
            m = 0;
            Intent intent = new Intent(this, ParameterActivity.class);
            startActivity(intent);
            finish();
        }
        else if (Arrays.asList(temperature).contains(speechRecvText)){
            m = 0;
            Intent intent = new Intent(this, TemperatureActivity.class);
            startActivity(intent);
            finish();
        }
        else if (Arrays.asList(humidity).contains(speechRecvText)){
            m = 0;
            Intent intent = new Intent(this, HumidityActivity.class);
            startActivity(intent);
            finish();
        }
        else if (Arrays.asList(level).contains(speechRecvText)){
            m = 0;
            Intent intent = new Intent(this, LevelActivity.class);
            startActivity(intent);
            finish();
        }
        else if (Arrays.asList(moisture).contains(speechRecvText)){
            m = 0;
            Intent intent = new Intent(this, MoistureActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(getApplicationContext(), "This is not a valid command! Refer help & try again..", Toast.LENGTH_SHORT).show();
            m = m.intValue() + x.intValue();
        }
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
                if (responce.equals("fon"))
                {
                    fanStatus.setText("The fan is On with automatic control");
                    lightStatus.setText("");
                    j = 0;
                    message = "hello";
                    i = 1;
                }
                else if (responce.equals("lon")){
                    lightStatus.setText("The light is On with intensity control");
                    fanStatus.setText("");
                    i = 0;
                    message = "hello";
                    j = 1;
                }
                else if (responce.equals("flon")){
                    fanStatus.setText("The fan is On with automatic control");
                    lightStatus.setText("The light is On with intensity control");
                    i = 1;
                    j = 1;
                    message = "hello";
                }
                else if (responce.equals("floff")){
                    fanStatus.setText("");
                    lightStatus.setText("");
                    i = 0;
                    j = 0;
                    message = "hello";
                }
                else{
                    message = "hello";
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
