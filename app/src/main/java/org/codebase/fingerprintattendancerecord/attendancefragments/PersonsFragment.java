package org.codebase.fingerprintattendancerecord.attendancefragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.codebase.fingerprintattendancerecord.MainActivity;
import org.codebase.fingerprintattendancerecord.adapter.RegisteredFpAdapter;
import org.codebase.fingerprintattendancerecord.databinding.FragmentPersonsBinding;
import org.codebase.fingerprintattendancerecord.models.RegisteredFPModel;
import org.codebase.fingerprintattendancerecord.roomdb.RecordDatabase;
import org.codebase.fingerprintattendancerecord.utils.App;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import pk.codebase.requests.HttpRequest;
import pk.codebase.requests.HttpResponse;

public class PersonsFragment extends Fragment {

    private FragmentPersonsBinding binding;
    private RegisteredFpAdapter registeredFpAdapter;
    private ArrayList<RegisteredFPModel> registeredFPModels = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentPersonsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Objects.requireNonNull(((MainActivity) requireActivity()).getSupportActionBar())
                .setTitle("Registered Persons");

        binding.registeredFPRV.setLayoutManager(new LinearLayoutManager(requireActivity()));
        registeredFpAdapter = new RegisteredFpAdapter(requireActivity());
        binding.registeredFPRV.setAdapter(registeredFpAdapter);

        RecordDatabase.getInstance(requireActivity()).fpDao().readRegisteredData().observe(requireActivity(),
                registeredFpAdapter::setRegisteredData);

//        binding.attendanceRecordId.setOnClickListener(view1 -> {
//            Intent intent = new Intent(requireActivity(), AttendanceRecordActivity.class);
//            startActivity(intent);
//        });

    }

    private void getRegisteredFP() {
        HttpRequest request = new HttpRequest();

        request.setOnResponseListener(response -> {
            if (response.code == HttpResponse.HTTP_OK) {
                Log.e("retrieved successfully ", String.valueOf(response.code));
                RecordDatabase.getInstance(requireActivity()).fpDao().deleteRegTable();
                JSONObject jsonObject = response.toJSONObject();

                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("candidates");
                    Log.e("canditates", jsonArray.toString());

                    registeredFPModels.clear();
                    for (int i =0; i < jsonArray.length(); i++) {
                        JSONObject jObject = jsonArray.getJSONObject(i);

                        String personName = jObject.getString("personName");
                        String personFpId = jObject.getString("fpid");
                        String joiningDate = jObject.getString("joiningdatetime");

                        Log.e("registered data : ", personName + "___" + personFpId);

                        registeredFPModels.add(new RegisteredFPModel(0, personName, personFpId, joiningDate));
                    }

                    RecordDatabase.getInstance(requireActivity()).fpDao().insertFPRegisteredRecord(registeredFPModels);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        request.setOnErrorListener(error -> {
            Log.e("retrieved failed ", String.valueOf(error.code));
        });

        request.get(App.GET_REGISTERED_USERS_API);
    }

    @Override
    public void onResume() {
        super.onResume();
        getRegisteredFP();
    }
}