package wanandroid.c03.jy.com.wanandroid.data;

import android.content.Context;

import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import wanandroid.c03.jy.com.wanandroid.data.entity.HttpResult;
import wanandroid.c03.jy.com.wanandroid.data.entity.KnowHerarData;
import wanandroid.c03.jy.com.wanandroid.data.entity.TwoKonwledgehierarchypage;
import wanandroid.c03.jy.com.wanandroid.data.remota.rteofit.DataService;
import wanandroid.c03.jy.com.wanandroid.data.remota.rteofit.WARetrofitService;
import wanandroid.c03.jy.com.wanandroid.exception.ExceptionManager;
import wanandroid.c03.jy.com.wanandroid.knowledgeHierarchy.KnowledgeHierarchyCotrcte;

/**
 * User:lenovo
 * Date:2018/12/10
 * Time:14:03
 * author:lzy
 */
public class KnowHierarRepository implements KnowledgeHierarchyCotrcte.KnowHierarMode{

    private static volatile KnowHierarRepository mInstance;

    private WARetrofitService mService;

    private KnowHierarRepository(Context context){
        mService = DataService.getService(context,true);
    }

    public static KnowHierarRepository getInstance(Context context){
        if(mInstance == null){
            synchronized (KnowHierarRepository.class){
                if(mInstance == null){
                    mInstance = new KnowHierarRepository(context);
                }
            }
        }
        return mInstance;
    }

    @Override
    public void getKnowHierarInfo(Observer<List<KnowHerarData>> callBack, RxFragment rxFragment) {
        mService.ramotKonwledgeHierarUser().flatMap(new Function<HttpResult<List<KnowHerarData>>, ObservableSource<List<KnowHerarData>>>() {
            @Override
            public ObservableSource<List<KnowHerarData>> apply(HttpResult<List<KnowHerarData>> listHttpResult) throws Exception {

                if(listHttpResult.errorCode == 0){
                    if(listHttpResult.data != null){
                        return Observable.just(listHttpResult.data);
                    }else{
                        return Observable.error(ExceptionManager.buildServerErrorMessage
                                (listHttpResult.errorCode,listHttpResult.errorMsg));
                    }

                }

                return Observable.error(ExceptionManager.buildServerErrorMessage
                        (listHttpResult.errorCode,listHttpResult.errorMsg));
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxFragment.<List<KnowHerarData>>bindUntilEvent(FragmentEvent.DETACH))
                .subscribe(callBack);
    }

    @Override
    public void getTwoKnowHierarInfo(Observer<TwoKonwledgehierarchypage> callBack, RxFragment rxFragment, int page, int id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("cid",id+"");
        mService.ramotKonwledgeHierarArticle(id).flatMap(new Function<HttpResult<TwoKonwledgehierarchypage>, ObservableSource<TwoKonwledgehierarchypage>>() {
            @Override
            public ObservableSource<TwoKonwledgehierarchypage> apply(HttpResult<TwoKonwledgehierarchypage> listHttpResult) throws Exception {
                if(listHttpResult.errorCode == 0){
                    if(listHttpResult.data != null){
                        return Observable.just(listHttpResult.data);
                    }else{
                        return Observable.error(ExceptionManager.buildServerErrorMessage
                                (listHttpResult.errorCode,listHttpResult.errorMsg));
                    }
                }
                return Observable.error(ExceptionManager.buildServerErrorMessage
                        (listHttpResult.errorCode,listHttpResult.errorMsg));
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxFragment.<TwoKonwledgehierarchypage>bindUntilEvent(FragmentEvent.DETACH))
                .subscribe(callBack);
    }
}
