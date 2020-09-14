package com.ptmkm.testapplication;

import android.util.Log;

import com.ptmkm.testapplication.model.User;
import com.ptmkm.testapplication.network.ApiClient;
import com.ptmkm.testapplication.network.ApiInterface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRequest implements MainConstact.Model {
    @Override
    public void getAccident(final OnFinishedListener onFinishedListener, String username) {
        ApiInterface apiInterface = ApiClient.retrofit.create(ApiInterface.class);

        Call<User> call = apiInterface.getUser(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                Log.d("Result ", user.toString());
                onFinishedListener.onFinished(user);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Result Error ", t.toString());
                onFinishedListener.onFailure(t);
            }
        });
    }
}
