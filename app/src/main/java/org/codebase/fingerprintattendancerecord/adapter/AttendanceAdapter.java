package org.codebase.fingerprintattendancerecord.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.codebase.fingerprintattendancerecord.R;
import org.codebase.fingerprintattendancerecord.models.AttendanceModel;

import java.util.ArrayList;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder> {

    Context context;
    ArrayList<AttendanceModel> attendanceModels;

    public AttendanceAdapter(Context context, ArrayList<AttendanceModel> attendanceModels) {
        this.context = context;
        this.attendanceModels = attendanceModels;
    }

    @NonNull
    @Override
    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_item_layout, parent, false);
        return new AttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return attendanceModels.size();
    }

    public class AttendanceViewHolder extends RecyclerView.ViewHolder {
        public AttendanceViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
