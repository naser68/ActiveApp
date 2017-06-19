package com.kapp.info.Activity;

import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        dbHelper = new DBHelper(HistoryActivity.this);
        activeCodes = dbHelper.getAllActiveCode();

        txtMessage = (TextView) findViewById(R.id.txtMessage);
        recyclerView = (RecyclerView) findViewById(R.id.listHistory);
        if (activeCodes == null || activeCodes.size() == 0) {
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
                    public void onLongItemClick(View view, final int position) {
                        // do whatever
                        new AlertDialog.Builder(new ContextThemeWrapper(HistoryActivity.this, R.style.myDialog))
                                .setTitle("پیغام")
                                .setMessage("آیا برای حذف مطمئن هستید؟")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        int id = activeCodes.get(position).getId();
                                        if (dbHelper.deleteActiveCode(id) > 0) {
                                            Toast.makeText(HistoryActivity.this, "با موفقیت حذف شد", Toast.LENGTH_SHORT).show();
                                            activeCodes = dbHelper.getAllActiveCode();
                                            historyAdapter = new HistoryAdapter(HistoryActivity.this, activeCodes);
                                            recyclerView.setAdapter(historyAdapter);
                                            try {
                                                recyclerView.getLayoutManager().scrollToPosition(position-1);
                                            }catch (Exception ex){
                                                ex.printStackTrace();
                                            }
                                            historyAdapter.notifyDataSetChanged();
                                        } else {
                                            Toast.makeText(HistoryActivity.this, "خطا موقع حذف اطلاعات رخ داده است", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                })
                                .setNegativeButton("خیر", null).show();
                    }
                })
        );
    }
}
