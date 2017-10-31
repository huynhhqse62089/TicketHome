package com.example.huynhhq.tickethome;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.huynhhq.tickethome.Data.DBManager;
import com.example.huynhhq.tickethome.model.Event;
import com.example.huynhhq.tickethome.model.Payment;
import com.example.huynhhq.tickethome.model.User;
import com.example.huynhhq.tickethome.model.ViewDialog;

import static com.example.huynhhq.tickethome.model.InfoPayment.get_instance;

public class PayTicketActivity extends AppCompatActivity {

    final String EVENT_BUNDLE_KEY = "EVENT_BUNDLE_KEY";
    EditText inputDc;
    TextView inputName, inputEmail, inputSdt;
    TextView goBack;
    Spinner spinnerProvince, spinnerDistrict;
    LinearLayout extendBank, extendCkbank, extendTtd, extendCod, extendBuild;
    RadioButton idBank, idCkbank, idTtd, idCod, idBuilding;
    Button btnContinue;
    Event event;
    int numberTicket;
    String nameTicket;
    ImageView imageBack;
    public Payment payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_ticket);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        bindingView();
        bindingAction();
        initViewValue();
        configToolbar();
    }

    private void bindingView() {
        inputName = (TextView) findViewById(R.id.input_name);
        inputEmail = (TextView) findViewById(R.id.input_email);
        inputSdt = (TextView) findViewById(R.id.input_sdt);
        inputDc = (EditText) findViewById(R.id.input_dc);
        extendBank = (LinearLayout) findViewById(R.id.extend_bank);
        extendCkbank = (LinearLayout) findViewById(R.id.extend_ckbank);
        extendCod = (LinearLayout) findViewById(R.id.extend_cod);
        extendTtd = (LinearLayout) findViewById(R.id.extend_ttd);
        extendBuild = (LinearLayout) findViewById(R.id.extend_buiding);
        idBank = (RadioButton) findViewById(R.id.id_bank);
        idCkbank = (RadioButton) findViewById(R.id.id_ckbank);
        idTtd = (RadioButton) findViewById(R.id.id_ttd);
        idCod = (RadioButton) findViewById(R.id.id_cod);
        idBuilding = (RadioButton) findViewById(R.id.id_building);
        spinnerDistrict = (Spinner) findViewById(R.id.spinner_district);
        spinnerProvince = (Spinner) findViewById(R.id.spinner_province);
        btnContinue = (Button) findViewById(R.id.btn_continue);
        goBack = (TextView) findViewById(R.id.go_back);
        imageBack = (ImageView) findViewById(R.id.image_back);
    }

    private void saveData() {
        if(idBank.isChecked()){
            payment.setPayMethod("Sử dụng Internet Banking");
            payment.setPayMethodId("IB");
        }else if(idCkbank.isChecked()){
            payment.setPayMethod("Chuyển khoản ngân hàng");
            payment.setPayMethodId("CK");
        }else if(idTtd.isChecked()){
            payment.setPayMethod("Thẻ tín dụng");
            payment.setPayMethodId("TTD");
        }else if(idCod.isChecked()){
            payment.setPayMethod("Giao vé thu tiền tận nhà (COD)");
            payment.setPayMethodId("COD");
        }else{
            payment.setPayMethod("Thanh toán tại văn phòng TicketHome");
            payment.setPayMethodId("TH");
        }

    }

    private void bindingAction() {
        idBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePayPt(R.id.id_bank);
            }
        });
        idCkbank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePayPt(R.id.id_ckbank);
            }
        });
        idTtd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePayPt(R.id.id_ttd);
            }
        });
        idCod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePayPt(R.id.id_cod);
            }
        });

        idBuilding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePayPt(R.id.id_building);
            }
        });
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    saveData();
                    Intent intent = new Intent(PayTicketActivity.this, FinishPaymentActivity.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation_go, R.anim.animation_back).toBundle();
                    startActivity(intent, bndlanimation);
                }
            }
        });
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initViewValue() {
        payment = get_instance();
        inputName.setText(payment.getFullname());
        inputEmail.setText(payment.getEmail());
        inputSdt.setText(payment.getPhoneNumber());
        event = payment.getEvent();
        numberTicket = Integer.parseInt((payment.getCountTicket()));
        nameTicket = payment.getNameTicket();
        extendBank.setVisibility(View.GONE);
        extendCkbank.setVisibility(View.GONE);
        extendCod.setVisibility(View.GONE);
        extendTtd.setVisibility(View.GONE);
        extendBuild.setVisibility(View.GONE);

    }

    public void toggle_contents(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.pt1:
                showInfoBank();
                break;
            case R.id.pt2:
                showInfoCkBank();
                break;
            case R.id.pt3:
                showInfoTtd();
                break;
            case R.id.pt4:
                showInfoCod();
                break;
            case R.id.pt5:
                showInfoBuilding();
                break;
        }
    }

    private void choosePayPt(int id) {
        switch (id) {
            case R.id.id_bank:
                showInfoBank();
                break;
            case R.id.id_ckbank:
                showInfoCkBank();
                break;
            case R.id.id_ttd:
                showInfoTtd();
                break;
            case R.id.id_cod:
                showInfoCod();
                break;
            case R.id.id_building:
                showInfoBuilding();
                break;
        }
    }

    private void showInfoBank() {
        idBank.setChecked(true);
        idCkbank.setChecked(false);
        idCod.setChecked(false);
        idTtd.setChecked(false);
        idBuilding.setChecked(false);
        extendCkbank.setVisibility(View.GONE);
        extendCod.setVisibility(View.GONE);
        extendTtd.setVisibility(View.GONE);
        extendBuild.setVisibility(View.GONE);
        extendBank.setVisibility(View.VISIBLE);
    }

    private void showInfoCkBank() {
        idBank.setChecked(false);
        idCkbank.setChecked(true);
        idCod.setChecked(false);
        idTtd.setChecked(false);
        idBuilding.setChecked(false);
        extendBank.setVisibility(View.GONE);
        extendCod.setVisibility(View.GONE);
        extendTtd.setVisibility(View.GONE);
        extendBuild.setVisibility(View.GONE);
        extendCkbank.setVisibility(View.VISIBLE);
    }

    private void showInfoTtd() {
        idBank.setChecked(false);
        idCkbank.setChecked(false);
        idCod.setChecked(false);
        idTtd.setChecked(true);
        idBuilding.setChecked(false);
        extendCkbank.setVisibility(View.GONE);
        extendCod.setVisibility(View.GONE);
        extendBuild.setVisibility(View.GONE);
        extendBank.setVisibility(View.GONE);
        extendTtd.setVisibility(View.VISIBLE);
    }

    private void showInfoCod() {
        idBank.setChecked(false);
        idCkbank.setChecked(false);
        idCod.setChecked(true);
        idTtd.setChecked(false);
        idBuilding.setChecked(false);
        extendCkbank.setVisibility(View.GONE);
        extendBank.setVisibility(View.GONE);
        extendTtd.setVisibility(View.GONE);
        extendBuild.setVisibility(View.GONE);
        extendCod.setVisibility(View.VISIBLE);
    }

    private void showInfoBuilding() {
        idBank.setChecked(false);
        idCkbank.setChecked(false);
        idCod.setChecked(false);
        idTtd.setChecked(false);
        idBuilding.setChecked(true);
        extendCkbank.setVisibility(View.GONE);
        extendBank.setVisibility(View.GONE);
        extendTtd.setVisibility(View.GONE);
        extendCod.setVisibility(View.GONE);
        extendBuild.setVisibility(View.VISIBLE);
    }

    private boolean checkValidation() {
        if (!idBank.isChecked() && !idCkbank.isChecked() && !idTtd.isChecked() && !idCod.isChecked() && !idBuilding.isChecked()) {
            ViewDialog customAleart = new ViewDialog();
            customAleart.showDialog(PayTicketActivity.this, "Xin hãy chọn phương thức thanh toán.");
            return false;
        } else if (idCod.isChecked() && inputDc.getText() == null) {
            ViewDialog customAleart = new ViewDialog();
            customAleart.showDialog(PayTicketActivity.this, "Xin hãy đầy đủ thông tin.");
            return false;
        } else if (idCod.isChecked() && inputDc.getText() != null) {
            ViewDialog customAleart = new ViewDialog();
            customAleart.showDialog(PayTicketActivity.this, "Dịch vụ này đang được tạm dừng.");
            return false;
        }
        return true;
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
        Intent intent = new Intent(PayTicketActivity.this, EventDetailActivity.class);
        intent.putExtra(EVENT_BUNDLE_KEY, event);
        startActivity(intent);
    }

}
