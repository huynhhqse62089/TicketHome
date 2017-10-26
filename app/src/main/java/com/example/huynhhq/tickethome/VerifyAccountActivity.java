package com.example.huynhhq.tickethome;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by HuynhHQ on 10/20/2017.
 */

public class VerifyAccountActivity extends AppCompatActivity {

    ImageButton btn_pre, btn_next;
    TextView txt_xn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        bindingView();

    }

    private void bindingView(){
        txt_xn = (TextView) findViewById(R.id.verify_text);
        btn_next = (ImageButton) findViewById(R.id.btn_next);
        btn_pre = (ImageButton) findViewById(R.id.btn_pre);
    }

}
