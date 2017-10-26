package com.example.huynhhq.tickethome;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.huynhhq.tickethome.model.MyProgressBar;

/**
 * Created by HuynhHQ on 10/13/2017.
 */

public class ChooseLocationActivity extends AppCompatActivity {

    LinearLayout llHcm, llHn, llAnother, llAll;
    CheckBox chkHcm, chkHn,chkAnother, chkAll;
    public final static String ID_CTIY = "ID_CTIY";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        bindingView();
        bindingAction();
        configToolbar();

    }

    private void configToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.mCustomToolbar);
        toolbar.setTitle(R.string.choose_location_title);
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

    private void bindingView(){
        llHcm = (LinearLayout) findViewById(R.id.loca_hcm);
        llHn = (LinearLayout) findViewById(R.id.loca_hn);
        llAnother = (LinearLayout) findViewById(R.id.loca_another);
        llAll = (LinearLayout) findViewById(R.id.loca_all);
        chkHcm = (CheckBox) findViewById(R.id.chk_hcm);
        chkHn = (CheckBox) findViewById(R.id.chk_hn);
        chkAnother = (CheckBox) findViewById(R.id.chk_another);
        chkAll = (CheckBox) findViewById(R.id.chk_all);
    }

    private void bindingAction(){
        llHcm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseLocationClicked(R.id.loca_hcm);
            }
        });
        llHn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseLocationClicked(R.id.loca_hn);
            }
        });
        llAnother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseLocationClicked(R.id.loca_another);
            }
        });
        llAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseLocationClicked(R.id.loca_all);

            }
        });
    }

    private void chooseLocationClicked(int id){
        switch (id){
            case R.id.loca_hcm:
                chkHcm.setChecked(true);
                chkHn.setChecked(false);
                chkAnother.setChecked(false);
                chkAll.setChecked(false);
                Intent intent = new Intent(getApplicationContext(), MainScreenActivity.class);
                intent.putExtra("ID_CTIY", 1);
                startActivity(intent);
                finish();
                break;
            case R.id.loca_hn:
                chkHn.setChecked(true);
                chkHcm.setChecked(false);
                chkAnother.setChecked(false);
                chkAll.setChecked(false);
                intent = new Intent(getApplicationContext(), MainScreenActivity.class);
                intent.putExtra("ID_CTIY", 2);
                startActivity(intent);
                finish();
                break;
            case R.id.loca_another:
                chkAnother.setChecked(true);
                chkHcm.setChecked(false);
                chkHn.setChecked(false);
                chkAll.setChecked(false);
                intent = new Intent(getApplicationContext(), MainScreenActivity.class);
                intent.putExtra("ID_CTIY", 3);
                startActivity(intent);
                finish();
                break;
            case R.id.loca_all:
                chkAll.setChecked(true);
                chkHcm.setChecked(false);
                chkHn.setChecked(false);
                chkAnother.setChecked(false);
                intent = new Intent(getApplicationContext(), MainScreenActivity.class);
                intent.putExtra("ID_CTIY", 4);
                startActivity(intent);
                finish();
                break;
        }
    }
}
