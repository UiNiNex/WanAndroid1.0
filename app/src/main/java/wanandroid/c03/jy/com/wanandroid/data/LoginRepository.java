package wanandroid.c03.jy.com.wanandroid.data;

import android.content.Context;
import android.util.Log;

import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import wanandroid.c03.jy.com.wanandroid.data.entity.HttpResult;
import wanandroid.c03.jy.com.wanandroid.data.entity.User;
import wanandroid.c03.jy.com.wanandroid.data.remota.rteofit.DataService;
import wanandroid.c03.jy.com.wanandroid.data.remota.rteofit.WARetrofitService;
import wanandroid.c03.jy.com.wanandroid.exception.ExceptionManager;
import wanandroid.c03.jy.com.wanandroid.login.LoginContract;
import wanandroid.c03.jy.com.wanandroid.util.CommonUtils;
import wanandroid.c03.jy.com.wanandroid.util.SharePreferenceUtils;

import static wanandroid.c03.jy.com.wanandroid.AppConstant.LOGGING_STATUS;
import static wanandroid.c03.jy.com.wanandroid.AppConstant.ParamsUser.KEY_USER_NAME;
import static wanandroid.c03.jy.com.wanandroid.AppConstant.ParamsUser.KEY_USER_PASSWORD;

/**
 * User:lenovo
 * Date:2018/12/8
 * Time:19:46
 * author:lzy
 */
public class LoginRepository implements LoginContract.ILoginMode {

    private static volatile LoginRepository mInstance;


    private WARetrofitService mRemoteService;
    private Context mContext;

    private LoginRepository(Context context) {
        mContext = context.getApplicationContext();
        mRemoteService = DataService.getService(context,false);
    }

    public static LoginRepository getInstance(Context context) {
        if (mInstance == null) {
            synchronized (LoginRepository.class) {
                if (mInstance == null) {
                    mInstance = new LoginRepository(context);
                }
            }
        }
        return mInstance;
    }



    @Override
    public void login(Observer<User> callBack, RxFragment rxFragment, HashMap<String, String> params) {
        Log.d("LZy",params.toString());

        mRemoteService.login(params).flatMap(new Function<HttpResult<User>, ObservableSource<User>>() {
            @Override
            public ObservableSource<User> apply(HttpResult<User> userHttpResult) throws Exception {

                if(userHttpResult.errorCode == 0){
                    User user = userHttpResult.data;
                    if(user != null){
                        return Observable.just(user);
                    }else{
                        return Observable.error(ExceptionManager.buildServerErrorMessage(userHttpResult.errorCode, userHttpResult.errorMsg));
                    }
                }
                return Observable.error(ExceptionManager.buildServerErrorMessage(userHttpResult.errorCode, userHttpResult.errorMsg));
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())

                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxFragment.<User>bindUntilEvent(FragmentEvent.DETACH)) // fragment detach 生命周期执行的时候，解除绑定
                .subscribe(callBack);
    }

    @Override
    public void aoutoLogin(Observer<User> callBack, RxFragment rxFragment) {

    }

    @Override
    public void save(final User user) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                String userName = "";
                String password = "";

                if(user != null){
                    userName = user.getUsername();
                    password = user.getPassword();
                    HashMap<String, String> map = CommonUtils.buildHashMap(new String[]{KEY_USER_NAME, KEY_USER_PASSWORD}
                            , new String[]{userName, password});
                    SharePreferenceUtils.saveToGlobalSp(mContext,map);
                    SharePreferenceUtils.saveToGlobalSp(mContext,LOGGING_STATUS,true);
                }

            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }


}
