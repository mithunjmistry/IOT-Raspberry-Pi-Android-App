package com.mithu.mithunmistry.mithun;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class NewSocketActivity extends AppCompatActivity {
    EditText newSocketText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_socket);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        newSocketText = (EditText) findViewById(R.id.newSocketText);
        mSocket.connect();
        mSocket.on("new message", onNewMessage);
        listen();





        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.1.101:5002");
        } catch (URISyntaxException e) {}
    }

    public void attemptSend(View view) {
        String message = newSocketText.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            return;
        }

        newSocketText.setText("");
        mSocket.emit("chat message", message);
    }

    public void listen(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // do something
                mSocket.emit("chat message", "cool");
                listening();
            }
        }, 1);
    }

    public void listening() {
           // mSocket.on("new message", onNewMessage);
        Log.d("running continously", "indefinite");
            listen();
    }


    private Runnable predrePhotoRunnable = new Runnable() {
        public void run() {

        }
    };

    public Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Log.d("loop", "entered listening loop");
            (NewSocketActivity.this).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String data = (String) args[0];
                    /*String username;
                    //String message;
                    try {
                        username = data.getString("mithun");
                        //message = data.getString("message");
                    } catch (JSONException e) {
                        return;
                    }

                    // add the message to view*/
                    newSocketText.setText(data);
                }
            });
        }
    };
}
