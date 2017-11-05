package com.example.huynhhq.tickethome;

import android.app.ActivityOptions;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huynhhq.tickethome.Data.DBEventManager;
import com.example.huynhhq.tickethome.adapter.EventListApdater;
import com.example.huynhhq.tickethome.apiservice.InteresteventService;
import com.example.huynhhq.tickethome.apiservice.ServiceManager;
import com.example.huynhhq.tickethome.model.Event;
import com.example.huynhhq.tickethome.model.EventNotification;
import com.example.huynhhq.tickethome.model.Payment;
import com.example.huynhhq.tickethome.model.StatusRegister;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.huynhhq.tickethome.model.InfoPayment.get_instance;
import static com.example.huynhhq.tickethome.model.MyProgressBar.dismiss;
import static com.example.huynhhq.tickethome.model.MyProgressBar.show;

/**
 * Created by HuynhHQ on 10/24/2017.
 */

public class EventDetailActivity extends AppCompatActivity {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    final String TAG = "EventDetailActivity";
    ImageView imageEvent, imageOrganizer;
    LinearLayout laySave, layShare, layNoti;
    CheckBox chkSave, chkNotify;
    TextView titleEvent, addressEvent, timeEvent, desEvent, groupTitle, priceEvent;
    public final static String EVENT_BUNDLE_KEY = "EVENT_BUNDLE_KEY";
    Button btnBook;
    Event event;
    Payment payment;
    InteresteventService interesteventService;
    boolean mode = true;
    final DBEventManager dbEvent = new DBEventManager(this);
    List<Event> listEvents, filteredListTick, filteredListNotify;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail_activity);
        interesteventService = ServiceManager.getInteresteventService();
        payment = get_instance();
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
        if (event.getPrice().length() != 0) {
            double amount = Double.parseDouble(event.getPrice());
            DecimalFormat formatter = new DecimalFormat("#,###");
            String formatted = formatter.format(amount);
            priceEvent.setText("Từ " + formatted + " VND");
            mode = true;
        } else {
            mode = false;
            priceEvent.setText("Miễn phí");
            btnBook.setText("Đăng ký");
        }
        checkNotifyOrTick();
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
                if (chkNotify.isChecked() && !dbEvent.checkDuplicatedWord(event.getId())) {
                    setNotifyEvents();
                } else if (!chkNotify.isChecked() && dbEvent.checkDuplicatedWord(event.getId())) {
                    cancelNotification(event.getId());
                    dbEvent.deleteEvent(event.getId());
                }
                if (mode) {
                    Intent intent = new Intent(EventDetailActivity.this, ChooseTicketActivity.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation_go, R.anim.animation_back).toBundle();
                    intent.putExtra(EVENT_BUNDLE_KEY, event);
                    startActivity(intent, bndlanimation);
                } else {
                    Intent intent = new Intent(EventDetailActivity.this, FinishPaymentFreeActivity.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation_go, R.anim.animation_back).toBundle();
                    startActivity(intent, bndlanimation);
                }
            }
        });

        chkSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(EventDetailActivity.this);
                boolean checked = ((CheckBox) v).isChecked();
                if (checked) {
                    int type = 1;
                    String username = payment.getUsername();
                    int eventId = payment.getEvent().getId();
                    interesteventService.addInterestevent(username, eventId, type).enqueue(new Callback<StatusRegister>() {
                        @Override
                        public void onResponse(Call<StatusRegister> call, Response<StatusRegister> response) {
                            dismiss();
                            if (response.isSuccessful()) {
                                Toast.makeText(EventDetailActivity.this, "Đã đánh dánh dấu", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d(TAG, "onResponse: ELSE");
                                System.out.println(response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<StatusRegister> call, Throwable t) {
                            dismiss();
                            Log.d(TAG, "onFailure: Đã có lỗi xảy ra!");
                            t.printStackTrace();
                        }
                    });

                } else {
                    int type = 1;
                    String username = payment.getUsername();
                    int eventId = payment.getEvent().getId();
                    interesteventService.addInterestevent(username, eventId, type).enqueue(new Callback<StatusRegister>() {
                        @Override
                        public void onResponse(Call<StatusRegister> call, Response<StatusRegister> response) {
                            dismiss();
                            if (response.isSuccessful()) {
                                Toast.makeText(EventDetailActivity.this, "Đã bỏ đánh dấu", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d(TAG, "onResponse: ELSE");
                                System.out.println(response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<StatusRegister> call, Throwable t) {
                            dismiss();
                            Log.d(TAG, "onFailure: Đã có lỗi xảy ra!");
                            t.printStackTrace();
                        }
                    });
                }
            }
        });

        chkNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(EventDetailActivity.this);
                boolean checked = ((CheckBox) v).isChecked();
                if (checked) {
                    int type = 2;
                    String username = payment.getUsername();
                    int eventId = payment.getEvent().getId();
                    interesteventService.addInterestevent(username, eventId, type).enqueue(new Callback<StatusRegister>() {
                        @Override
                        public void onResponse(Call<StatusRegister> call, Response<StatusRegister> response) {
                            dismiss();
                            if (response.isSuccessful()) {
                                Toast.makeText(EventDetailActivity.this, "Sự kiện sẽ được thông báo", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d(TAG, "onResponse: ELSE");
                                System.out.println(response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<StatusRegister> call, Throwable t) {
                            dismiss();
                            Log.d(TAG, "onFailure: Đã có lỗi xảy ra!");
                            t.printStackTrace();
                        }
                    });
                } else {
                    int type = 2;
                    String username = payment.getUsername();
                    int eventId = payment.getEvent().getId();
                    interesteventService.addInterestevent(username, eventId, type).enqueue(new Callback<StatusRegister>() {
                        @Override
                        public void onResponse(Call<StatusRegister> call, Response<StatusRegister> response) {
                            dismiss();
                            if (response.isSuccessful()) {
                                Toast.makeText(EventDetailActivity.this, "Đã bỏ dánh dấu thông báo", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d(TAG, "onResponse: ELSE");
                                System.out.println(response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<StatusRegister> call, Throwable t) {
                            dismiss();
                            Log.d(TAG, "onFailure: Đã có lỗi xảy ra!");
                            t.printStackTrace();
                        }
                    });
                }
            }
        });

        laySave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkSave.isChecked()) {
                    show(EventDetailActivity.this);
                    int type = 1;
                    String username = payment.getUsername();
                    int eventId = payment.getEvent().getId();
                    interesteventService.addInterestevent(username, eventId, type).enqueue(new Callback<StatusRegister>() {
                        @Override
                        public void onResponse(Call<StatusRegister> call, Response<StatusRegister> response) {
                            dismiss();
                            if (response.isSuccessful()) {
                                Toast.makeText(EventDetailActivity.this, "Đã bỏ dánh dấu", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d(TAG, "onResponse: ELSE");
                                System.out.println(response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<StatusRegister> call, Throwable t) {
                            dismiss();
                            Log.d(TAG, "onFailure: Đã có lỗi xảy ra!");
                            t.printStackTrace();
                        }
                    });
                    chkSave.setChecked(false);
                } else {
                    show(EventDetailActivity.this);
                    int type = 1;
                    String username = payment.getUsername();
                    int eventId = payment.getEvent().getId();
                    interesteventService.addInterestevent(username, eventId, type).enqueue(new Callback<StatusRegister>() {
                        @Override
                        public void onResponse(Call<StatusRegister> call, Response<StatusRegister> response) {
                            dismiss();
                            if (response.isSuccessful()) {
                                Toast.makeText(EventDetailActivity.this, "Đã đánh dánh dấu", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d(TAG, "onResponse: ELSE");
                                System.out.println(response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<StatusRegister> call, Throwable t) {
                            dismiss();
                            Log.d(TAG, "onFailure: Đã có lỗi xảy ra!");
                            t.printStackTrace();
                        }
                    });
                    chkSave.setChecked(true);
                }
            }
        });

        layNoti.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                if (chkNotify.isChecked()) {
                    int type = 2;
                    show(EventDetailActivity.this);
                    String username = payment.getUsername();
                    int eventId = payment.getEvent().getId();
                    interesteventService.addInterestevent(username, eventId, type).enqueue(new Callback<StatusRegister>() {
                        @Override
                        public void onResponse(Call<StatusRegister> call, Response<StatusRegister> response) {
                            dismiss();
                            if (response.isSuccessful()) {
                                Toast.makeText(EventDetailActivity.this, "Đã bỏ dánh dấu thông báo", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d(TAG, "onResponse: ELSE");
                                System.out.println(response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<StatusRegister> call, Throwable t) {
                            dismiss();
                            Log.d(TAG, "onFailure: Đã có lỗi xảy ra!");
                            t.printStackTrace();
                        }
                    });
                    chkNotify.setChecked(false);
                } else {
                    int type = 2;
                    show(EventDetailActivity.this);
                    String username = payment.getUsername();
                    int eventId = payment.getEvent().getId();
                    interesteventService.addInterestevent(username, eventId, type).enqueue(new Callback<StatusRegister>() {
                        @Override
                        public void onResponse(Call<StatusRegister> call, Response<StatusRegister> response) {
                            dismiss();
                            if (response.isSuccessful()) {
                                Toast.makeText(EventDetailActivity.this, "Sự kiện sẽ được thông báo", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d(TAG, "onResponse: ELSE");
                                System.out.println(response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<StatusRegister> call, Throwable t) {
                            dismiss();
                            Log.d(TAG, "onFailure: Đã có lỗi xảy ra!");
                            t.printStackTrace();
                        }
                    });
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
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
        if (chkNotify.isChecked() && !dbEvent.checkDuplicatedWord(event.getId())) {
            setNotifyEvents();
        } else if (!chkNotify.isChecked() && dbEvent.checkDuplicatedWord(event.getId())) {
            cancelNotification(event.getId());
            dbEvent.deleteEvent(event.getId());
        }
        if (payment.getMode() == 1) {
            Intent intent = new Intent(getApplicationContext(), MainScreenActivity.class);
            intent.putExtra("ID_CTIY", payment.getIdCity());
            startActivity(intent);
        }
    }

    private String getCurrentDate(long timeStamp) {
        String str = null;
        Calendar calendar = Calendar.getInstance();
        Date netDate = (new Date(timeStamp));
        calendar.setTime(netDate);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
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

    private void setNotifyEvents() {
        EventNotification eventNotification = new EventNotification();
        eventNotification.setRealId(event.getId());
        eventNotification.setStartDate(Long.parseLong(event.getStartDate()));
        eventNotification.setName(event.getName());
        String date = getDate(Long.parseLong(event.getStartDate()));
        dbEvent.inserEvent(eventNotification);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 5);
        Intent intent = new Intent(getApplicationContext(), Notification_receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), eventNotification.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public void cancelNotification(int requestCode) {
        try {
            Intent notificationIntent = new Intent(getApplicationContext(), Notification_receiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), requestCode, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getDate(long timeStamp) {
        try {
            Date netDate = (new Date(timeStamp));
            return sdf.format(netDate);
        } catch (Exception ex) {
            return "xx";
        }
    }

    private void checkNotifyOrTick() {
        show(EventDetailActivity.this);
        String username = payment.getUsername();
        interesteventService.getInterestevent(username).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                dismiss();
                if (response.isSuccessful()) {
                    listEvents = response.body();
                    filteredListTick = filteredListTick(listEvents);
                    filteredListNotify = filteredListNotify(listEvents);
                    if (filteredListTick.size() != 0) {
                        for (int i = 0; i < filteredListTick.size(); i++) {
                            if (event.getId() == filteredListTick.get(i).getId()) {
                                chkSave.setChecked(true);
                                break;
                            }
                        }
                    }
                    if (filteredListNotify.size() != 0) {
                        for (int i = 0; i < filteredListNotify.size(); i++) {
                            if (event.getId() == filteredListNotify.get(i).getId()) {
                                chkNotify.setChecked(true);
                                break;
                            }
                        }
                    }
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

    private List<Event> filteredListTick(List<Event> list) {
        List<Event> filteredList = new ArrayList<>();
        for (Event temp : list
                ) {
            if (temp.isCheck()) {
                filteredList.add(temp);
            }
        }
        return filteredList;
    }

    private List<Event> filteredListNotify(List<Event> list) {
        List<Event> filteredList = new ArrayList<>();
        for (Event temp : list
                ) {
            if (temp.isNotify()) {
                filteredList.add(temp);
            }
        }
        return filteredList;
    }
}
