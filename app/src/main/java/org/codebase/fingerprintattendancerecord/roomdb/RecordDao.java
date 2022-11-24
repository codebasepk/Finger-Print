package org.codebase.fingerprintattendancerecord.roomdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import org.codebase.fingerprintattendancerecord.models.AttendanceModel;
import org.codebase.fingerprintattendancerecord.models.RegisteredFPModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface RecordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertFPRecord(ArrayList<AttendanceModel> attendanceModel);

    @Query("Select * from attendance_table order by id DESC")
    LiveData<List<AttendanceModel>> readAllData();

    @Query("Delete from attendance_table")
    void deleteAttTable();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertFPRegisteredRecord(ArrayList<RegisteredFPModel> registeredFPModels);

    @Query("Select * from register_fp_table order by rId ASC")
    LiveData<List<RegisteredFPModel>> readRegisteredData();

    @Query("Select * from attendance_table where fp_id=:fpId order by id DESC")
    LiveData<List<AttendanceModel>> getUserDetail(String fpId);

    @Query("Select * from attendance_table where (fp_id And `current_date`) BETWEEN :startDate And :endDate And :fp_id")
    LiveData<List<AttendanceModel>> sortDetailsByDate(String startDate, String endDate, String fp_id);

    @Query("Select * from attendance_table where `current_date` =:todayDate")
    LiveData<List<AttendanceModel>> toDayAttendance(String todayDate);

    @Query("Delete from register_fp_table")
    void deleteRegTable();
}
