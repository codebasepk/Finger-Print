package org.codebase.fingerprintattendancerecord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import org.codebase.fingerprintattendancerecord.attendancefragments.AttendanceRecordFragment;
import org.codebase.fingerprintattendancerecord.attendancefragments.PersonsFragment;
import org.codebase.fingerprintattendancerecord.attendancefragments.TodayAttendanceFragment;
import org.codebase.fingerprintattendancerecord.databinding.ActivityMainBinding;
import org.codebase.fingerprintattendancerecord.models.AttendanceModel;
import org.codebase.fingerprintattendancerecord.roomdb.RecordDatabase;
import org.codebase.fingerprintattendancerecord.utils.App;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pk.codebase.requests.HttpRequest;
import pk.codebase.requests.HttpResponse;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    PersonsFragment personsFragment;
    TodayAttendanceFragment todayAttendanceFragment;
    AttendanceRecordFragment attendanceRecordFragment;

    public static ArrayList<AttendanceModel> attendanceModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Finger Print");
        personsFragment = new PersonsFragment();
        todayAttendanceFragment = new TodayAttendanceFragment();
        attendanceRecordFragment = new AttendanceRecordFragment();
        setCurrentFragment(personsFragment);

        binding.bottomNavigationViewId.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.personsMenuId:
                    setCurrentFragment(personsFragment);
                    break;
                case R.id.todayAttendanceMenuId:
                    setCurrentFragment(todayAttendanceFragment);
                    break;
                case R.id.attendanceRecordMenuId:
                    setCurrentFragment(attendanceRecordFragment);
                    break;
            }
            return true;
        });
    }

    private void setCurrentFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutId, fragment)
                .commit();
    }

    public void getRegisteredFPRecord() {
        Log.e("get response ", "tring.valueOf(response.code)");
        attendanceModel = new ArrayList<>();

        HttpRequest request = new HttpRequest();

        request.setOnResponseListener(response -> {
            if (response.code == HttpResponse.HTTP_OK) {
                Log.e("get response09 ", String.valueOf(response.code));
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
            Log.e("Error ", error.reason +",.,.,."+ error.code);
        });

        request.get(App.GET_DATA_API);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        getRegisteredFPRecord();
    }
}