package com.example.huynhhq.tickethome;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huynhhq.tickethome.apiservice.ServiceManager;
import com.example.huynhhq.tickethome.apiservice.TicketService;
import com.example.huynhhq.tickethome.model.Event;
import com.example.huynhhq.tickethome.model.Payment;
import com.example.huynhhq.tickethome.model.StatusRegister;
import com.example.huynhhq.tickethome.model.Ticket;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.huynhhq.tickethome.model.InfoPayment.get_instance;
import static com.example.huynhhq.tickethome.model.MyProgressBar.dismiss;
import static com.example.huynhhq.tickethome.model.MyProgressBar.show;

public class FinishPaymentFreeActivity extends AppCompatActivity {

    final String EVENT_BUNDLE_KEY = "EVENT_BUNDLE_KEY";
    Event event;
    TextView userFullname, userEmail, userSdt, idPttt;
    Button btnShowPrice, btnMinus, btnPlus, btnPay;
    Payment payment;
    final String TAG = "FinishPaymentActivity";
    TicketService ticketService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_payment_free);
        bindingView();
        initViewValue();
        configToolbar();
        bindingAction();

    }

    private void bindingView() {
        userFullname = (TextView) findViewById(R.id.user_fullname);
        userEmail = (TextView) findViewById(R.id.user_email);
        userSdt = (TextView) findViewById(R.id.user_sdt);
        idPttt = (TextView) findViewById(R.id.id_pttt);
        btnShowPrice = (Button) findViewById(R.id.btn_showPrice);
        btnMinus = (Button) findViewById(R.id.btn_minus);
        btnPlus = (Button) findViewById(R.id.btn_plus);
        btnPay = (Button) findViewById(R.id.btn_pay);
    }

    private void bindingAction(){
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int curPrice = Integer.parseInt(btnShowPrice.getText().toString());
                    if (curPrice == 0) {
                        Toast.makeText(FinishPaymentFreeActivity.this, "Không thể mua với số lượng là một số âm", Toast.LENGTH_SHORT).show();
                    } else {
                        curPrice -= 1;
                        btnShowPrice.setText(String.valueOf(curPrice));
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
                        Toast.makeText(FinishPaymentFreeActivity.this, "Bạn chỉ được mua tối thiểu 4 vé", Toast.LENGTH_SHORT).show();
                    } else {
                        curPrice += 1;
                        btnShowPrice.setText(String.valueOf(curPrice));
                        if (curPrice == 4) {
                            btnPlus.setTextColor(getResources().getColor(R.color.color_red_a700));
                            btnMinus.setTextColor(getResources().getColor(R.color.color_green_900));
                        } else if (curPrice >= 0 && curPrice <= 3) {
                            btnMinus.setTextColor(getResources().getColor(R.color.color_green_900));
                            btnPlus.setTextColor(getResources().getColor(R.color.color_green_900));
                        }
                    }
                } catch (NumberFormatException ex) {
                    System.out.println(ex);
                }
            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    show(FinishPaymentFreeActivity.this);
                    int curPrice = Integer.parseInt(btnShowPrice.getText().toString());
                    if (curPrice == 0) {
                        Toast.makeText(FinishPaymentFreeActivity.this, "Đặt vé cần ít nhất 1 vé.", Toast.LENGTH_SHORT).show();
                    } else {
                        payment.setPayMethodId("Miễn phí");
                        ticketService = ServiceManager.getTicketService();
                        Ticket ticket = new Ticket();
                        ticket.setEventId(event.getId());
                        ticket.setUsername(payment.getUsername());
                        ticket.setDescription("Đặt vé bên APP");
                        ticket.setPosition("Normal");
                        ticket.setAvailable(true);
                        ticket.setPrice(event.getPrice());
                        ticket.setPaymentMethod("Miễn phí");
                        ticket.setShipAddress("hihi");
                        ticket.setPaid(true);
                        ticketService.bookTicket(ticket.getEventId(),ticket.getUsername(), ticket.getDescription(), ticket.getPosition(),
                                ticket.isAvailable(), ticket.getPrice(), ticket.getPaymentMethod(), ticket.getShipAddress(), ticket.isPaid(), curPrice).
                                enqueue(new Callback<StatusRegister>() {
                                    @Override
                                    public void onResponse(Call<StatusRegister> call, Response<StatusRegister> response) {
                                        dismiss();
                                        if (response.isSuccessful()) {
                                            StatusRegister statusRegister = response.body();
                                            if(statusRegister.getStatus() == true){
                                                Intent intent = new Intent(FinishPaymentFreeActivity.this, SuccessfulBookActivity.class);
                                                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation_go, R.anim.animation_back).toBundle();
                                                startActivity(intent, bndlanimation);
                                            }else{
                                                Toast.makeText(FinishPaymentFreeActivity.this, "Đã có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Log.d(TAG, "onResponse: ELSE");
                                            System.out.println(response.errorBody());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<StatusRegister> call, Throwable t) {
                                        Log.d(TAG, "onFailure: Đã có lỗi xảy ra!");
                                        t.printStackTrace();
                                    }
                                });
                    }

                } catch (NumberFormatException ex) {
                    System.out.println(ex);
                }
            }
        });
    }

    private void initViewValue() {
        payment = get_instance();
        event = payment.getEvent();
        userFullname.setText(payment.getFullname());
        userEmail.setText(payment.getEmail());
        userSdt.setText(payment.getPhoneNumber());
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
        Intent intent = new Intent(FinishPaymentFreeActivity.this, EventDetailActivity.class);
        intent.putExtra(EVENT_BUNDLE_KEY, event);
        startActivity(intent);
    }

}
