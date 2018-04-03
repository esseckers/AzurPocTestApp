package com.testapp.azurpoc.azurpoctestapp.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.testapp.azurpoc.azurpoctestapp.App;
import com.testapp.azurpoc.azurpoctestapp.R;
import com.testapp.azurpoc.azurpoctestapp.model.Post;
import com.testapp.azurpoc.azurpoctestapp.remote.NetworkManager;
import com.testapp.azurpoc.azurpoctestapp.remote.event.SimpleDataCallback;
import com.testapp.azurpoc.azurpoctestapp.utils.RxUtils;
import com.testapp.azurpoc.azurpoctestapp.view.PostActivity;

import java.util.Random;

/**
 * Created by Ivan Danilov on 4/3/2018.
 * Email - esseckers@gmail.com
 */
public class PostService extends Service {

    private static final int DELAY = 10;
    private Handler handler;
    private Runnable runnable;

    private Post post;

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(1, createNotification());
        }
        handler = new Handler();
        runnable = this::loadPost;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.removeCallbacks(runnable);
        runnable.run();
        return super.onStartCommand(intent, flags, startId);
    }

    private void loadPost() {
        RxUtils.executeSingle(NetworkManager.getInstance().getPost(randomId()), new SimpleDataCallback<Post>() {
            @Override
            public void onResponse(Post post) {
                handler.postDelayed(runnable, DELAY * 1000L);
                PostService.this.post = post;
                App.getInstance().setPost(post);
                showNotification();
            }

            @Override
            public void onFail(Throwable throwable) {
                handler.postDelayed(runnable, DELAY * 1000L);
                App.getInstance().setPost(post);
            }
        });
    }

    public void showNotification() {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(2, createNotification());
        }
    }

    private Notification createNotification() {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent();
        intent.setClass(this, PostActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        String channelId = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId = createNotificationChannel();
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true)

                .setPriority(Notification.PRIORITY_MAX)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        if (post != null) {
            notificationBuilder
                    .setContentTitle(post.getTitle())
                    .setContentText(post.getBody());
        }
        return notificationBuilder.build();
    }

    private int randomId() {
        Random r = new Random();
        int low = 1;
        int high = 100;
        return r.nextInt(high - low) + low;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel() {
        String channelId = "my_service";
        String channelName = "My Background Service";
        NotificationChannel chan = new NotificationChannel(channelId,
                channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager service = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (service != null) {
            service.createNotificationChannel(chan);
        }
        return channelId;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
