package org.codebase.fingerprintattendancerecord.attendancefragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.google.android.material.textfield.TextInputEditText;

import org.codebase.fingerprintattendancerecord.MainActivity;
import org.codebase.fingerprintattendancerecord.R;
import org.codebase.fingerprintattendancerecord.adapter.AttendanceAdapter;
import org.codebase.fingerprintattendancerecord.databinding.FragmentTodayAttendanceBinding;
import org.codebase.fingerprintattendancerecord.models.AttendanceModel;
import org.codebase.fingerprintattendancerecord.roomdb.RecordDatabase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import pk.codebase.requests.HttpRequest;
import pk.codebase.requests.HttpResponse;

public class TodayAttendanceFragment extends Fragment {

    private FragmentTodayAttendanceBinding todayAttendanceBinding;

    private AttendanceAdapter attendanceAdapter;
    private RecordDatabase recordDatabase;
    private String todayDateString = "";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        todayAttendanceBinding = FragmentTodayAttendanceBinding.inflate(inflater, container, false);
        return todayAttendanceBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Objects.requireNonNull(((MainActivity) requireActivity()).getSupportActionBar()).setTitle("Today Attendance");
        todayAttendanceBinding.todayAttendanceRVId.setLayoutManager(new LinearLayoutManager(requireActivity()));
        attendanceAdapter = new AttendanceAdapter(requireActivity());
        todayAttendanceBinding.todayAttendanceRVId.setAdapter(attendanceAdapter);

        recordDatabase = RecordDatabase.getInstance(requireActivity());

        Date todayDate = Calendar.getInstance().getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(todayDate);
        cal.add(Calendar.DAY_OF_MONTH, -2);
        Date date = cal.getTime();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String todayString = formatter.format(date);

        todayAttendanceBinding.todayAttendanceETId.setText(todayString);

        todayDateString = String.valueOf(todayAttendanceBinding.todayAttendanceETId.getText());

        recordDatabase.fpDao().toDayAttendance(todayDateString).observe(requireActivity(),
                attendanceAdapter::setData);

        todayAttendanceBinding.todayAttendanceTILId.setStartIconOnClickListener(view1 -> {
            datePicker(todayAttendanceBinding.todayAttendanceETId);
        });
    }

    public void datePicker(TextInputEditText textInputEditText) {
        Calendar myCalendar = Calendar.getInstance(); //This calendar class allow us to create the calendar objects
        int year = myCalendar.get(Calendar.YEAR);
        int month = myCalendar.get(Calendar.MONTH);
        int day = myCalendar.get(Calendar.DAY_OF_MONTH);
        // date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        textInputEditText.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        textInputEditText.getText();
                        recordDatabase.fpDao().toDayAttendance(String.valueOf(textInputEditText.getText())).observe(requireActivity(),
                                attendanceAdapter::setData);
                    }
                }, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }

//    public void getRegisteredFPRecord() {
//        Log.e("get response ", "tring.valueOf(response.code)");
//        attendanceModel = new ArrayList<>();
//
//        HttpRequest request = new HttpRequest();
//
//        request.setOnResponseListener(response -> {
//            if (response.code == HttpResponse.HTTP_OK) {
//                Log.e("get response ", String.valueOf(response.code));
//                JSONObject jsonObject = response.toJSONObject();
//                RecordDatabase.getInstance(requireActivity()).fpDao().deleteAttTable();
//                try {
//
////                    String getStatus = jsonObject.getString("status");
////                    String getCandidates = jsonObject.getString("candidates");
//
//
////                    Log.e("Get status ", getStatus);
////                    Log.e("Get candidates ", getCandidates);
//                    JSONArray jsonArray = jsonObject.getJSONArray("candidates");
//                    Log.e("array", String.valueOf(jsonArray.length()));
//
//                    attendanceModel.clear();
//                    Log.e("sizee", String.valueOf(attendanceModel.size()));
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject object = jsonArray.getJSONObject(i);
//                        String userName = object.getString("username");
//                        String checkInStatus = object.getString("checkinstatus");
//                        String currentDate = object.getString("currentdate");
//                        String checkInTime = object.getString("checkintime");
//                        String exitStatus = object.getString("exitstatus");
//                        String checkOutTime = object.getString("checkouttime");
//                        String fpId = object.getString("fpid");
//                        Log.e("Details", userName + " " + checkInStatus + " .."+currentDate
//                                + "-- " + checkInTime + fpId);
//                        attendanceModel.add(new AttendanceModel(0, userName, fpId, currentDate, checkInTime,
//                                checkInStatus, checkOutTime, exitStatus));
//
//                        Log.e("how ", String.valueOf(attendanceModel.size()));
//
//                    }
//                    RecordDatabase.getInstance(requireActivity()).fpDao()
//                            .insertFPRecord(attendanceModel);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        request.setOnErrorListener(error -> {
//            Log.e("Error ", error.reason);
//        });
//
//        request.get(attendanceRecordUrl);
//    }
}