package com.example.appbanhang.Model;

public class LoaiSP {
    public int Id;
    public String TenLoaiSP,HinhLoaiSP;

    public LoaiSP(int id, String tenLoaiSP, String hinhLoaiSP) {
        Id = id;
        TenLoaiSP = tenLoaiSP;
        HinhLoaiSP = hinhLoaiSP;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTenLoaiSP() {
        return TenLoaiSP;
    }

    public void setTenLoaiSP(String tenLoaiSP) {
        TenLoaiSP = tenLoaiSP;
    }

    public String getHinhLoaiSP() {
        return HinhLoaiSP;
    }

    public void setHinhLoaiSP(String hinhLoaiSP) {
        HinhLoaiSP = hinhLoaiSP;
    }
}
