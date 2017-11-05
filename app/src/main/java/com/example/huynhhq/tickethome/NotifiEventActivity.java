package com.example.huynhhq.tickethome;

import android.app.ActivityOptions;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.huynhhq.tickethome.adapter.EventListApdater;
import com.example.huynhhq.tickethome.apiservice.InteresteventService;
import com.example.huynhhq.tickethome.apiservice.ServiceManager;
import com.example.huynhhq.tickethome.model.Event;
import com.example.huynhhq.tickethome.model.Payment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.huynhhq.tickethome.model.InfoPayment.get_instance;
import static com.example.huynhhq.tickethome.model.MyProgressBar.dismiss;
import static com.example.huynhhq.tickethome.model.MyProgressBar.show;

public class NotifiEventActivity extends AppCompatActivity {

    final String EVENT_BUNDLE_KEY = "EVENT_BUNDLE_KEY";
    final String TAG = "NotifiEventActivity";
    public final static int TYPE = 2;
    private RecyclerView rcv;
    private EventListApdater adapter;
    private List<Event> listEvents;
    private InteresteventService interesteventService;
    Payment payment;
    List<Event> filteredList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifi_event);
        interesteventService = ServiceManager.getInteresteventService();
        payment = get_instance();
        configToolbar();
        bindingView();
        String username = payment.getUsername();
        show(NotifiEventActivity.this);
        interesteventService.getInterestevent(username).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                dismiss();
                if(response.isSuccessful()){
                    listEvents = response.body();
                    filteredList = filteredList(listEvents);
                    if(filteredList.size() != 0){
                        adapter = new EventListApdater(filteredList, NotifiEventActivity.this, rowClickListener, R.layout.item_notify_event);
                        rcv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }else {
                    Log.d(TAG, "onResponse: ELSE");
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                dismiss();
                Log.d(TAG, "onFailure: Something wrong!");
                t.printStackTrace();
            }
        });
    }

    private void bindingView(){
        rcv = (RecyclerView) findViewById(R.id.rcv_notifi_event);
        rcv.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rcv.setLayoutManager(layoutManager);
    }

    private void configToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.mCustomToolbar);
        toolbar.setTitle(R.string.notify_event);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.color_white), PorterDuff.Mode.SRC_ATOP);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private EventListApdater.ItemRowClickListener rowClickListener = new EventListApdater.ItemRowClickListener() {

        @Override
        public void itemClick(View view, int position) {
            Event event = filteredList.get(position);
            Intent intent = new Intent(NotifiEventActivity.this, EventDetailActivity.class);
            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation_go, R.anim.animation_back).toBundle();
            intent.putExtra(EVENT_BUNDLE_KEY, event);
            startActivity(intent, bndlanimation);
        }
    };


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

    private List<Event> filteredList(List<Event> list){
        List<Event> filteredList = new ArrayList<>();
        for (Event temp: list
                ) {
            if(temp.isNotify()){
                filteredList.add(temp);
            }
        }
        return filteredList;
    }
}
