package wanandroid.c03.jy.com.wanandroid.knowledgeHierarchy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import wanandroid.c03.jy.com.wanandroid.R;
import wanandroid.c03.jy.com.wanandroid.adepter.KnowHierarViewPagerAdpter;
import wanandroid.c03.jy.com.wanandroid.adepter.TwoKHRecyclerAdpter;
import wanandroid.c03.jy.com.wanandroid.base.BaseActivity;
import wanandroid.c03.jy.com.wanandroid.data.entity.AutoMassage;
import wanandroid.c03.jy.com.wanandroid.data.entity.KnowHerarData;
import wanandroid.c03.jy.com.wanandroid.data.entity.TwoKonwledgehierarchypage;
import wanandroid.c03.jy.com.wanandroid.home.MassageActivity;

public class FragmentsMessageActivity extends BaseActivity implements TwoKHRecyclerAdpter.TwoKHOnClike {

    @BindView(R.id.kh_messge_activity)
    Toolbar khMessgeActivity;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.kn_message_)
    ViewPager knMessage;
    @BindView(R.id.kh_messga_activity_tb)
    TabLayout khMessgaActivityTb;


    private List<Fragment> mFragments;
    private List<String> mTitle;
    private ReuseFragments fragment;
    private Bundle bundle;
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        bind = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        EventBus.getDefault().post("");

        setSupportActionBar(khMessgeActivity);

    }



    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void messageInfo(KnowHerarData mKnowHerarData) {
        khMessgeActivity.setTitle(mKnowHerarData.getName());

        if (mFragments == null) {
            mFragments = new ArrayList<>();
        }

        if (mTitle == null) {
            mTitle = new ArrayList<>();
        }

        List<KnowHerarData.ChildrenBean> children = mKnowHerarData.getChildren();


        for (int i = 0; i < children.size(); i++) {

            ReuseFragments reuseFragments = new ReuseFragments();
            bundle = new Bundle();

            bundle.putInt("id",children.get(i).getId());
            reuseFragments.setArguments(bundle);
            mFragments.add(reuseFragments);
            mTitle.add(children.get(i).getName());

        }

        KnowHierarViewPagerAdpter knowHierarViewPagerAdpter = new KnowHierarViewPagerAdpter(getSupportFragmentManager(), mFragments, mTitle);

        knMessage.setAdapter(knowHierarViewPagerAdpter);

        khMessgaActivityTb.setupWithViewPager(knMessage);


        byFragmentValue(mKnowHerarData);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        EventBus.getDefault().unregister(this);
    }

    public void byFragmentValue(final KnowHerarData mKnowHerarData) {
        knMessage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                ReuseFragments reuseFragments = (ReuseFragments) mFragments.get(i);
                bundle.putInt("id",mKnowHerarData.getChildren().get(i).getId());
                reuseFragments.setArguments(bundle);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void twoKHOnClike(TwoKonwledgehierarchypage.DatasBean datasBean) {

        AutoMassage autoMassage = new AutoMassage();
        autoMassage.title = datasBean.getTitle();
        autoMassage.url = datasBean.getLink();

        EventBus.getDefault().postSticky(autoMassage);
        startActivity(new Intent(FragmentsMessageActivity.this, MassageActivity.class));
    }

}
