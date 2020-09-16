package com.example.appbanhang.Model;

import java.io.Serializable;

public class Sanpham implements Serializable {//Serializable  giup truyen 1 du lieu dang sanpham
    public int IdSP;
    public String TenSP;
    public Integer GiaSP;
    public String HinhSP,MoTaSP;
    public int IdLoaiSP;

    public Sanpham(int idSP, String tenSP, Integer giaSP, String hinhSP, String moTaSP, int idLoaiSP) {
        IdSP = idSP;
        TenSP = tenSP;
        GiaSP = giaSP;
        HinhSP = hinhSP;
        MoTaSP = moTaSP;
        IdLoaiSP = idLoaiSP;
    }

    public int getIdSP() {
        return IdSP;
    }

    public void setIdSP(int idSP) {
        IdSP = idSP;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public Integer getGiaSP() {
        return GiaSP;
    }

    public void setGiaSP(Integer giaSP) {
        GiaSP = giaSP;
    }

    public String getHinhSP() {
        return HinhSP;
    }

    public void setHinhSP(String hinhSP) {
        HinhSP = hinhSP;
    }

    public String getMoTaSP() {
        return MoTaSP;
    }

    public void setMoTaSP(String moTaSP) {
        MoTaSP = moTaSP;
    }

    public int getIdLoaiSP() {
        return IdLoaiSP;
    }

    public void setIdLoaiSP(int idLoaiSP) {
        IdLoaiSP = idLoaiSP;
    }
}
