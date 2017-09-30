package com.mithu.mithunmistry.mithun;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button access;
    CheckBox loggedIn;
    String uname = "mithunjmistry";
    String pwd = "raspberry";
    Boolean cbChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText)findViewById(R.id.editText);
        password = (EditText)findViewById(R.id.editText2);
        //loggedIn = (CheckBox)findViewById(R.id.checkBox);
        username.setText("mithunjmistry");
        password.setFocusable(true);
        password.setFocusableInTouchMode(true);
        password.requestFocus();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Default password is 'raspberry'", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void attemptLogin(View view)
    {
        //loggedIn = (CheckBox)findViewById(R.id.checkBox);
        String entereduname = username.getText().toString();
        String enteredpwd = password.getText().toString();

        if(uname.equals(entereduname) && pwd.equals(enteredpwd))
        {
            /*if (loggedIn.isChecked()) {
                SharedPreferences logactivity = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = logactivity.edit();
                editor.putBoolean("Login Check", false);
                editor.commit();
                Intent intent = new Intent(this, Main2Activity.class);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(this, Main2Activity.class);
                startActivity(intent);
            }*/
        login();
        }
        else
        {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            password.setText("");

            dlgAlert.setMessage("Incorrect password or username");
            dlgAlert.setTitle("Try Again");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();

            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
        }

    }

    @Override
    public void onBackPressed() {
        new android.support.v7.app.AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent1 = new Intent(Intent.ACTION_MAIN);
                        intent1.addCategory(Intent.CATEGORY_HOME);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                    }
                }).show();

    }

    private void login(){
        loggedIn = (CheckBox)findViewById(R.id.checkBox);
        if (loggedIn.isChecked()) {
            SharedPreferences logactivity = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = logactivity.edit();
            editor.putBoolean("Login Check", false);
            editor.commit();
            Intent intent = new Intent(this, IpActivity.class);
            startActivity(intent);
        }
        else{
            SharedPreferences logactivity1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = logactivity1.edit();
            editor.putBoolean("Login Check Transient", false);
            editor.commit();
            Intent intent = new Intent(this, IpActivity.class);
            startActivity(intent);
        }
    }
}
