package com.example.osmaini.Model;

import java.util.List;

public class ResponseReadModel {
    String  kode, pesan;
    List<ListModel> result;

    public List<ListModel> getResult() {
        return result;
    }

    public void setResult(List<ListModel> result) {
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
