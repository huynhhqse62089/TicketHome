package com.example.huynhhq.tickethome;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.huynhhq.tickethome.Data.DBEventManager;
import com.example.huynhhq.tickethome.Data.DBManager;
import com.example.huynhhq.tickethome.model.EventNotification;
import com.example.huynhhq.tickethome.model.Payment;
import com.example.huynhhq.tickethome.model.User;

import static com.example.huynhhq.tickethome.model.InfoPayment.get_instance;

/**
 * Created by HuynhHQ on 11/4/2017.
 */

public class Notification_receiver extends BroadcastReceiver {

    Payment payment;

    @Override
    public void onReceive(Context context, Intent intent) {
        payment = get_instance();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context, ChooseLocationActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(ChooseLocationActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        final DBEventManager dbManager = new DBEventManager(context);
        EventNotification event = dbManager.getLastedEvent();
        Notification notification = builder.setContentTitle(event.getName() + " sắp diễn ra.")
                .setContentText("Chạm đễ tìm những sự kiện mới nhất nào...")
                .setTicker("New Message Alert!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent).build();

        notificationManager.notify(event.getId(), notification);
        dbManager.deleteEvent(event.getRealId());
    }
}
