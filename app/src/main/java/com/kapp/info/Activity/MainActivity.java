package com.kapp.info.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kapp.info.Db.DBHelper;
import com.kapp.info.activeapp.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mActiveCode;
    private TextView mTextMessage;

    private EditText etKeyCode;
    private EditText etName;
    private EditText etMobileNumber;

    private Button btnActive;
    private Button btnSend;
    private Button btnCopy;

    private LinearLayout activePanel;

    private boolean isNew = true;

    private DBHelper dbHelper;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            activePanel.setVisibility(View.GONE);
            switch (item.getItemId()) {
                case R.id.navigation_history:
                    Intent mintent = new Intent(MainActivity.this, HistoryActivity.class);
                    startActivity(mintent);
                    return true;
                case R.id.navigation_new:
                    mTextMessage.setText(R.string.active_new);
                    mTextMessage.setTextColor(Color.GREEN);
                    isNew = true;
                    return true;
                case R.id.navigation_old:
                    mTextMessage.setText(R.string.active_old);
                    mTextMessage.setTextColor(Color.BLUE);
                    isNew = false;
                    return true;
                /*case R.id.navigation_send:
                    Intent sintent = new Intent(MainActivity.this, SendActivity.class);
                    startActivity(sintent);
                    return true;*/
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(getApplicationContext());

        activePanel = (LinearLayout) findViewById(R.id.activePanel);

        mTextMessage = (TextView) findViewById(R.id.activeType);
        mActiveCode = (TextView) findViewById(R.id.activeCode);

        etKeyCode = (EditText) findViewById(R.id.etKeyCode);
        etName = (EditText) findViewById(R.id.etName);
        etMobileNumber = (EditText) findViewById(R.id.etMobileNumber);

        btnActive = (Button) findViewById(R.id.btnActive);
        btnCopy = (Button) findViewById(R.id.btnCopy);
        btnSend = (Button) findViewById(R.id.btnSend);

        btnActive.setOnClickListener(this);
        btnCopy.setOnClickListener(this);
        btnSend.setOnClickListener(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_new);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnActive:
                if (etKeyCode.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "لطفا کلید تبادل را وارد نمایید", Toast.LENGTH_LONG).show();
                    return;
                }
                String hash =  md5(etKeyCode.getText().toString());

                String result = "";
                if(isNew) {
                    result = hash.substring(2,3) + hash.substring(10,11)  + hash.substring(7,8) + hash.substring(17,18) +
                            hash.substring(20,21)+ hash.substring(15,16)  + hash.substring(23,24) + hash.substring(12,13) ;
                }else{
                    result = hash.substring(2, 3) + hash.substring(7, 8) + hash.substring(17, 18) +
                            hash.substring(20, 21) + hash.substring(23, 24) + hash.substring(12, 13);
                }
                mActiveCode.setText(result);
                activePanel.setVisibility(View.VISIBLE);
                break;
            case R.id.btnCopy:
                if (etName.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "لطفا نام را وارد نمایید", Toast.LENGTH_LONG).show();
                    return;
                }
                if (etMobileNumber.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "لطفا شماره موبایل را وارد نمایید", Toast.LENGTH_LONG).show();
                    return;
                }


                dbHelper.insertActiveCode(etName.getText().toString(), etMobileNumber.getText().toString(), etKeyCode.getText().toString(), mActiveCode.getText().toString());

                break;
            case R.id.btnSend:
                if (etName.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "لطفا نام را وارد نمایید", Toast.LENGTH_LONG).show();
                    return;
                }
                if (etMobileNumber.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "لطفا شماره موبایل را وارد نمایید", Toast.LENGTH_LONG).show();
                    return;
                }

                dbHelper.insertActiveCode(etName.getText().toString(), etMobileNumber.getText().toString(), etKeyCode.getText().toString(), mActiveCode.getText().toString());

                String shareBody = mActiveCode.getText().toString();
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "ارسال کد فعال سازی");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));

                break;
        }
    }

    public  String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(URLEncoder.encode(s, "utf-8").getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
