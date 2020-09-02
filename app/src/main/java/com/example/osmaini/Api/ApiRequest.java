package com.example.osmaini.Api;

import com.example.osmaini.Model.ResponseApiModel;
import com.example.osmaini.Model.ResponseModel;
import com.example.osmaini.Model.ResponseReadLahModel;
import com.example.osmaini.Model.ResponseReadModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiRequest {
    @FormUrlEncoded
    @POST("masuk.php")
    Call<ResponseApiModel> login (@Field("username") String username,
                                  @Field("password") String password);
    @FormUrlEncoded
    @POST("registrasi.php")
    Call<ResponseModel> sendBiodata(@Field("username") String username,
                                    @Field("bidang_kode") String bidang_kode,
                                    @Field("password") String password,
                                    @Field("c_pass") String c_pass);
    @FormUrlEncoded
    @POST("bayar.php")
    Call<ResponseReadModel> sendTopup(@Field("id_pembayaran") String id_pembayaran,
                                      @Field("id_user") String id_user,
                                      @Field("uang") String uang);
    @FormUrlEncoded
    @POST("updatebayar.php")
    Call<ResponseReadLahModel> sendTopup(@Field("id_pembayaran") String id_pembayaran);
    @FormUrlEncoded
    @POST("delok.php")
    Call<ResponseReadModel> getRiwayat(@Field("id_user") String id_user);
    @FormUrlEncoded
    @POST("delokmari.php")
    Call<ResponseReadModel> getRiwayatA(@Field("id_user") String id_user);
}
