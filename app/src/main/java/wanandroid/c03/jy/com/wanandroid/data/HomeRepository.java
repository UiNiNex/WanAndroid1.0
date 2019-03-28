package wanandroid.c03.jy.com.wanandroid.data;

import android.content.Context;
import android.util.Log;

import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import wanandroid.c03.jy.com.wanandroid.data.entity.ArticleData;
import wanandroid.c03.jy.com.wanandroid.data.entity.Data;
import wanandroid.c03.jy.com.wanandroid.data.entity.HomeBanner;
import wanandroid.c03.jy.com.wanandroid.data.entity.HomeUser;
import wanandroid.c03.jy.com.wanandroid.data.entity.HttpResult;
import wanandroid.c03.jy.com.wanandroid.data.entity.User;
import wanandroid.c03.jy.com.wanandroid.data.remota.rteofit.DataService;
import wanandroid.c03.jy.com.wanandroid.data.remota.rteofit.WARetrofitService;
import wanandroid.c03.jy.com.wanandroid.exception.ExceptionManager;
import wanandroid.c03.jy.com.wanandroid.home.HomeContract;
import wanandroid.c03.jy.com.wanandroid.util.CommonUtils;

/**
 * User:lenovo
 * Date:2018/12/9
 * Time:13:59
 * author:lzy
 */
public class HomeRepository implements HomeContract.IHomeMode{

    private static volatile HomeRepository mInstance;
    private WARetrofitService mService;
    private Context mContext;
    private int page = 0;

    private HomeRepository (Context context){
        mService = DataService.getService(context,true);
    }

    public static HomeRepository getInstance(Context context){
        if(mInstance == null){
            synchronized (HomeRepository.class){
                if(mInstance == null){
                    mInstance = new HomeRepository(context);
                }
            }
        }
        return mInstance;
    }

    @Override
    public void getHomeBannerInfo(Observer<List<HomeBanner>> callBack, RxFragment rxFragment) {
        mService.ramotBannerHomeUser().flatMap(new Function<HttpResult<List<HomeBanner>>, ObservableSource<List<HomeBanner>>>() {
            @Override
            public ObservableSource<List<HomeBanner>> apply(HttpResult<List<HomeBanner>> listHttpResult) throws Exception {

                if(listHttpResult.errorCode == 0){
                    if(listHttpResult.data != null){
                        return Observable.just(listHttpResult.data);
                    }else{
                        return Observable.error(ExceptionManager.buildServerErrorMessage(listHttpResult.errorCode,listHttpResult.errorMsg));
                    }
                }
                return Observable.error(ExceptionManager.buildServerErrorMessage(listHttpResult.errorCode,listHttpResult.errorMsg));
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxFragment.<List<HomeBanner>>bindUntilEvent(FragmentEvent.DETACH))
                .subscribe(callBack);
    }

    @Override
    public void getHomeUser(Observer<ArticleData> callBack, RxFragment rxFragment, int page) {
        mService.ramotHomeUser(page).flatMap(new Function<HttpResult<ArticleData>, ObservableSource<ArticleData>>() {
            @Override
            public ObservableSource<ArticleData> apply(HttpResult<ArticleData> listHttpResult) throws Exception {
                if(listHttpResult.errorCode == 0){
                    if(listHttpResult.data != null && listHttpResult.data.getDatas() != null && listHttpResult.data.getDatas().size() > 0){
                        return Observable.just(listHttpResult.data);
                    }
                }
                return Observable.error(ExceptionManager.buildServerErrorMessage(listHttpResult.errorCode, listHttpResult.errorMsg));
        }
    }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxFragment.<ArticleData>bindUntilEvent(FragmentEvent.DETACH))
                .subscribe(callBack);
    }

    @Override
    public void onCollectItem(Observer<HttpResult<Data>> observable,int id,RxFragment
            rxFragment) {
        mService.onCollect(id)
                .flatMap(new Function<HttpResult<Data>, ObservableSource<HttpResult<Data>>>() {
                    @Override
                    public ObservableSource<HttpResult<Data>> apply(HttpResult<Data> userHttpResult)
                            throws Exception {
                        if(userHttpResult.errorCode == 0){
                            return Observable.just(userHttpResult);
                        }
                        return Observable.error(ExceptionManager.buildServerErrorMessage
                                (userHttpResult.errorCode,userHttpResult.errorMsg));
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxFragment.<HttpResult<Data>>bindUntilEvent(FragmentEvent.DETACH))
                .subscribe(observable);
    }
}
