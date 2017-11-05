package com.example.huynhhq.tickethome;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.huynhhq.tickethome.model.Payment;

import static com.example.huynhhq.tickethome.model.InfoPayment.get_instance;

public class SuccessfulBookActivity extends AppCompatActivity {

    public final static String ID_CTIY = "ID_CTIY";
    Payment payment;
    TextView txtCongra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_book);
        payment = get_instance();
        configToolbar();
        txtCongra = (TextView) findViewById(R.id.txt_congra);
        String idPaymentMe = payment.getPayMethodId();
        if(idPaymentMe.equalsIgnoreCase("IB")){
            txtCongra.setText("Cám ơn bạn đã đặt vé. Hãy truy cập mail để nhận Payment Code của bạn.");
        }else if(idPaymentMe.equalsIgnoreCase("CK")){
            txtCongra.setText("Vui lòng truy cập vào mail để nhận thông tin chyển khoản. Payment Code Của bạn sẽ hết hạn trong vòng 7 ngày, hãy chuyển khoản trong khoảng thời gian sớm nhất để kích hoạt Payment Code.");
        }else if(idPaymentMe.equalsIgnoreCase("TTD")){
            txtCongra.setText("Cám ơn bạn đã đặt vé. Hãy truy cập mail để nhận Payment Code của bạn.");
        }else if(idPaymentMe.equalsIgnoreCase("TH")){
            txtCongra.setText("Vui lòng đến văn phòng Ticket Home tại số 33 đường 44 phường 14 Quận Gò Vấp để nhận vé. Giờ làm việc: Thứ 2 - Thứ 6: 8:30AM - 7:30PM");
        }else{
            txtCongra.setText("Cám ơn bạn đã đăng ký. Hãy truy cập mail để nhận Payment Code của bạn.");
        }
    }

    private void configToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.mCustomToolbar);
        toolbar.setTitle("Quay Trở Về Trang Chính");
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
        Intent intent = new Intent(getApplicationContext(), MainScreenActivity.class);
        intent.putExtra("ID_CTIY", payment.getIdCity());
        startActivity(intent);
    }
}
