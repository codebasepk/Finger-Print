package org.codebase.fingerprintattendancerecord.roomdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import org.codebase.fingerprintattendancerecord.models.AttendanceModel;
import org.codebase.fingerprintattendancerecord.models.RegisteredFPModel;

@Database(entities = {AttendanceModel.class, RegisteredFPModel.class}, version = 5, exportSchema = false)
public abstract class RecordDatabase extends RoomDatabase {

    private static volatile RecordDatabase INSTANCE;

    public synchronized static RecordDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RecordDatabase.class,
                    "FPR_DB")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public abstract RecordDao fpDao();
}
