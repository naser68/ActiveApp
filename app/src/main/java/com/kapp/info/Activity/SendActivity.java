package com.kapp.info.Activity;

import android.bluetooth.BluetoothDevice;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kapp.info.activeapp.R;

import java.io.File;

public class SendActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSend:
                String path = getAssets() + "/text.txt" ;

                File file = new File(path);

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                startActivity(intent);
                return;
        }
    }
}
