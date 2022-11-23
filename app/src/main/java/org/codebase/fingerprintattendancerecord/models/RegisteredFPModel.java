package org.codebase.fingerprintattendancerecord.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "register_fp_table", indices = {@Index(value = {"fp_id"}, unique = true)})
public class RegisteredFPModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int rId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "fp_id")
    private String fpId;

    @ColumnInfo(name = "register_date")
    private String registerDate;

    public RegisteredFPModel(int rId, String name, String fpId, String registerDate) {
        this.rId = rId;
        this.name = name;
        this.fpId = fpId;
        this.registerDate = registerDate;
    }

    public RegisteredFPModel() {

    }

    public int getRId() {
        return rId;
    }

    public void setRId(int rId) {
        this.rId = rId;
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

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }
}
