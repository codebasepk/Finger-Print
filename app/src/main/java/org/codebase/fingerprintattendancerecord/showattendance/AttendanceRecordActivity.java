package org.codebase.fingerprintattendancerecord.showattendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.codebase.fingerprintattendancerecord.R;
import org.codebase.fingerprintattendancerecord.adapter.AttendanceAdapter;
import org.codebase.fingerprintattendancerecord.databinding.ActivityAttendanceRecordBinding;
import org.codebase.fingerprintattendancerecord.models.AttendanceModel;
import org.codebase.fingerprintattendancerecord.roomdb.RecordDatabase;
import org.codebase.fingerprintattendancerecord.utils.App;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import pk.codebase.requests.HttpRequest;
import pk.codebase.requests.HttpResponse;

public class AttendanceRecordActivity extends AppCompatActivity {

    private ActivityAttendanceRecordBinding recordBinding;
    private AttendanceAdapter attendanceAdapter;
    private RecordDatabase recordDatabase;

    private ArrayList<AttendanceModel> attendanceModel;
    private LiveData<List<AttendanceModel>> listLiveData;
    public String startDate = "";
    public String endDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recordBinding = ActivityAttendanceRecordBinding.inflate(getLayoutInflater());
        setContentView(recordBinding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).setTitle("Attendance Record");

        recordBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        attendanceAdapter = new AttendanceAdapter(this);
        recordBinding.recyclerView.setAdapter(attendanceAdapter);

        recordDatabase = RecordDatabase.getInstance(getApplicationContext());

        Date todayDate = Calendar.getInstance().getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(todayDate);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date date = cal.getTime();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String todayString = formatter.format(date);

        recordBinding.endDateETId.setText(todayString);

        startDate = String.valueOf(recordBinding.startDateETId.getText());
//        String startDate = "2022-11-20";

        endDate = String.valueOf(recordBinding.endDateETId.getText());
//        String endDate = "2022-11-21";
        Log.e("end date ", endDate);

        Intent intent = getIntent();
        String getFpId = null;

        if (intent != null) {
            Log.e("intent ", intent.toString());
            getFpId = intent.getStringExtra("FP_ID");
        }

        if (getFpId != null) {
            // get details of specific person on the base of FP_ID7
            Log.e("intent fdpid ", intent.toString());

            recordDatabase.fpDao().getUserDetail(getFpId).observe(
                    this, attendanceAdapter::setData);
            recordBinding.endDateETId.setText(null);
        } else if (!endDate.isEmpty()){
            recordDatabase.fpDao().toDayAttendance(endDate).observe(this,
                    attendanceAdapter::setData);
            Log.e("end ", endDate);
        } else {
            // get all persons data from DB
            recordDatabase.fpDao().readAllData().observe(this,
                    attendanceAdapter::setData);
            recordBinding.endDateETId.setText(null);
        }

        recordBinding.startDateTILId.setStartIconOnClickListener(view -> {
            datePicker(recordBinding.startDateETId);
//            recordBinding.detailsByDateButtonId.setEnabled(true);
//            recordBinding.detailsByDateButtonId.setClickable(true);
//            Log.e("start date + end date ", String.valueOf(recordBinding.startDateETId.getText())
//                    + ",.,."+endDate);
//            recordDatabase.fpDao().sortDetailsByDate(String.valueOf(recordBinding.startDateETId.getText()),
//                    endDate).observe(this, attendanceAdapter::setData);
        });

//        recordBinding.detailsByDateButtonId.setOnClickListener(view -> {
//            Log.e("start date + end date ", startDate
//                    + ",.,."+endDate);
//            if (!startDate.isEmpty() && !endDate.isEmpty()) {
//                recordDatabase.fpDao().sortDetailsByDate(startDate, endDate).observe(this,
//                        attendanceAdapter::setData);
//            }
//        });

//        recordBinding.endDateETId.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                filterList(editable.toString());
//            }
//        });

        recordBinding.endDateTILId.setStartIconOnClickListener(view -> {
            datePicker(recordBinding.endDateETId);
        });

        getRegisteredFPRecord();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.actionSearchId);

        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String name) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String name) {
                filterList(name);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void filterList(String s) {
        Log.e("out filter ", s);

        ArrayList<AttendanceModel> filteredList = new ArrayList<>();

        for (AttendanceModel item: attendanceModel) {
            Log.e("out filter list ", item.getFpId());

            if (item.getName().toLowerCase().contains(s.toLowerCase()) || item.getCurrentDate().contains(s)) {
                filteredList.add(item);
                Log.e("in filter list ", item.getFpId());
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            attendanceAdapter.filterList(filteredList);
        }
    }

    public void datePicker(TextInputEditText textInputEditText) {
        Calendar myCalendar = Calendar.getInstance(); //This calendar class allow us to create the calendar objects
        int year = myCalendar.get(Calendar.YEAR);
        int month = myCalendar.get(Calendar.MONTH);
        int day = myCalendar.get(Calendar.DAY_OF_MONTH);
        // date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        textInputEditText.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        startDate = Objects.requireNonNull(textInputEditText.getText()).toString();
                    }
                }, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }

    public void getRegisteredFPRecord() {
        Log.e("get response ", "tring.valueOf(response.code)");
        attendanceModel = new ArrayList<>();

        HttpRequest request = new HttpRequest();

        request.setOnResponseListener(response -> {
            if (response.code == HttpResponse.HTTP_OK) {
                Log.e("get response ", String.valueOf(response.code));
                JSONObject jsonObject = response.toJSONObject();
                RecordDatabase.getInstance(getApplicationContext()).fpDao().deleteAttTable();
                try {

//                    String getStatus = jsonObject.getString("status");
//                    String getCandidates = jsonObject.getString("candidates");


//                    Log.e("Get status ", getStatus);
//                    Log.e("Get candidates ", getCandidates);
                    JSONArray jsonArray = jsonObject.getJSONArray("candidates");
                    Log.e("array", String.valueOf(jsonArray.length()));

                    attendanceModel.clear();
                    Log.e("sizee", String.valueOf(attendanceModel.size()));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String userName = object.getString("username");
                        String checkInStatus = object.getString("checkinstatus");
                        String currentDate = object.getString("currentdate");
                        String checkInTime = object.getString("checkintime");
                        String exitStatus = object.getString("exitstatus");
                        String checkOutTime = object.getString("checkouttime");
                        String fpId = object.getString("fpid");
                        Log.e("Details", userName + " " + checkInStatus + " .."+currentDate
                                + "-- " + checkInTime + fpId);
                        attendanceModel.add(new AttendanceModel(0, userName, fpId, currentDate, checkInTime,
                                checkInStatus, checkOutTime, exitStatus));

                        Log.e("how ", String.valueOf(attendanceModel.size()));

                    }
                    RecordDatabase.getInstance(getApplicationContext()).fpDao()
                            .insertFPRecord(attendanceModel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        request.setOnErrorListener(error -> {
            Log.e("Error ", error.reason);
        });

        request.get(App.GET_DATA_API);
    }

}