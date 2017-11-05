package com.example.huynhhq.tickethome;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huynhhq.tickethome.model.Event;
import com.example.huynhhq.tickethome.model.Payment;

import java.text.DecimalFormat;

import static com.example.huynhhq.tickethome.model.InfoPayment.get_instance;

public class ChooseTicketActivity extends AppCompatActivity {

    final String EVENT_BUNDLE_KEY = "EVENT_BUNDLE_KEY";
    Event event;
    TextView titleTicket, ticketPrice, priceEvent, outSlot;
    Button btnContinue, btnMinus, btnPlus, btnShowPrice;
    Payment payment;
    TableRow tableRow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_ticket);
        bindingView();
        initViewValue();
        configToolbar();
        bindingAction();
    }

    private void saveData(){
        payment = get_instance();
        payment.setEvent(event);
        payment.setCountTicket(btnShowPrice.getText().toString().trim());
        payment.setNameTicket(titleTicket.getText().toString().trim());
    }

    private void initViewValue() {
        Bundle b = getIntent().getExtras();
        event = (Event) b.get(EventDetailActivity.EVENT_BUNDLE_KEY);
        double amount = Double.parseDouble(event.getPrice());
        DecimalFormat formatter = new DecimalFormat("#,###");
        String formatted = formatter.format(amount);
        ticketPrice.setText(formatted + " VND");
        if(event.getNumberSlot() <= 0){
            outSlot.setVisibility(View.VISIBLE);
            tableRow.setVisibility(View.GONE);
            btnContinue.setEnabled(false);
        }else{
            outSlot.setVisibility(View.GONE);
            tableRow.setVisibility(View.VISIBLE);
            btnContinue.setEnabled(true);
        }
    }

    private void bindingView() {
        tableRow = (TableRow) findViewById(R.id.table_row);
        outSlot = (TextView) findViewById(R.id.out_slot);
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
                        if (curPrice == 0) {
                            btnMinus.setTextColor(getResources().getColor(R.color.color_red_a700));
                            btnPlus.setTextColor(getResources().getColor(R.color.color_green_900));
                        } else if (curPrice >= 1 && curPrice <= 4) {
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
                        if(curPrice <= event.getNumberSlot()){
                            btnShowPrice.setText(String.valueOf(curPrice));
                            double amount = Double.parseDouble(event.getPrice());
                            DecimalFormat formatter = new DecimalFormat("#,###");
                            String formatted = formatter.format(amount * curPrice);
                            String realSum = "Tổng cộng: " + formatted + " VND";
                            priceEvent.setText(realSum);
                            if (curPrice == 4) {
                                btnPlus.setTextColor(getResources().getColor(R.color.color_red_a700));
                                btnMinus.setTextColor(getResources().getColor(R.color.color_green_900));
                            } else if (curPrice >= 0 && curPrice <= 3) {
                                btnMinus.setTextColor(getResources().getColor(R.color.color_green_900));
                                btnPlus.setTextColor(getResources().getColor(R.color.color_green_900));
                            }
                        }else{
                            Toast.makeText(ChooseTicketActivity.this, "Hiện hệ thống chỉ còn lại " + event.getNumberSlot() + " vé.", Toast.LENGTH_SHORT).show();
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
                int countTicket = Integer.parseInt(btnShowPrice.getText().toString().trim());
                if (countTicket == 0) {
                    Toast.makeText(ChooseTicketActivity.this, "Xin hãy chọn số lượng vé.", Toast.LENGTH_SHORT).show();
                } else {
                    saveData();
                    Intent intent = new Intent(ChooseTicketActivity.this, PayTicketActivity.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation_go, R.anim.animation_back).toBundle();
                    startActivity(intent, bndlanimation);
                }
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
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
        Intent intent = new Intent(ChooseTicketActivity.this, EventDetailActivity.class);
        intent.putExtra(EVENT_BUNDLE_KEY, event);
        startActivity(intent);
    }
}
