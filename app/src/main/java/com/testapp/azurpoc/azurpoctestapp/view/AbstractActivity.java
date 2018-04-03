package com.testapp.azurpoc.azurpoctestapp.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.testapp.azurpoc.azurpoctestapp.annotaion.Layout;
import com.testapp.azurpoc.azurpoctestapp.service.PostService;

import butterknife.ButterKnife;

/**
 * Created by Ivan Danilov on 4/3/2018.
 * Email - esseckers@gmail.com
 */
public abstract class AbstractActivity extends AppCompatActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(getLayoutRes());
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(this, PostService.class));
        } else {
            startService(new Intent(this, PostService.class));
        }

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        bindView();
    }

    protected void bindView() {

    }

    /**
     * set layout resource for current activity (for setContentView method)
     *
     * @return layout resource
     */
    private int getLayoutRes() {
        Layout layout = this.getClass().getAnnotation(Layout.class);
        return layout != null ? layout.layoutRes() : 0;
    }
}
