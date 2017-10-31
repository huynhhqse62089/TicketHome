package com.example.huynhhq.tickethome;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huynhhq.tickethome.Data.DBManager;
import com.example.huynhhq.tickethome.apiservice.ServiceManager;
import com.example.huynhhq.tickethome.apiservice.UserService;
import com.example.huynhhq.tickethome.model.Event;
import com.example.huynhhq.tickethome.model.StatusRegister;
import com.example.huynhhq.tickethome.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.huynhhq.tickethome.model.MyProgressBar.dismiss;
import static com.example.huynhhq.tickethome.model.MyProgressBar.show;

/**
 * Created by HuynhHQ on 10/20/2017.
 */

public class VerifyAccountActivity extends AppCompatActivity {

    final String TAG = "VerifyAccountActivity";
    ImageButton btn_pre, btn_next;
    TextView txt_xn;
    EditText txtCode;
    String verifyCode;
    String username;
    User user;
    UserService userService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        userService = ServiceManager.getUserService();
        bindingView();
        initViewValue();
        bindingAction();
    }

    private void bindingView(){
        txt_xn = (TextView) findViewById(R.id.verify_text);
        btn_next = (ImageButton) findViewById(R.id.btn_next);
        btn_pre = (ImageButton) findViewById(R.id.btn_pre);
        txtCode = (EditText) findViewById(R.id.txt_code);
    }

    private void bindingAction(){
        final DBManager dbManager = new DBManager(this);
        btn_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCode = txtCode.getText().toString().trim();
                username = user.getUsername();
                show(VerifyAccountActivity.this);
                if(txt_xn.getText().toString().trim().length() == 0){
                    dismiss();
                    Toast.makeText(VerifyAccountActivity.this, "Xin hãy nhập verify code!", Toast.LENGTH_SHORT).show();
                }else{
                    userService.checkVerify(username, verifyCode).enqueue(new Callback<StatusRegister>() {
                        @Override
                        public void onResponse(Call<StatusRegister> call, Response<StatusRegister> response) {
                            dismiss();
                            if(response.isSuccessful()){
                                Log.d(TAG, "onResponse: IF");
                                StatusRegister statusRegister = response.body();
                                if(statusRegister.getStatus()){
                                    dbManager.addUser(user);
                                    Intent intent = new Intent(VerifyAccountActivity.this, ChooseLocationActivity.class);
                                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation_go, R.anim.animation_back).toBundle();
                                    startActivity(intent, bndlanimation);
                                }else {
                                    Toast.makeText(VerifyAccountActivity.this, "Mã sai, xin hãy nhập lại!", Toast.LENGTH_SHORT).show();
                                }

                            }else {
                                Log.d(TAG, "onResponse: ELSE");
                                System.out.println(response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<StatusRegister> call, Throwable t) {
                            dismiss();
                            Log.d(TAG, "onFailure: Đã có lỗi xảy ra!");
                            t.printStackTrace();
                        }
                    });
                }
            }
        });
    }

    private void initViewValue(){
        Bundle b = getIntent().getExtras();
        user = (User) b.get(RegisterActivity.USER_INFO);
    }



}
