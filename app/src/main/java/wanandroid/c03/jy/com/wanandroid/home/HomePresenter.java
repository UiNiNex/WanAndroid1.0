package wanandroid.c03.jy.com.wanandroid.home;

import android.util.Log;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import wanandroid.c03.jy.com.wanandroid.data.entity.ArticleData;
import wanandroid.c03.jy.com.wanandroid.data.entity.Data;
import wanandroid.c03.jy.com.wanandroid.data.entity.HomeBanner;
import wanandroid.c03.jy.com.wanandroid.data.entity.HomeUser;
import wanandroid.c03.jy.com.wanandroid.data.entity.HttpResult;
import wanandroid.c03.jy.com.wanandroid.data.entity.User;
import wanandroid.c03.jy.com.wanandroid.exception.ExceptionManager;

/**
 * User:lenovo
 * Date:2018/12/9
 * Time:15:32
 * author:lzy
 */
public class HomePresenter implements HomeContract.IHomePresenter {

    private static final  int BANNER_RETRY_MAX_COUNT  = 3;

    private HomeContract.IHomeMode mRepository;
    private HomeContract.IHomeView mView;


    private int mPage = 0;
    private int mBannerRetryCount = 0;

    public HomePresenter(HomeContract.IHomeMode mRepository) {
        this.mRepository = mRepository;
    }

    @Override
    public void getBanner() {
        if(mBannerRetryCount >= BANNER_RETRY_MAX_COUNT){
            return;
        }

        mRepository.getHomeBannerInfo(new Observer<List<HomeBanner>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<HomeBanner> homeBanners) {
                mView.onBannerSuccess(homeBanners);
            }

            @Override
            public void onError(Throwable e) {
                mView.onBannerFail(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        },(RxFragment) mView);
    }

    @Override
    public void getHomeData() {
        //获取顶部banner
        getBanner();
        //获取文章列表
        getArticles(HomeContract.IHomeView.LOAD_TYPE_FIRST);



    }

    @Override
    public void onHomeCollect(int id) {
        mRepository.onCollectItem(new Observer<HttpResult<Data>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(HttpResult<Data> userHttpResult) {
                mView.onCollectSucces(userHttpResult);
            }

            @Override
            public void onError(Throwable e) {
                mView.onCollectError(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        }, id, (RxFragment) mView);
    }

    // 获取文章列表
    private void getArticles(final int type) {
        // 获取文章列表
        mRepository.getHomeUser(new Observer<ArticleData>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ArticleData homeUsers) {
                mView.onArticleSuccess(homeUsers.getDatas(),type);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("LZY",e.getMessage());
                mView.onBannerFail(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        },(RxFragment) mView,mPage);
    }

    @Override
    public void reFresh() {
        mPage = 0;
        getArticles(HomeContract.IHomeView.LOAD_TYPE_REFIRST);
    }

    @Override
    public void loadMore() {
        mPage++;
        getArticles(HomeContract.IHomeView.LOAD_TYPE_MORE);
    }

    @Override
    public void attachView(HomeContract.IHomeView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
