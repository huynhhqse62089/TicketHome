package com.example.huynhhq.tickethome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.huynhhq.tickethome.apiservice.ProductService;
import com.example.huynhhq.tickethome.apiservice.ServiceManager;
import com.example.huynhhq.tickethome.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ProductService productService = ServiceManager.getPService();
        productService.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    Log.e("ERRRR", "Success");
                    Log.e("ERRRR", response.body().size()+"");
                } else {
                    Log.e("ERRRR", "ELSE");

                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

                Log.e("ERRRR", "Fail");
            }
        });

        Log.e("ERRRR", "Fail");
    }
}
