package com.testapp.azurpoc.azurpoctestapp.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.testapp.azurpoc.azurpoctestapp.App;
import com.testapp.azurpoc.azurpoctestapp.R;
import com.testapp.azurpoc.azurpoctestapp.annotaion.Layout;
import com.testapp.azurpoc.azurpoctestapp.model.Post;

import butterknife.BindView;
/**
 * Created by Ivan Danilov on 4/3/2018.
 * Email - esseckers@gmail.com
 */
@Layout(layoutRes = R.layout.activity_main)
public class PostActivity extends AbstractActivity {

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.tv_body)
    protected TextView tvBody;

    @BindView(R.id.srl_refresh)
    protected SwipeRefreshLayout srlRefresh;

    @Override
    protected void bindView() {
        showPost();
        srlRefresh.setOnRefreshListener(this::showPost);
    }

    private void showPost() {
        srlRefresh.setRefreshing(false);
        Post post = App.getInstance().getPost();
        if (post != null) {
            tvTitle.setText(post.getTitle());
            tvBody.setText(post.getBody());
        } else {
            Toast.makeText(this, R.string.txt_error, Toast.LENGTH_LONG).show();
        }
    }
}
