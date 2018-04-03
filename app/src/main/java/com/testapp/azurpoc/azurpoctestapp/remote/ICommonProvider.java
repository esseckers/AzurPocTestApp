package com.testapp.azurpoc.azurpoctestapp.remote;


import com.testapp.azurpoc.azurpoctestapp.model.Post;

import io.reactivex.Single;
/**
 * Created by Ivan Danilov on 4/3/2018.
 * Email - esseckers@gmail.com
 */
public interface ICommonProvider {

    Single<Post> getPost(int id);


}
