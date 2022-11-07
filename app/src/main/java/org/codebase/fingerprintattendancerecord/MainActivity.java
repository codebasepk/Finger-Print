package org.codebase.fingerprintattendancerecord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import org.codebase.fingerprintattendancerecord.databinding.ActivityMainBinding;
import org.codebase.fingerprintattendancerecord.showattendance.AttendanceRecordActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.attendanceRecordId.setOnClickListener(view -> {

            Intent intent = new Intent(this, AttendanceRecordActivity.class);
            startActivity(intent);
        });
    }
}