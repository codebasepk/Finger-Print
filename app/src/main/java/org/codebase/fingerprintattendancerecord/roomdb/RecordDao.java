package org.codebase.fingerprintattendancerecord.roomdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import org.codebase.fingerprintattendancerecord.models.AttendanceModel;

import java.util.List;

@Dao
public interface RecordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertFPRecord(AttendanceModel attendanceModel);

//    @Query("Select * from attendanceModel order by fpId ASC")
    LiveData<List<AttendanceModel>> readAllData();
}
