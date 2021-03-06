package wanandroid.c03.jy.com.wanandroid.adepter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * User:lenovo
 * Date:2018/12/11
 * Time:16:50
 * author:lzy
 */
public class KnowHierarViewPagerAdpter extends FragmentStatePagerAdapter{

    private List<Fragment> fragments;
    private List<String> title;

    public KnowHierarViewPagerAdpter(FragmentManager fm,List<Fragment> fragments,List<String> title) {
        super(fm);
        this.fragments = fragments;
        this.title = title;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }
}
