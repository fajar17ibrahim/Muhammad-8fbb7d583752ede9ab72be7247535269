package com.ptmkm.testapplication.ui.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ptmkm.testapplication.MainActivity;
import com.ptmkm.testapplication.R;
import com.ptmkm.testapplication.network.ApiClient;
import com.ptmkm.testapplication.network.ApiInterface;
import com.ptmkm.testapplication.ui.login.LoginActivity;
import com.ptmkm.testapplication.utils.Constans;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences sharedPreferences;

    private Context context;

    private Button btnRegister;

    private EditText eUsername;
    private EditText ePassword;
    private EditText eRepeatPassword;

    private String sUsername;
    private String sPassword;
    private String sRepeatPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        context = this;

        eUsername = (EditText) findViewById(R.id.et_username);
        ePassword = (EditText) findViewById(R.id.et_password);
        eRepeatPassword = (EditText) findViewById(R.id.et_repeat_password);

        btnRegister = (Button) findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                sUsername = eUsername.getText().toString();
                sPassword = ePassword.getText().toString();
                sRepeatPassword = eRepeatPassword.getText().toString();

                if (sUsername.equals("") || sPassword.equals("")) {
                    Toast.makeText(context, "Field tidak boleh kosong!", Toast.LENGTH_LONG).show();
                } else {
                    if (sPassword.equals(sRepeatPassword)) {
                        registerPost(sUsername, sPassword);
                    } else {
                        Toast.makeText(context, "Password tidak sama!", Toast.LENGTH_LONG).show();
                    }
                }
        }
    }

    public void registerPost(String sUsername, String sPassword) {
        ApiInterface apiInterface = ApiClient.retrofit.create(ApiInterface.class);

        Call<ResponseBody> call = apiInterface.requestRegister(sUsername, sPassword);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                JSONObject jsonRESULTS = null;
                try {
                    jsonRESULTS = new JSONObject(response.body().string());
                    if (jsonRESULTS.getString("success").equals("1")) {

                        String message = jsonRESULTS.getString("message");

                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                        Intent iLogin = new Intent(context, LoginActivity.class);
                        startActivity(iLogin);

                    } else {
                        String error_message = jsonRESULTS.getString("message");
                        Toast.makeText(context, error_message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });


    }
}