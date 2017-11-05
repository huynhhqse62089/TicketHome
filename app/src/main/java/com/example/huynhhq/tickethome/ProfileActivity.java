package com.example.huynhhq.tickethome;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.huynhhq.tickethome.model.Payment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.huynhhq.tickethome.model.InfoPayment.get_instance;

public class ProfileActivity extends AppCompatActivity {

    TextView phoneNum, email, name, dob;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    Payment payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        bindingView();
        configToolbar();
        initViewValue();
    }

    private void bindingView(){
        payment = get_instance();
        phoneNum = (TextView) findViewById(R.id.tvNumber1);
        email = (TextView) findViewById(R.id.tvNumber3);
        name = (TextView) findViewById(R.id.tvNumber5);
        dob = (TextView) findViewById(R.id.tvNumber6);
    }

    private void initViewValue(){
        phoneNum.setText(payment.getPhoneNumber());
        email.setText(payment.getEmail());
        name.setText(payment.getFullname());
        String dobStr = payment.getDob();
        Long dobLong = Long.parseLong(dobStr);
        dob.setText(dateFormat.format(new Date(dobLong)));
    }

    private void configToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(payment.getFullname());
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.color_white), PorterDuff.Mode.SRC_ATOP);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
