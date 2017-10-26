package com.example.huynhhq.tickethome;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huynhhq.tickethome.Data.DBManager;
import com.example.huynhhq.tickethome.apiservice.ServiceManager;
import com.example.huynhhq.tickethome.apiservice.UserService;
import com.example.huynhhq.tickethome.model.Status;
import com.example.huynhhq.tickethome.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.huynhhq.tickethome.model.MyProgressBar.dismiss;
import static com.example.huynhhq.tickethome.model.MyProgressBar.show;

public class LoginActivity extends AppCompatActivity {
    final String TAG = "LoginActivity";
    private EditText txtUsername, txtPassword;
    private Button btnLogin;
    private TextView tvRegister;
    UserService userService;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userService = ServiceManager.getUserService();
        bindingView();
        bindingAction();


    }

    private void bindingView() {
        txtUsername = (EditText) findViewById(R.id.txt_username);
        txtPassword = (EditText) findViewById(R.id.txt_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        tvRegister = (TextView) findViewById(R.id.txt_register);
    }

    private void bindingAction() {
        final DBManager dbManager = new DBManager(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show(LoginActivity.this);
                String username = txtUsername.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                boolean check = checkValidLogin(username, password);
                if (check) {
                    userService.checkLogin(username, password).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            dismiss();
                            if (response.isSuccessful()) {
                                Log.d(TAG, "onResponse: IF");
                                User user = response.body();
                                if (user.getStatus()) {
                                    dbManager.addUser(user);
                                    Intent intent = new Intent(LoginActivity.this, ChooseLocationActivity.class);
                                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation_go, R.anim.animation_back).toBundle();
                                    startActivity(intent, bndlanimation);
                                } else {
                                    Toast.makeText(LoginActivity.this, "Your username or password is wrong!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Log.d(TAG, "onResponse: ELSE");
                                System.out.println(response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            dismiss();
                            Log.d(TAG, "onFailure: Something wrong!");
                            t.printStackTrace();
                        }
                    });


                }
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                Bundle bndlanimation =
                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation_go, R.anim.animation_back).toBundle();
                startActivity(intent, bndlanimation);
            }
        });
    }

    private boolean checkValidLogin(String username, String password) {
        if (username.length() <= 6) {
            Toast.makeText(LoginActivity.this, "Please input username again! (greater than 6 char)", Toast.LENGTH_SHORT).show();
            dismiss();
            return false;
        } else if (password.length() <= 6) {
            Toast.makeText(LoginActivity.this, "Please input password again! (greater than 6 char)", Toast.LENGTH_SHORT).show();
            dismiss();
            return false;
        }
        return true;
    }


}
