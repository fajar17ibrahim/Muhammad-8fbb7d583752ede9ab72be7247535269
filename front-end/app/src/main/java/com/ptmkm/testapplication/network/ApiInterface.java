package com.ptmkm.testapplication.network;

import com.ptmkm.testapplication.model.Login;
import com.ptmkm.testapplication.model.Register;
import com.ptmkm.testapplication.model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseBody> requestLogin(@Field("username") String username, @Field("password") String password, @Field("login_time") String login_time);

    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseBody> requestRegister(@Field("username") String username, @Field("password") String password);

    @GET("user.php")
    Call<User> getUser(@Query("username") String username);


}
