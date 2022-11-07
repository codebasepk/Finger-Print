package org.codebase.fingerprintattendancerecord.showattendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.codebase.fingerprintattendancerecord.R;
import org.codebase.fingerprintattendancerecord.adapter.AttendanceAdapter;
import org.codebase.fingerprintattendancerecord.databinding.ActivityAttendanceRecordBinding;

public class AttendanceRecordActivity extends AppCompatActivity {

    private ActivityAttendanceRecordBinding recordBinding;
    private AttendanceAdapter attendanceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recordBinding = ActivityAttendanceRecordBinding.inflate(getLayoutInflater());
        setContentView(recordBinding.getRoot());

        getSupportActionBar().hide();

        recordBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        attendanceAdapter = new AttendanceAdapter(this);
        recordBinding.recyclerView.setAdapter(attendanceAdapter);

    }
}