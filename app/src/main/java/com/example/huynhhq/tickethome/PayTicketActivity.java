package com.example.huynhhq.tickethome;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PayTicketActivity extends AppCompatActivity {

    EditText inputName;
    LinearLayout extendBank;
    RelativeLayout test1;
    private Animation animShow, animHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_ticket);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        bindingView();
        initViewValue();

    }

    private void bindingView() {
        inputName = (EditText) findViewById(R.id.input_name);
        extendBank = (LinearLayout) findViewById(R.id.extend_bank);
        test1 = (RelativeLayout) findViewById(R.id.test1);
    }

    private void initViewValue() {
        extendBank.setVisibility(View.GONE);
        animShow = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        animHide = AnimationUtils.loadAnimation(this, R.anim.slide_up);
    }

    public void toggle_contents(View v) {
        extendBank.setVisibility(extendBank.isShown() ? View.GONE : View.VISIBLE);
    }

}
