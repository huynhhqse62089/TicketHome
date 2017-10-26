package com.example.huynhhq.tickethome;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huynhhq.tickethome.model.Event;

import java.text.DecimalFormat;

public class ChooseTicketActivity extends AppCompatActivity {

    Event event;
    TextView titleTicket, ticketPrice, priceEvent;
    Button btnContinue, btnMinus, btnPlus, btnShowPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_ticket);
        bindingView();
        initViewValue();
        configToolbar();
        bindingAction();
    }

    private void initViewValue() {
        Bundle b = getIntent().getExtras();
        event = (Event) b.get(EventDetailActivity.EVENT_BUNDLE_KEY);
        double amount = Double.parseDouble(event.getPrice());
        DecimalFormat formatter = new DecimalFormat("#,###");
        String formatted = formatter.format(amount);
        ticketPrice.setText(formatted + " VND");
    }

    private void bindingView() {
        titleTicket = (TextView) findViewById(R.id.title_ticket);
        ticketPrice = (TextView) findViewById(R.id.ticket_price);
        priceEvent = (TextView) findViewById(R.id.price_event);
        btnContinue = (Button) findViewById(R.id.btn_continue);
        btnMinus = (Button) findViewById(R.id.btn_minus);
        btnPlus = (Button) findViewById(R.id.btn_plus);
        btnShowPrice = (Button) findViewById(R.id.btn_showPrice);
    }



    private void bindingAction() {
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int curPrice = Integer.parseInt(btnShowPrice.getText().toString());
                    if (curPrice == 0) {
                        Toast.makeText(ChooseTicketActivity.this, "Không thể mua với số lượng là một số âm", Toast.LENGTH_SHORT).show();
                    } else {
                        curPrice -= 1;
                        btnShowPrice.setText(String.valueOf(curPrice));
                        double amount = Double.parseDouble(event.getPrice());
                        DecimalFormat formatter = new DecimalFormat("#,###");
                        String formatted = formatter.format(amount * curPrice);
                        String realSum = "Tổng cộng: " + formatted + " VND";
                        priceEvent.setText(realSum);
                        if(curPrice == 0){
                            btnMinus.setTextColor(getResources().getColor(R.color.color_red_a700));
                            btnPlus.setTextColor(getResources().getColor(R.color.color_green_900));
                        }else if(curPrice >= 1 && curPrice <= 4) {
                            btnMinus.setTextColor(getResources().getColor(R.color.color_green_900));
                            btnPlus.setTextColor(getResources().getColor(R.color.color_green_900));
                        }
                    }
                } catch (NumberFormatException ex) {
                    System.out.println(ex);
                }
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int curPrice = Integer.parseInt(btnShowPrice.getText().toString());
                    if (curPrice == 4) {
                        Toast.makeText(ChooseTicketActivity.this, "Bạn chỉ được mua tối thiểu 4 vé", Toast.LENGTH_SHORT).show();
                    } else {
                        curPrice += 1;
                        btnShowPrice.setText(String.valueOf(curPrice));
                        double amount = Double.parseDouble(event.getPrice());
                        DecimalFormat formatter = new DecimalFormat("#,###");
                        String formatted = formatter.format(amount * curPrice);
                        String realSum = "Tổng cộng: " + formatted + " VND";
                        priceEvent.setText(realSum);
                        if(curPrice == 4){
                            btnPlus.setTextColor(getResources().getColor(R.color.color_red_a700));
                            btnMinus.setTextColor(getResources().getColor(R.color.color_green_900));
                        }else if(curPrice >= 0 && curPrice <= 3){
                            btnMinus.setTextColor(getResources().getColor(R.color.color_green_900));
                            btnPlus.setTextColor(getResources().getColor(R.color.color_green_900));
                        }
                    }
                } catch (NumberFormatException ex) {
                    System.out.println(ex);
                }
            }
        });
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void configToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.mCustomToolbar);
        toolbar.setTitle(event.getName());
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_close_black_18dp);
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
