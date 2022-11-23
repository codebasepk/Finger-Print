package org.codebase.fingerprintattendancerecord.attendancefragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.codebase.fingerprintattendancerecord.MainActivity;
import org.codebase.fingerprintattendancerecord.R;
import org.codebase.fingerprintattendancerecord.adapter.AttendanceAdapter;
import org.codebase.fingerprintattendancerecord.databinding.FragmentAttendanceRecordBinding;
import org.codebase.fingerprintattendancerecord.models.AttendanceModel;
import org.codebase.fingerprintattendancerecord.roomdb.RecordDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class AttendanceRecordFragment extends Fragment {

    private FragmentAttendanceRecordBinding recordBinding;

    private AttendanceAdapter attendanceAdapter;
    private RecordDatabase recordDatabase;
    private String todayDateString = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        recordBinding = FragmentAttendanceRecordBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        return recordBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Objects.requireNonNull(((MainActivity) requireActivity()).getSupportActionBar())
                .setTitle("Attendance Record");

        recordBinding.recordRecyclerViewId.setLayoutManager(new LinearLayoutManager(requireActivity()));
        attendanceAdapter = new AttendanceAdapter(requireActivity());
        recordBinding.recordRecyclerViewId.setAdapter(attendanceAdapter);

        recordDatabase = RecordDatabase.getInstance(requireActivity());

        recordDatabase.fpDao().readAllData().observe(requireActivity(),
                attendanceAdapter::setData);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);

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
    }

    private void filterList(String s) {
        Log.e("out filter ", s);

        ArrayList<AttendanceModel> filteredList = new ArrayList<>();

        for (AttendanceModel item: MainActivity.attendanceModel) {
            Log.e("out filter list ", item.getFpId());

            if (item.getName().toLowerCase().contains(s.toLowerCase()) || item.getCurrentDate().contains(s)) {
                filteredList.add(item);
                Log.e("in filter list ", item.getFpId());
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(requireActivity(), "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            attendanceAdapter.filterList(filteredList);
        }
    }

}