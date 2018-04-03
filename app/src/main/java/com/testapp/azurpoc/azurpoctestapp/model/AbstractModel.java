package com.testapp.azurpoc.azurpoctestapp.model;

import android.os.Bundle;

import java.io.Serializable;
/**
 * Created by Ivan Danilov on 4/3/2018.
 * Email - esseckers@gmail.com
 */
public abstract class AbstractModel implements Serializable {

    protected AbstractModel() {
    }

    public Bundle toBundle() {
        Bundle b = new Bundle();
        b.putSerializable(getBundleKey(), this);
        return b;
    }

    protected abstract String getBundleKey();
}
