package org.codebase.fingerprintattendancerecord.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.codebase.fingerprintattendancerecord.R;
import org.codebase.fingerprintattendancerecord.models.RegisteredFPModel;
import org.codebase.fingerprintattendancerecord.showattendance.AttendanceRecordActivity;

import java.util.ArrayList;
import java.util.List;

public class RegisteredFpAdapter extends RecyclerView.Adapter<RegisteredFpAdapter.RegisteredFpViewHolder> {

    Activity context;
    private List<RegisteredFPModel> list = new ArrayList<>();

    public RegisteredFpAdapter(Activity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RegisteredFpAdapter.RegisteredFpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.registered_fp_record_layout, parent, false);
        return new RegisteredFpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegisteredFpAdapter.RegisteredFpViewHolder holder, int position) {

        RegisteredFPModel fpModel = list.get(position);

        Log.e("id ", fpModel.getFpId());
        holder.registeredUserName.setText(fpModel.getName());
        holder.registeredFpId.setText(fpModel.getFpId());
        holder.registeredDate.setText(fpModel.getRegisterDate());

        holder.cardView.setOnClickListener(view -> {
            Intent intent = new Intent(context, AttendanceRecordActivity.class);
            intent.putExtra("FP_ID", fpModel.getFpId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class RegisteredFpViewHolder extends RecyclerView.ViewHolder {

        TextView registeredUserName, registeredFpId, registeredDate;
        CardView cardView;
        public RegisteredFpViewHolder(@NonNull View itemView) {
            super(itemView);

            registeredUserName = itemView.findViewById(R.id.registeredNameId);
            registeredFpId = itemView.findViewById(R.id.registeredFpIdId);
            registeredDate = itemView.findViewById(R.id.registeredDateId);
            cardView = itemView.findViewById(R.id.registeredCVId);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setRegisteredData(List<RegisteredFPModel> fpModels) {
        this.list = fpModels;
        notifyDataSetChanged();
    }
}
