package com.example.huynhhq.tickethome.apiservice;

import com.example.huynhhq.tickethome.model.Event;

/**
 * Created by HuynhHQ on 10/15/2017.
 */

public class ServiceManager {
    private static final String getURL() {
//        if(Utils.isEmulator()){
//            return "http://10.0.2.2:49860/";
//        }else{
        return "http://minhnbh.songphi.cloud/";
//        }
    }

    public static ProductService getPService() {
        return RetrofitClient.getClient(getURL()).create(ProductService.class);
    }

    public static UserService getUserService(){
        return RetrofitClient.getClient(getURL()).create(UserService.class);
    }

    public static EventService getEventService(){
        return RetrofitClient.getClient(getURL()).create(EventService.class);
    }

    public static  TicketService getTicketService(){
        return RetrofitClient.getClient(getURL()).create(TicketService.class);
    }
}
