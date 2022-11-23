package org.codebase.fingerprintattendancerecord.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.codebase.fingerprintattendancerecord.R;
import org.codebase.fingerprintattendancerecord.models.AttendanceModel;

import java.util.ArrayList;
import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder> {

    Context context;
    private List<AttendanceModel> attendanceModels = new ArrayList<>();

    public AttendanceAdapter(Context context) {
        this.context = context;
    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<AttendanceModel> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        attendanceModels = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_item_layout, parent, false);
        return new AttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceViewHolder holder, int position) {
        AttendanceModel model = attendanceModels.get(position);

        Log.e("size", String.valueOf(attendanceModels.size()));
        Log.e("id", String.valueOf(model.getId()));
        holder.name.setText("Name: "+model.getName());
        holder.fpId.setText("FPId: "+model.getFpId());
        holder.currentDate.setText("Current Date: "+model.getCurrentDate());
        holder.checkInTime.setText("CheckInTime: "+model.getCheckInTime());
        holder.checkInStatus.setText("CheckInStatus: "+model.getCheckInStatus());
        if (model.getCheckOutTime().equals("")) {
            holder.checkOutTime.setText("CheckOutTime: "+"Pending");
        } else {
            holder.checkOutTime.setText("CheckOutTime: "+model.getCheckOutTime());
        }
        holder.checkOutStatus.setText("CheckOutStatus: "+model.getCheckOutStatus());
    }

    @Override
    public int getItemCount() {
        return attendanceModels.size();
    }

    public static class AttendanceViewHolder extends RecyclerView.ViewHolder {

        TextView name, fpId, currentDate, checkInTime, checkInStatus, checkOutTime, checkOutStatus;

        public AttendanceViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nameId);
            fpId = itemView.findViewById(R.id.fpIdId);
            currentDate = itemView.findViewById(R.id.currentDateId);
            checkInTime = itemView.findViewById(R.id.checkInTimeId);
            checkInStatus = itemView.findViewById(R.id.checkInStatusId);
            checkOutTime = itemView.findViewById(R.id.checkOutTimeId);
            checkOutStatus = itemView.findViewById(R.id.checkOutStatusId);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<AttendanceModel> friendList) {
        this.attendanceModels = friendList;
        notifyDataSetChanged();
    }
}
