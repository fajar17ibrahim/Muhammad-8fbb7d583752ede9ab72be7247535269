package com.ptmkm.testapplication.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ptmkm.testapplication.MainActivity;
import com.ptmkm.testapplication.R;
import com.ptmkm.testapplication.model.Login;
import com.ptmkm.testapplication.network.ApiClient;
import com.ptmkm.testapplication.network.ApiInterface;
import com.ptmkm.testapplication.ui.register.RegisterActivity;
import com.ptmkm.testapplication.utils.Constans;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;

    private Button btnLogin;

    private SharedPreferences sharedPreferences;

    private TextView tvRegister;

    private EditText eUsername;
    private EditText ePassword;

    private String sUsername;
    private String sPassword;

    private Boolean session = false;

    private Handler handler = new Handler();

    String strTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;

        // Cek session login jika TRUE maka langsung buka MainActivity
        sharedPreferences = getSharedPreferences(Constans.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        session = sharedPreferences.getBoolean(Constans.TAG_SESSION, false);

        if (session) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(context, "Silahkan login terlebih dahulu", Toast.LENGTH_LONG).show();
        }

        context = this;

        handler.postDelayed(runnable, 1000);

        eUsername = (EditText) findViewById(R.id.et_username);
        ePassword = (EditText) findViewById(R.id.et_password);

        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);

        tvRegister = (TextView) findViewById(R.id.tv_register);
        tvRegister.setOnClickListener(this);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Calendar c1 = Calendar.getInstance();
            SimpleDateFormat sdf1 = new SimpleDateFormat("h:m:s a");
//            SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
            strTime = sdf1.format(c1.getTime());
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
                Intent iRegister = new Intent(context, RegisterActivity.class);
                startActivity(iRegister);
                break;

            case R.id.btn_login:
                sUsername = eUsername.getText().toString();
                sPassword = ePassword.getText().toString();
                if (sUsername.equals("") || sPassword.equals("")) {
                    Toast.makeText(context, "Field tidak boleh kosong!", Toast.LENGTH_LONG).show();
                } else {
                    loginPost(sUsername, sPassword, strTime);
                }

                break;
        }
    }

    public void loginPost(final String sUsername, String sPassword, String time) {
        ApiInterface apiInterface = ApiClient.retrofit.create(ApiInterface.class);

        Call<ResponseBody> call = apiInterface.requestLogin(sUsername, sPassword, time);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonRESULTS = null;
                try {
                    jsonRESULTS = new JSONObject(response.body().string());
                    if (jsonRESULTS.getString("success").equals("1")) {

                        String name = jsonRESULTS.getString("username");
                        sharedPreferences = getSharedPreferences(Constans.SHARED_PREFERENCES, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Constans.TAG_USERNAME, name);
                        editor.putBoolean(Constans.TAG_SESSION, true);
                        editor.apply();

                        Toast.makeText(context, "Hai " + name + " waktu login anda " + strTime, Toast.LENGTH_LONG).show();

                        Intent iMain = new Intent(context, MainActivity.class);
                        startActivity(iMain);

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