package com.example.osmaini.Model;

import java.util.List;

public class ResponseReadLahModel {
    String  kode, pesan;
    List<CobaModel> result;

    public List<CobaModel> getResult() {
        return result;
    }

    public void setResult(List<CobaModel> result) {
        this.result = result;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }
}
