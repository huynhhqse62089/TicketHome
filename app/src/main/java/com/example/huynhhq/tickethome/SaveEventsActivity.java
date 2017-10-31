package com.example.huynhhq.tickethome;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.huynhhq.tickethome.adapter.EventListApdater;
import com.example.huynhhq.tickethome.model.Event;

import java.util.List;

public class SaveEventsActivity extends AppCompatActivity {

    final String TAG = "SaveEventsActivity";
    private RecyclerView rcv;
    private EventListApdater adapter;
    private List<Event> listEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_events);
        configToolbar();
        bindingView();
        adapter = new EventListApdater(listEvents, SaveEventsActivity.this, rowClickListener, R.layout.item_event_list);
    }

    private void bindingView(){
        rcv = (RecyclerView) findViewById(R.id.rcv_saved_event);
        rcv.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rcv.setLayoutManager(layoutManager);

    }

    private EventListApdater.ItemRowClickListener rowClickListener = new EventListApdater.ItemRowClickListener() {

        @Override
        public void itemClick(View view, int position) {
//            Event event = listEvents.get(position);
//            Intent intent = new Intent(MainScreenActivity.this, EventDetailActivity.class);
//            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation_go, R.anim.animation_back).toBundle();
//            intent.putExtra(EVENT_BUNDLE_KEY, event);
//            startActivity(intent, bndlanimation);
        }
    };

    private void configToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.mCustomToolbar);
        toolbar.setTitle(R.string.saved_event);
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
