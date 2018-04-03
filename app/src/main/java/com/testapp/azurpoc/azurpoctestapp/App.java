package com.testapp.azurpoc.azurpoctestapp;

import android.app.Application;

import com.testapp.azurpoc.azurpoctestapp.model.Post;
/**
 * Created by Ivan Danilov on 4/3/2018.
 * Email - esseckers@gmail.com
 */
public class App extends Application {

    private Post post;
    private static App sApplication;

    public static App getInstance() {
        return sApplication;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }
}
