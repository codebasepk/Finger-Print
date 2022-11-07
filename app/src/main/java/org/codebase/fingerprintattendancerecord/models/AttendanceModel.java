package org.codebase.fingerprintattendancerecord.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AttendanceModel {

    @PrimaryKey(autoGenerate = true)
    private int fpId;

    @ColumnInfo(name = "hash_val")
    private String hashVal;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "check_in_time")
    private String checkInTime;

    @ColumnInfo(name = "check_out_time")
    private String checkOutTime;

    @ColumnInfo(name = "status")
    private boolean status;

    public int getFpId() {
        return fpId;
    }

    public void setFpId(int fpId) {
        this.fpId = fpId;
    }

    public String getHashVal() {
        return hashVal;
    }

    public void setHashVal(String hashVal) {
        this.hashVal = hashVal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
