package org.codebase.fingerprintattendancerecord.showattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.codebase.fingerprintattendancerecord.R;

public class AttendanceRecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_record);

        getSupportActionBar().hide();

    }
}