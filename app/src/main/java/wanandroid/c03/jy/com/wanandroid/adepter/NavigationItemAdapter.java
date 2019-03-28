package wanandroid.c03.jy.com.wanandroid.adepter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
//import com.test.lc.wanandroid.R;
//import com.test.lc.wanandroid.entity.NavigationData;
//import com.test.lc.wanandroid.util.CommenUtil;

import java.util.List;

import wanandroid.c03.jy.com.wanandroid.R;
import wanandroid.c03.jy.com.wanandroid.data.entity.NavigationUser;
import wanandroid.c03.jy.com.wanandroid.util.CommenUtil;


public class NavigationItemAdapter extends BaseQuickAdapter<NavigationUser.ArticlesBean, BaseViewHolder> {
    public NavigationItemAdapter(@Nullable List<NavigationUser.ArticlesBean> data) {
            super(R.layout.item_navigation_tv, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, NavigationUser.ArticlesBean item) {
        helper.setText(R.id.navigation_tv, item.getTitle())
                .setBackgroundColor(R.id.navigation_tv, CommenUtil.randomTagColor());
    }

    public void setNewData(List<NavigationUser.ArticlesBean> articles) {
    }
}
