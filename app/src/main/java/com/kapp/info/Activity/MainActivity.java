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
                String random = String.valueOf(new Random().nextLong());
                mActiveCode.setText(random);
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

                break;
        }
    }
}
