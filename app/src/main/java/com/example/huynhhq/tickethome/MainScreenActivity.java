package com.example.huynhhq.tickethome;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import static com.example.huynhhq.tickethome.model.InfoPayment.get_instance;
import static com.example.huynhhq.tickethome.model.MyProgressBar.dismiss;
import static com.example.huynhhq.tickethome.model.MyProgressBar.show;

import com.example.huynhhq.tickethome.Data.DBEventManager;
import com.example.huynhhq.tickethome.Data.DBManager;
import com.example.huynhhq.tickethome.adapter.EventListApdater;
import com.example.huynhhq.tickethome.apiservice.EventService;
import com.example.huynhhq.tickethome.model.Event;
import com.example.huynhhq.tickethome.apiservice.ServiceManager;
import com.example.huynhhq.tickethome.model.EventNotification;
import com.example.huynhhq.tickethome.model.Payment;
import com.example.huynhhq.tickethome.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainScreenActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    final String TAG = "MainScreenActivity";
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private RecyclerView rcv;
    private EventListApdater adapter;
    private List<Event> listEvents;
    int typeIdCity;
    public final static int ID_HCM = 1;
    public final static int ID_HN = 2;
    public final static int ID_DN = 3;
    public final static String EVENT_BUNDLE_KEY = "EVENT_BUNDLE_KEY";
    TextView fullname, emailUser;
    NavigationView navigationView;
    View headerView;
    Payment payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        configToolbar();
        bindingView();
        initViewValue();
        show(MainScreenActivity.this);
        Bundle b = getIntent().getExtras();
        typeIdCity = (int) b.get(ChooseLocationActivity.ID_CTIY);
        payment.setIdCity(typeIdCity);
        EventService eventService = ServiceManager.getEventService();
        if (typeIdCity == 1) {
            eventService.getByCity(ID_HCM).enqueue(new Callback<List<Event>>() {
                @Override
                public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                    dismiss();
                    if (response.isSuccessful()) {
                        Log.d(TAG, "onResponse: IF");
                        listEvents = response.body();
                        adapter = new EventListApdater(listEvents, MainScreenActivity.this, rowClickListener, R.layout.item_event_list);
                        rcv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
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
        } else if (typeIdCity == 2) {
            eventService.getByCity(ID_HN).enqueue(new Callback<List<Event>>() {
                @Override
                public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                    dismiss();
                    if (response.isSuccessful()) {
                        Log.d(TAG, "onResponse: IF");
                        listEvents = response.body();
                        adapter = new EventListApdater(listEvents, MainScreenActivity.this, rowClickListener, R.layout.item_event_list);
                        rcv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
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
        } else if (typeIdCity == 3) {
            eventService.getByCity(ID_DN).enqueue(new Callback<List<Event>>() {
                @Override
                public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                    dismiss();
                    if (response.isSuccessful()) {
                        Log.d(TAG, "onResponse: IF");
                        listEvents = response.body();
                        adapter = new EventListApdater(listEvents, MainScreenActivity.this, rowClickListener, R.layout.item_event_list);
                        rcv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
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
        } else if (typeIdCity == 4) {
            eventService.getAllEvent().enqueue(new Callback<List<Event>>() {
                @Override
                public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                    dismiss();
                    if (response.isSuccessful()) {
                        Log.d(TAG, "onResponse: IF");
                        listEvents = response.body();
                        adapter = new EventListApdater(listEvents, MainScreenActivity.this, rowClickListener, R.layout.item_event_list);
                        rcv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
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
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreenActivity.this, ProfileActivity.class);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation_go, R.anim.animation_back).toBundle();
                ;
                startActivity(intent, bndlanimation);
            }
        });
    }

    private void initViewValue() {
        final DBManager dbManager = new DBManager(this);
        User user = dbManager.getUser();
        payment = get_instance();
        payment.setUsername(user.getUsername());
        payment.setFullname(user.getFullname());
        payment.setEmail(user.getEmail());
        payment.setPhoneNumber(user.getPhone());
        payment.setDob(user.getDob());
        fullname.setText(user.getFullname());
        emailUser.setText(user.getEmail());
    }

    private void configToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.mCustomToolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
    }

    private void bindingView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mToggle.getDrawerArrowDrawable().setColor(getColor(R.color.color_white));
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rcv = (RecyclerView) findViewById(R.id.rcv_event);
        rcv.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rcv.setLayoutManager(layoutManager);
        navigationView = (NavigationView) findViewById(R.id.nav_main);
        headerView = navigationView.inflateHeaderView(R.layout.navigation_header);
        fullname = (TextView) headerView.findViewById(R.id.fullname);
        emailUser = (TextView) headerView.findViewById(R.id.emailUser);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private EventListApdater.ItemRowClickListener rowClickListener = new EventListApdater.ItemRowClickListener() {

        @Override
        public void itemClick(View view, int position) {
            Event event = listEvents.get(position);
            payment.setEvent(event);
            payment.setMode(1);
            Intent intent = new Intent(MainScreenActivity.this, EventDetailActivity.class);
            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation_go, R.anim.animation_back).toBundle();
            intent.putExtra(EVENT_BUNDLE_KEY, event);
            startActivity(intent, bndlanimation);
        }
    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_choose_location) {
            Intent intent = new Intent(MainScreenActivity.this, ChooseLocationActivity.class);
            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.trans_right_in, R.anim.trans_right_out).toBundle();
            startActivity(intent, bndlanimation);
        } else if (id == R.id.nav_save_event) {
            payment.setMode(2);
            Intent intent = new Intent(MainScreenActivity.this, SaveEventsActivity.class);
            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.trans_right_in, R.anim.trans_right_out).toBundle();
            startActivity(intent, bndlanimation);
        } else if (id == R.id.nav_notify_event) {
            payment.setMode(2);
            Intent intent = new Intent(MainScreenActivity.this, NotifiEventActivity.class);
            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.trans_right_in, R.anim.trans_right_out).toBundle();
            startActivity(intent, bndlanimation);
        } else if (id == R.id.nav_intro) {

        } else if (id == R.id.nav_ask) {

        } else if (id == R.id.nav_contact) {

        } else if (id == R.id.nav_logout) {
            final DBManager dbManager = new DBManager(this);
            dbManager.deleteUser();
            Intent intent = new Intent(MainScreenActivity.this, LoginActivity.class);
            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.trans_right_in, R.anim.trans_right_out).toBundle();
            startActivity(intent, bndlanimation);
        }
        return false;
    }
}
