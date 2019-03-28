package wanandroid.c03.jy.com.wanandroid.home;


import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.scwang.smartrefresh.header.DropBoxHeader;
import com.scwang.smartrefresh.header.PhoenixHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.bgabanner.BGABanner;
import wanandroid.c03.jy.com.wanandroid.GlideApp;
import wanandroid.c03.jy.com.wanandroid.MainActivity;
import wanandroid.c03.jy.com.wanandroid.R;
import wanandroid.c03.jy.com.wanandroid.adepter.HomeAdapter;
import wanandroid.c03.jy.com.wanandroid.base.BasFragment;
import wanandroid.c03.jy.com.wanandroid.data.HomeRepository;
import wanandroid.c03.jy.com.wanandroid.data.entity.AutoMassage;
import wanandroid.c03.jy.com.wanandroid.data.entity.HomeBanner;
import wanandroid.c03.jy.com.wanandroid.data.entity.HomeUser;
import wanandroid.c03.jy.com.wanandroid.adepter.KnowHierarAdpter;
import wanandroid.c03.jy.com.wanandroid.data.entity.HttpResult;
import wanandroid.c03.jy.com.wanandroid.view.LoadingPage;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BasFragment implements HomeContract.IHomeView, HomeAdapter.HomePageOnClicks {

    @BindView(R.id.main_home_article_list)
    RecyclerView mainHomeArticleList;
    @BindView(R.id.home_smartrefreshlayout)
    SmartRefreshLayout homeSmartrefreshlayout;

    Unbinder unbinder;

    private View mBannerParent;
    private BGABanner mHeaderBanner;

    private HomeContract.IHomePresenter mPresenter;

    private HomeAdapter mHomeAdapter;

    private List<HomeUser> mListHomeUser;
    private List<HomeBanner> mBanners;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter(new HomePresenter(HomeRepository.getInstance(getContextObject())));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_home, container, false);
        EventBus.getDefault().register(this);
        homeSmartrefreshlayout = inflate.findViewById(R.id.home_smartrefreshlayout);
        homeSmartrefreshlayout.setRefreshHeader(new DropBoxHeader(getContext()));
        homeSmartrefreshlayout.setEnableLoadMore(true);
        homeSmartrefreshlayout.setEnableRefresh(true);

        homeSmartrefreshlayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.reFresh();
            }
        });

        unbinder = ButterKnife.bind(this, inflate);


        // 给 recycleview  item 设置分割线
        mainHomeArticleList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));


        mBannerParent = getLayoutInflater().inflate(R.layout.item_home_banner, container, false);

        mHeaderBanner = mBannerParent.findViewById(R.id.home_item_header);

        //早上
        homeSmartrefreshlayout.setRefreshHeader(new PhoenixHeader(getContext()));
        //游戏
//        homeSmartrefreshlayout.setRefreshHeader(new FunGameBattleCityHeader(getContext()));
        //水滴
//        homeSmartrefreshlayout.setRefreshHeader(new WaveSwipeHeader(getContext()));
//        飞机
//        homeSmartrefreshlayout.setRefreshHeader(new TaurusHeader(getContext()));
//        游戏2
//        homeSmartrefreshlayout.setRefreshHeader(new FunGameHitBlockHeader(getContext()));
//        homeSmartrefreshlayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));
        homeSmartrefreshlayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));



        hdieBottom(mainHomeArticleList,getActivity());

        return inflate;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 显示loading 页面
        showLoadingPage((ViewGroup) view, LoadingPage.MODE_SHOW_BUTTON_AND_TEXT);

        // 获取首页数据（包括 banner + 文章里列表）
        mPresenter.getHomeData();
        mHomeAdapter = new HomeAdapter();
        mainHomeArticleList.setLayoutManager(new LinearLayoutManager(getContext()));
        mHomeAdapter.setAnimation(new KnowHierarAdpter.SlideInRightAnimation());
        mainHomeArticleList.setAdapter(mHomeAdapter);
        mHomeAdapter.setmHomePageOnClicks((MainActivity)getActivity());
        mHomeAdapter.setmHomeCollectOnClicks(new HomeAdapter.HomeCollectOnClicks() {
            @Override
            public void onClick(int id) {
                mPresenter.onHomeCollect(id);
            }
        });

        mHeaderBanner.setDelegate(new BGABanner.Delegate<ImageView, String>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {
//                if(mHomeBanneOnClicke != null && mBanners != null){
                    AutoMassage autoMassage = new AutoMassage();
                    autoMassage.url = mBanners.get(position).getUrl();
                    autoMassage.title = mBanners.get(position).getTitle();
                    EventBus.getDefault().postSticky(autoMassage);
                    startActivity(new Intent(getContext(),MassageActivity.class ));
//                    mHomeBanneOnClicke.BannerAutoMasseg(autoMassage);
//                }

            }
        });

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void HomBanner(String s){}

    @Override
    public void onBannerSuccess(final List<HomeBanner> banners) {
        mBanners = new ArrayList<>();
        mBanners.addAll(banners);

        Collections.sort(banners);

        mHeaderBanner.setAutoPlayAble(banners.size() > 1);

        mHeaderBanner.setData(HomeBanner.getImageUrsl(banners), HomeBanner.getTitles(banners));

        mHeaderBanner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable String imageUri, int position) {
                GlideApp.with(itemView).load(imageUri).placeholder(R.drawable.ic_placeholder_bg_default).into(itemView);
            }
        });



        // 如果文章列表还没有加载回来，此时 homeAdapter = null
        if (mHomeAdapter == null) {
            mHomeAdapter = new HomeAdapter();
            mHomeAdapter.addHeader(mBannerParent);
            mainHomeArticleList.setLayoutManager(new LinearLayoutManager(getContext()));
            mainHomeArticleList.setAdapter(mHomeAdapter);
        } else {
            mHomeAdapter.addHeader(mBannerParent);
            mHomeAdapter.notifyItemChanged(0);
        }
//
    }

    @Override
    public void onBannerFail(String msg) {
        mPresenter.getBanner();
    }

    @Override
    public void onArticleSuccess(List<HomeUser> articles, int type) {
        if (mListHomeUser == null) {
            mListHomeUser = new ArrayList<>();
        }

        if (type == LOAD_TYPE_FIRST) {
            removeLoadingPage();
            mListHomeUser.clear();

        } else if (type == LOAD_TYPE_REFIRST) {
            homeSmartrefreshlayout.finishRefresh(1000, true);
            mListHomeUser.clear();
        } else if (type == LOAD_TYPE_MORE) {
            homeSmartrefreshlayout.finishLoadMore(true);
        }

        mListHomeUser.addAll(articles);

        if (mHomeAdapter == null) {
            mHomeAdapter = new HomeAdapter();
            mHomeAdapter.setData(mListHomeUser);
            mainHomeArticleList.setLayoutManager(new LinearLayoutManager(getContext()));
            mainHomeArticleList.setAdapter(mHomeAdapter);
            mHomeAdapter.notifyDataSetChanged();
        } else {
            mHomeAdapter.setData(mListHomeUser);
            mHomeAdapter.notifyDataSetChanged();
        }

//        homeSmartrefreshlayout.autoRefresh();
    }

    @Override
    public void onArticleFail(String msg) {
        onError(new LoadingPage.OnReloadCallBack() {
            @Override
            public void reload() {
                mPresenter.reFresh();
            }
        },msg);
    }

    @Override
    public void onCollectSucces(HttpResult httpResult) {
        if(httpResult.errorCode == 0){
            Toast.makeText(getContext(),"收藏成功",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCollectError(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(HomeContract.IHomePresenter presenter) {
        mPresenter = presenter;
        presenter.attachView(this);

    }

    @Override
    public Context getContextObject() {
        return getContext();
    }


    @Override
    public void onClick(HomeUser mArticles) {
//        AutoMassage autoMassage = new AutoMassage();
//        autoMassage.title = mArticles.getTitle();
//        autoMassage.url = mArticles.getLink();
//        EventBus.getDefault().postSticky(autoMassage);
//        startActivity(new Intent(getContext(), MassageActivity.class));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void HomeTransfer(String s){}

    public void setRecyclerToTop(){
        mainHomeArticleList.smoothScrollToPosition(0);
    }

    private HomeBanneOnClicke mHomeBanneOnClicke;

    public void setmHomeBanneOnClicke(HomeBanneOnClicke mHomeBanneOnClicke) {
        this.mHomeBanneOnClicke = mHomeBanneOnClicke;
    }

    public interface HomeBanneOnClicke{
        void BannerAutoMasseg(AutoMassage autoMassage);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }


}
