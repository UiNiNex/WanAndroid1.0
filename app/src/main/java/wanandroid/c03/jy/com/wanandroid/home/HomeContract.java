package wanandroid.c03.jy.com.wanandroid.home;

import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;

import io.reactivex.Observer;
import wanandroid.c03.jy.com.wanandroid.base.IBasePresenter;
import wanandroid.c03.jy.com.wanandroid.base.IBaseView;
import wanandroid.c03.jy.com.wanandroid.data.entity.ArticleData;
import wanandroid.c03.jy.com.wanandroid.data.entity.Data;
import wanandroid.c03.jy.com.wanandroid.data.entity.HomeBanner;
import wanandroid.c03.jy.com.wanandroid.data.entity.HomeUser;
import wanandroid.c03.jy.com.wanandroid.data.entity.HttpResult;
import wanandroid.c03.jy.com.wanandroid.data.entity.User;

/**
 * User:lenovo
 * Date:2018/12/9
 * Time:13:49
 * author:lzy
 */
public interface HomeContract {

    public interface IHomeView extends IBaseView<IHomePresenter>{

        int LOAD_TYPE_FIRST = 0;//第一次
        int LOAD_TYPE_REFIRST = 1;//刷新
        int LOAD_TYPE_MORE = 2;//加载过

        void onBannerSuccess(List<HomeBanner> banners);
        void onBannerFail(String msg);

        void onArticleSuccess(List<HomeUser> articles, int type);
        void onArticleFail(String msg);

        void onCollectSucces(HttpResult<Data> httpResult);
        void onCollectError(String msg);
    }

    public interface IHomePresenter extends IBasePresenter<IHomeView>{

        void getBanner();
        void getHomeData();
        void onHomeCollect(int id);
        void reFresh();//刷新
        void loadMore();//加载更多

    }

    public interface IHomeMode{

        void getHomeBannerInfo(Observer<List<HomeBanner>> callBack, RxFragment rxFragment);
        void getHomeUser(Observer<ArticleData> callBack, RxFragment rxFragment, int page);
        void onCollectItem(Observer<HttpResult<Data>> observer, int id, RxFragment rxFragment);

    }

}
