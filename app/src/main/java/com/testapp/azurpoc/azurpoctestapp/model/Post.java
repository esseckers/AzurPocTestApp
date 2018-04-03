package com.testapp.azurpoc.azurpoctestapp.model;

/**
 * Created by Ivan Danilov on 3/5/2018.
 * Email - esseckers@gmail.com
 */

public class Post extends AbstractModel {
    private int id;
    private String title;
    private String body;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    @Override
    protected String getBundleKey() {
        return Post.class.getSimpleName();
    }
}
