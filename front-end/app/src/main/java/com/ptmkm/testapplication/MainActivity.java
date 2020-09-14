package com.ptmkm.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ptmkm.testapplication.model.User;
import com.ptmkm.testapplication.ui.login.LoginActivity;
import com.ptmkm.testapplication.utils.Constans;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements MainConstact.View, View.OnClickListener {

    private Context context;

    String strdate1;

    private SharedPreferences sharedPreferences;

    private String username;

    private String status;
    private String login_time;

    private MainPresenter mainPresenter;

    private Button btnHello;
    private Button btnOut;


    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        handler.postDelayed(runnable, 1000);

        // Cek session login jika TRUE maka langsung buka MainActivity
        sharedPreferences = getSharedPreferences(Constans.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        username = sharedPreferences.getString(Constans.TAG_USERNAME, "null");

        mainPresenter = new MainPresenter(this);
        mainPresenter.requestDataFromServer(username);

        btnHello = (Button) findViewById(R.id.btn_hello);
        btnHello.setOnClickListener(this);

        btnOut = (Button) findViewById(R.id.btn_out);
        btnOut.setOnClickListener(this);

    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Calendar c1 = Calendar.getInstance();
            SimpleDateFormat sdf1 = new SimpleDateFormat("h:m:s a");
            //SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
             strdate1 = sdf1.format(c1.getTime());
            TextView txtdate1 = (TextView) findViewById(R.id.tv_time);
            txtdate1.setText(strdate1);
            handler.postDelayed(this, 1000);
        }
    };


    @Override
    public void setDataToView(User user) {
        status = user.getLogin_state();
        login_time = user.getLogin_time();
        username = user.getUsername();

    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.d("Error ", throwable.toString());
        Toast.makeText(context, throwable.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_hello:
                Toast.makeText(context, "Hai "  + username + ", waktu login anda " + login_time, Toast.LENGTH_LONG).show();
                break;

            case R.id.btn_out:
                sharedPreferences.edit().remove(Constans.TAG_USERNAME).commit();
                sharedPreferences.edit().remove(Constans.TAG_SESSION).commit();
                sharedPreferences.edit().remove(Constans.TAG_LOGIN_STATE).commit();

                Intent iLogin = new Intent(context, LoginActivity.class);
                startActivity(iLogin);
                break;
        }
    }
}
