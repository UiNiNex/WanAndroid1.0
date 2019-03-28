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
import wanandroid.c03.jy.com.wanandroid.data.entity.ProjectAccountsArtileData;
import wanandroid.c03.jy.com.wanandroid.data.entity.ProjectAccountsNavigationUser;
import wanandroid.c03.jy.com.wanandroid.data.remota.rteofit.DataService;
import wanandroid.c03.jy.com.wanandroid.data.remota.rteofit.WARetrofitService;
import wanandroid.c03.jy.com.wanandroid.exception.ExceptionManager;
import wanandroid.c03.jy.com.wanandroid.project.ProjectAccountsConuntract;

/**
 * User:lenovo
 * Date:2018/12/12
 * Time:20:19
 * author:lzy
 */
public class ProjectAccountsNavigationUserMode implements ProjectAccountsConuntract.ProjectMode {

    private static ProjectAccountsNavigationUserMode mInstance;
    private WARetrofitService mService;

    private ProjectAccountsNavigationUserMode(Context context, boolean clen){
        mService = DataService.getService(context,true);
    }

    public static ProjectAccountsNavigationUserMode getInstance(Context context, boolean blen){
        if(mInstance == null){
            synchronized (ProjectAccountsNavigationUserMode.class){
                if(mInstance == null){
                    mInstance = new ProjectAccountsNavigationUserMode(context,blen);
                }
            }
        }
        return mInstance;
    }

    @Override
    public void onRemotProjectUser(Observer<List<ProjectAccountsNavigationUser>> callBack, RxFragment rxFragment) {
        mService.ramotProjectAccountsNavifationUser().flatMap(new Function<HttpResult<List<ProjectAccountsNavigationUser>>,
                ObservableSource<List<ProjectAccountsNavigationUser>>>() {
            @Override
            public ObservableSource<List<ProjectAccountsNavigationUser>> apply(HttpResult<List<ProjectAccountsNavigationUser>> listHttpResult) throws Exception {
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
                .compose(rxFragment.<List<ProjectAccountsNavigationUser>>bindUntilEvent(FragmentEvent.DETACH))
                .subscribe(callBack);
    }

    @Override
    public void onRemotProjectArticleList(Observer<ProjectAccountsArtileData> callBack, RxFragment rxFragment, int page, int id) {
        mService.ramotProjectAccountsArtileUser(page,id).flatMap(new Function<HttpResult<ProjectAccountsArtileData>, ObservableSource<ProjectAccountsArtileData>>() {
            @Override
            public ObservableSource<ProjectAccountsArtileData> apply(HttpResult<ProjectAccountsArtileData> projectAccountsNavigationUserHttpResult) throws Exception {
                if(projectAccountsNavigationUserHttpResult.errorCode == 0){
                    if(projectAccountsNavigationUserHttpResult.data != null){
                        return Observable.just(projectAccountsNavigationUserHttpResult.data);
                    }
                }
                return Observable.error(ExceptionManager.buildServerErrorMessage(projectAccountsNavigationUserHttpResult
                        .errorCode,projectAccountsNavigationUserHttpResult.errorMsg));
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxFragment.<ProjectAccountsArtileData>bindUntilEvent(FragmentEvent.DETACH))
                .subscribe(callBack);
    }
}
