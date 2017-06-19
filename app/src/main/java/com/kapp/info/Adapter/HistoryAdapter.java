package com.kapp.info.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kapp.info.Entity.ActiveCode;
import com.kapp.info.activeapp.R;

import java.util.List;

/**
 * Created by Naser on 6/17/2017.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private Context mContext;
    private List<ActiveCode> activeCodeList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName, txtPhone, txtKeyCode , txtCode , txtDate;
        public LinearLayout itemlayout;
        public MyViewHolder(View view) {
            super(view);
            txtName = (TextView) view.findViewById(R.id.txtName);
            txtPhone = (TextView) view.findViewById(R.id.txtPhone);
            txtKeyCode = (TextView) view.findViewById(R.id.txtKeyCode);
            txtCode = (TextView) view.findViewById(R.id.txtCode);
            txtDate = (TextView) view.findViewById(R.id.txtDate);
        }
    }

    public HistoryAdapter(Context mContext, List<ActiveCode> activeCodes) {
        this.mContext = mContext;
        this.activeCodeList = activeCodes;
    }

    @Override
    public HistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activecode_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.MyViewHolder holder, int position) {
        final ActiveCode activeCode = activeCodeList.get(position);

        holder.txtName.setText(activeCode.getName());
        holder.txtPhone.setText(activeCode.getMobileNumber());
        holder.txtKeyCode.setText(activeCode.getKeyCode());
        holder.txtCode.setText(activeCode.getActiveCode());
        holder.txtDate.setText(activeCode.getCreateDate());

    }

    @Override
    public int getItemCount() {
        return activeCodeList.size();
    }
}
