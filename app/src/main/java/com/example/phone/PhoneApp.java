package com.example.phone;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class PhoneApp extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    private Toolbar mTopToolbar;
    private static final int REQUEST_PHONE_CALL = 1;
    ImageButton sendBtn;
    EditText txtMessage;
    EditText edittext1;
    ImageButton button1;
    String phoneNo;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myphone);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Getting the edittext and button instance
        edittext1=(EditText)findViewById(R.id.editText);
        button1=(ImageButton)findViewById(R.id.btnCall);
        sendBtn = (ImageButton) findViewById(R.id.btnSendSMS);
        txtMessage = (EditText) findViewById(R.id.editText2);

        //Performing action on button click
        button1.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {
                makeCall();
            }



        });

        sendBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(PhoneApp.this,
                        Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PhoneApp.this,
                            new String[] {Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
                } else {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(
                            edittext1.getText().toString(), null, txtMessage.getText().toString(), null, null);

                    Toast.makeText(getApplicationContext(), "SMS sent", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    Intent intent = new Intent(Intent.ACTION_CALL);

    protected void makeCall() {
        String number=edittext1.getText().toString();
        intent.setData(Uri.parse("tel:"+number));
        if (ContextCompat.checkSelfPermission(PhoneApp.this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PhoneApp.this,
                    new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
        }
        else
        {
            startActivity(intent);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {

            case REQUEST_PHONE_CALL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Calling....",
                            Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "Unable to connect", Toast.LENGTH_LONG).show();
                    return;
                }
                return;
            }
        }
    }

}




