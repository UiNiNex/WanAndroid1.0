package wanandroid.c03.jy.com.wanandroid.adepter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

import wanandroid.c03.jy.com.wanandroid.R;
import wanandroid.c03.jy.com.wanandroid.data.entity.NavigationUser;
import wanandroid.c03.jy.com.wanandroid.util.DetailsUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;



public class NavigationAdapter extends BaseQuickAdapter<NavigationUser, BaseViewHolder> {


    public NavigationAdapter(@Nullable List<NavigationUser> data) {
        super(R.layout.item_list_navigation, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NavigationUser item) {
        RecyclerView recyclerView = helper.getView(R.id.rv_item_navigation);

        final List<NavigationUser.ArticlesBean> articles = item.getArticles();

        helper.setText(R.id.tv_item_navigation, item.getName());

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(mContext);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);

        recyclerView.setLayoutManager(layoutManager);

        NavigationItemAdapter navigationItemAdapter = new NavigationItemAdapter(articles);
        navigationItemAdapter.setNewData(articles);

        navigationItemAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                NavigationUser.ArticlesBean bean = articles.get(position);

                String link = bean.getLink();
                String title = bean.getTitle();

                DetailsUtil.getInstance().setIntent(mContext, link, title);

            }
        });
        recyclerView.setAdapter(navigationItemAdapter);
    }

}
