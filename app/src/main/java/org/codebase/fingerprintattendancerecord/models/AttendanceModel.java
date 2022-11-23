package org.codebase.fingerprintattendancerecord.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "attendance_table")
public class AttendanceModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "fp_id")
    private String fpId;

    @ColumnInfo(name = "current_date")
    private String currentDate;

    @ColumnInfo(name = "check_in_time")
    private String checkInTime;

    @ColumnInfo(name = "check_in_status")
    private String checkInStatus;

    @ColumnInfo(name = "checkout_time")
    private String checkOutTime;

    @ColumnInfo(name = "checkout_status")
    private String checkOutStatus;

    // constructor
    public AttendanceModel(int id, String name, String fpId, String currentDate, String checkInTime,
                           String checkInStatus, String checkOutTime, String checkOutStatus) {
        this.id = id;
        this.name = name;
        this.fpId = fpId;
        this.currentDate = currentDate;
        this.checkInTime = checkInTime;
        this.checkInStatus = checkInStatus;
        this.checkOutTime = checkOutTime;
        this.checkOutStatus = checkOutStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFpId() {
        return fpId;
    }

    public void setFpId(String fpId) {
        this.fpId = fpId;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckInStatus() {
        return checkInStatus;
    }

    public void setCheckInStatus(String checkInStatus) {
        this.checkInStatus = checkInStatus;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getCheckOutStatus() {
        return checkOutStatus;
    }

    public void setCheckOutStatus(String checkOutStatus) {
        this.checkOutStatus = checkOutStatus;
    }
// empty constructor
//    public AttendanceModel() {
//
//    }

}
