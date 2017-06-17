package com.kapp.info.Activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kapp.info.Adapter.HistoryAdapter;
import com.kapp.info.Db.DBHelper;
import com.kapp.info.Entity.ActiveCode;
import com.kapp.info.Util.RecyclerItemClickListener;
import com.kapp.info.activeapp.R;

import java.util.List;

import static java.security.AccessController.getContext;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView txtMessage;
    private HistoryAdapter historyAdapter;
    private List<ActiveCode> activeCodes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        activeCodes = new DBHelper(getApplicationContext()).getAllActiveCode();

        txtMessage = (TextView) findViewById(R.id.txtMessage);
        recyclerView = (RecyclerView) findViewById(R.id.listHistory);
        if(activeCodes == null || activeCodes.size() == 0){
            txtMessage.setText("تاریخچه خالی می باشد");
            recyclerView.setVisibility(View.GONE);
            return;
        }

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyAdapter = new HistoryAdapter(getApplicationContext(), activeCodes);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(historyAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }
}
