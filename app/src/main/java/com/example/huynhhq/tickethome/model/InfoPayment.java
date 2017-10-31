package com.example.huynhhq.tickethome.model;

/**
 * Created by HuynhHQ on 10/28/2017.
 */

public class InfoPayment {

    private static Payment _instance = null;

    public static synchronized Payment get_instance() {
        if (_instance == null) {
            _instance = new Payment();
        }
        return _instance;
    }

    public static void set_instance(Payment _instance) {
        InfoPayment._instance = _instance;
    }
}
