package com.testapp.azurpoc.azurpoctestapp.remote;


import com.testapp.azurpoc.azurpoctestapp.model.Post;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
/**
 * Created by Ivan Danilov on 4/3/2018.
 * Email - esseckers@gmail.com
 */
public interface ServiceApi {

    @GET("posts/{id}")
    Single<Post> getPost(@Path("id") int id);
}