package com.example.huynhhq.tickethome;

import android.app.ActivityOptions;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huynhhq.tickethome.model.Event;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by HuynhHQ on 10/24/2017.
 */

public class EventDetailActivity extends AppCompatActivity {

    ImageView imageEvent, imageOrganizer;
    LinearLayout laySave, layShare, layNoti;
    CheckBox chkSave, chkNotify;
    TextView titleEvent, addressEvent, timeEvent, desEvent, groupTitle, priceEvent;
    public final static String EVENT_BUNDLE_KEY = "EVENT_BUNDLE_KEY";
    Button btnBook;
    Event event;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail_activity);
        configToolbar();
        bindingView();
        bindingAction();
        initViewValue();
    }

    private void initViewValue() {
        Bundle b = getIntent().getExtras();
        event = (Event) b.get(MainScreenActivity.EVENT_BUNDLE_KEY);
        titleEvent.setText(event.getName());
        addressEvent.setText(event.getAddress());
        Long startDate = Long.parseLong(event.getStartDate());
        Long endDate = Long.parseLong(event.getEndDate());
        String sDate = getCurrentDate(startDate);
        String eDate = getCurrentDate(endDate);
        String startEndDate = sDate + " - " + eDate;
        timeEvent.setText(startEndDate);
        desEvent.setText(event.getDescription());
        double amount = Double.parseDouble(event.getPrice());
        DecimalFormat formatter = new DecimalFormat("#,###");
        String formatted = formatter.format(amount);
        priceEvent.setText("Từ " + formatted + " VND");
    }

    private void bindingView() {
        imageEvent = (ImageView) findViewById(R.id.image_event);
        imageOrganizer = (ImageView) findViewById(R.id.image_organizer);
        laySave = (LinearLayout) findViewById(R.id.lay_save);
        layShare = (LinearLayout) findViewById(R.id.lay_share);
        layNoti = (LinearLayout) findViewById(R.id.lay_noti);
        chkSave = (CheckBox) findViewById(R.id.chk_save);
        chkNotify = (CheckBox) findViewById(R.id.chk_noti);
        titleEvent = (TextView) findViewById(R.id.title_event_detail);
        addressEvent = (TextView) findViewById(R.id.address_event_detail);
        timeEvent = (TextView) findViewById(R.id.time_event_detail);
        desEvent = (TextView) findViewById(R.id.des_event_detail);
        groupTitle = (TextView) findViewById(R.id.group_title);
        priceEvent = (TextView) findViewById(R.id.price_event);
        btnBook = (Button) findViewById(R.id.btn_book);
    }

    private void bindingAction() {

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventDetailActivity.this, ChooseTicketActivity.class);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation_go, R.anim.animation_back).toBundle();
                intent.putExtra(EVENT_BUNDLE_KEY, event);
                startActivity(intent, bndlanimation);
            }
        });

        chkSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (checked) {

                } else {

                }
            }
        });

        chkNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (checked) {

                } else {

                }
            }
        });

        laySave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chkSave.isChecked()){
                    chkSave.setChecked(false);
                }else {
                    chkSave.setChecked(true);
                }
            }
        });

        layNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chkNotify.isChecked()){
                    chkNotify.setChecked(false);
                }else {
                    chkNotify.setChecked(true);
                }
            }
        });
    }

    private void configToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.mCustomToolbar);
        toolbar.setTitle(R.string.event_detail);
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

    private String getCurrentDate(long timeStamp){
        String str= null;
        Calendar calendar = Calendar.getInstance();
        Date netDate = (new Date(timeStamp));
        calendar.setTime(netDate);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek){
            case Calendar.MONDAY:
                str = "Thứ hai, " + calendar.get(Calendar.DAY_OF_MONTH) + " tháng " + calendar.get(Calendar.MONTH);
                break;
            case Calendar.TUESDAY:
                str = "Thứ ba, " + calendar.get(Calendar.DAY_OF_MONTH) + " tháng " + calendar.get(Calendar.MONTH);
                break;
            case Calendar.WEDNESDAY:
                str = "Thứ tư, " + calendar.get(Calendar.DAY_OF_MONTH) + " tháng " + calendar.get(Calendar.MONTH);
                break;
            case Calendar.THURSDAY:
                str = "Thứ năm, " + calendar.get(Calendar.DAY_OF_MONTH) + " tháng " + calendar.get(Calendar.MONTH);
                break;
            case Calendar.FRIDAY:
                str = "Thứ sáu, " + calendar.get(Calendar.DAY_OF_MONTH) + " tháng " + calendar.get(Calendar.MONTH);
                break;
            case Calendar.SATURDAY:
                str = "Thứ bảy, " + calendar.get(Calendar.DAY_OF_MONTH) + " tháng " + calendar.get(Calendar.MONTH);
                break;
            case Calendar.SUNDAY:
                str = "Chủ nhật, " + calendar.get(Calendar.DAY_OF_MONTH) + " tháng " + calendar.get(Calendar.MONTH);
                break;
        }
        return str;
    }
}
