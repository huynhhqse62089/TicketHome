package com.example.huynhhq.tickethome;

import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huynhhq.tickethome.Data.DBManager;
import com.example.huynhhq.tickethome.apiservice.ServiceManager;
import com.example.huynhhq.tickethome.apiservice.UserService;
import com.example.huynhhq.tickethome.model.StatusRegister;
import com.example.huynhhq.tickethome.model.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import static com.example.huynhhq.tickethome.model.MyProgressBar.dismiss;
import static com.example.huynhhq.tickethome.model.MyProgressBar.show;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HuynhHQ on 10/13/2017.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    public final static String USER_INFO = "USER_INFO";
    final String TAG = "RegisterActivity";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private Button btnDob, btnRegister;
    private TextView txtDob;
    private EditText txtName, txtUsername, txtPassword, txtComfirmPassword, txtMail, txtPhone;
    private Calendar calendar, editDate;
    private RelativeLayout alreadyAcc;
    private User user;
    UserService userService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userService = ServiceManager.getUserService();
        calendar = Calendar.getInstance();
        editDate = Calendar.getInstance();
        bindingView();
        initViewValue();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_set_dob:
                btnDobAction();
                break;
            case R.id.btn_register:
                btnRegister();
                break;
            case R.id.link_login:
                txtLink();
                break;
        }
    }

    private void initViewValue() {
        txtDob.setText(dateFormat.format(Calendar.getInstance().getTime()));
    }

    private void bindingView() {
        btnDob = (Button) findViewById(R.id.btn_set_dob);
        btnRegister = (Button) findViewById(R.id.btn_register);
        txtDob = (TextView) findViewById(R.id.txt_dob);
        txtName = (EditText) findViewById(R.id.txt_name);
        txtUsername = (EditText) findViewById(R.id.txt_usernameReg);
        txtPassword = (EditText) findViewById(R.id.txt_passwordReg);
        txtComfirmPassword = (EditText) findViewById(R.id.txt_passwordComfirm);
        txtMail = (EditText) findViewById(R.id.txt_email);
        txtPhone = (EditText) findViewById(R.id.txt_phoneReg);
        alreadyAcc = (RelativeLayout) findViewById(R.id.link_login);
        btnDob.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        alreadyAcc.setOnClickListener(this);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            editDate = Calendar.getInstance();
            editDate.set(year, month, dayOfMonth);
            txtDob.setText(dateFormat.format(editDate.getTime()));
        }
    };

    private void btnDobAction() {
        Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(RegisterActivity.this,
                        datePickerListener,
                        c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
        datePickerDialog.show();
    }

    private void txtLink() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.trans_right_in, R.anim.trans_right_out).toBundle();
        startActivity(intent, bndlanimation);
    }

    private void btnRegister() {
        show(this);
        final DBManager dbManager = new DBManager(this);
        final String fullname, email, username, password, passwordConfirm, phone;
        fullname = txtName.getText().toString().trim();
        email = txtMail.getText().toString().trim();
        username = txtUsername.getText().toString().trim();
        password = txtPassword.getText().toString().trim();
        passwordConfirm = txtComfirmPassword.getText().toString().trim();
        phone = txtPhone.getText().toString().trim();
        calendar.set(
                editDate.get(Calendar.YEAR),
                editDate.get(Calendar.MONTH),
                editDate.get(Calendar.DAY_OF_MONTH));
        final Date dob = calendar.getTime();
        final String dobStr = String.valueOf(dob.getTime());
        boolean check = checkValid(fullname, email, username, password, passwordConfirm, phone, dob);
        try {
            if (check) {
                userService.createUser(username, password, fullname, phone, email, dobStr, 0).enqueue(new Callback<StatusRegister>() {
                    @Override
                    public void onResponse(Call<StatusRegister> call, Response<StatusRegister> response) {
                        dismiss();
                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: IF");
                            StatusRegister status = response.body();
                            if (status.getStatus()) {
                                user = new User(username,password, fullname, phone, email, dobStr, 0);
                                dbManager.addUser(user);
                                Intent intent = new Intent(RegisterActivity.this, VerifyAccountActivity.class);
                                intent.putExtra(USER_INFO, user);
                                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation_go, R.anim.animation_back).toBundle();
                                startActivity(intent, bndlanimation);
                            } else if(status.getMessage().equals("existed")){
                                Toast.makeText(RegisterActivity.this, "Register fail! Username is existed.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "DKM", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<StatusRegister> call, Throwable t) {
                        Log.d(TAG, "onFailure: Something wrong!");
                        t.printStackTrace();
                    }
                });
            } else {
                Toast.makeText(RegisterActivity.this, "Register fail!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception se) {
            se.printStackTrace();
        }

    }

    private boolean checkValid(String fullanme, String email, String username, String password, String passwordConfirm, String phone, Date dob) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Date now = new Date(System.currentTimeMillis());
        int result = now.compareTo(dob);
        if (fullanme.length() <= 2) {
            Toast.makeText(RegisterActivity.this, "Please input full name again! (greater than 2 char)", Toast.LENGTH_SHORT).show();
            dismiss();
            return false;
        } else if (!email.matches(emailPattern) || email.length() < 3) {
            Toast.makeText(RegisterActivity.this, "Email is invalid. Please input again!", Toast.LENGTH_SHORT).show();
            dismiss();
            return false;
        } else if (username.length() <= 6) {
            Toast.makeText(RegisterActivity.this, "Please input username again! (greater than 6 char)", Toast.LENGTH_SHORT).show();
            dismiss();
            return false;
        } else if (phone.length() < 10 || phone.length() > 11) {
            Toast.makeText(RegisterActivity.this, "Invalid phone number. Please input phone number again!", Toast.LENGTH_SHORT).show();
            dismiss();
            return false;
        } else if (result <= 0) {
            Toast.makeText(RegisterActivity.this, "Invalid birthday. Please input again!", Toast.LENGTH_SHORT).show();
            dismiss();
            return false;
        } else if (password.length() <= 6) {
            Toast.makeText(RegisterActivity.this, "Please input password again! (greater than 6 char)", Toast.LENGTH_SHORT).show();
            dismiss();
            return false;
        } else if (passwordConfirm.length() <= 6 || passwordConfirm.compareTo(password) != 0) {
            Toast.makeText(RegisterActivity.this, "Password is not match!", Toast.LENGTH_SHORT).show();
            dismiss();
            return false;
        }
        return true;
    }


}
