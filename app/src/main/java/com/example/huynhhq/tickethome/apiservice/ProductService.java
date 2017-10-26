package com.example.huynhhq.tickethome.apiservice;

import com.example.huynhhq.tickethome.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by HuynhHQ on 10/15/2017.
 */

public interface ProductService {
    @GET("/api/products/1")
    Call<List<Product>> getProducts();

    @GET("/api/products/1")
    Call<List<Product>> getProduct();
}
