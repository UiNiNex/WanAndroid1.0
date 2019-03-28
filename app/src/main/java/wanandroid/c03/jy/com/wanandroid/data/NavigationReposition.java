package wanandroid.c03.jy.com.wanandroid.data;

import android.content.Context;

import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import wanandroid.c03.jy.com.wanandroid.data.entity.HttpResult;
import wanandroid.c03.jy.com.wanandroid.data.entity.NavigationListData;
import wanandroid.c03.jy.com.wanandroid.data.entity.NavigationUser;
import wanandroid.c03.jy.com.wanandroid.data.remota.rteofit.DataService;
import wanandroid.c03.jy.com.wanandroid.data.remota.rteofit.WARetrofitService;
import wanandroid.c03.jy.com.wanandroid.exception.ExceptionManager;
import wanandroid.c03.jy.com.wanandroid.navigation.NavigationContract;

/**
 * User:lenovo
 * Date:2018/12/15
 * Time:19:34
 * author:lzy
 */
public class NavigationReposition implements NavigationContract.NavigationMode{

    private static NavigationReposition mInstance;
    private WARetrofitService mService;

    private NavigationReposition (Context context,boolean len){
        mService = DataService.getService(context,len);
    }

    public static NavigationReposition getInstance(Context context,boolean l){
        if(mInstance == null){
            synchronized (NavigationReposition.class){
                mInstance = new NavigationReposition(context,l);
            }
        }
        return mInstance;
    }

    @Override
    public void onRemotNavigationUser(Observer<List<NavigationListData>> callBack, RxFragment rxFragment) {
        mService.ramotNavigationUSer().flatMap(new Function<HttpResult<List<NavigationListData>>, ObservableSource<List<NavigationListData>>>() {
            @Override
            public ObservableSource<List<NavigationListData>> apply(HttpResult<List<NavigationListData>> listHttpResult) throws Exception {

                if(listHttpResult.errorCode == 0){
                    if(listHttpResult.data != null){
                        return Observable.just(listHttpResult.data);
                    }
                }

                return Observable.error(ExceptionManager.buildServerErrorMessage(listHttpResult
                        .errorCode,listHttpResult.errorMsg));
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxFragment.<List<NavigationListData>>bindUntilEvent(FragmentEvent.DETACH))
                .subscribe(callBack);
    }
}
