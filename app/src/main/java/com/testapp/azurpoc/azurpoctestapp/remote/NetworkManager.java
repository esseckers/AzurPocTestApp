package com.testapp.azurpoc.azurpoctestapp.remote;


import com.testapp.azurpoc.azurpoctestapp.model.Post;

import io.reactivex.Single;
/**
 * Created by Ivan Danilov on 4/3/2018.
 * Email - esseckers@gmail.com
 */
public class NetworkManager implements ICommonProvider {

    private static NetworkManager instance;
    private ServiceApi api;

    private NetworkManager() {
        this.api = ApiConnection.provideServiceApi();
    }

    public static NetworkManager getInstance() {
        NetworkManager localInstance = instance;
        if (localInstance == null) {
            synchronized (NetworkManager.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new NetworkManager();
                }
            }
        }
        return localInstance;
    }

    @Override
    public Single<Post> getPost(int id) {
        return api.getPost(id);
    }
}