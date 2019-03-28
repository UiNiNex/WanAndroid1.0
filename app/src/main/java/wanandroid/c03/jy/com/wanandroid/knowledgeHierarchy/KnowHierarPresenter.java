package wanandroid.c03.jy.com.wanandroid.knowledgeHierarchy;

import android.util.Log;

import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import wanandroid.c03.jy.com.wanandroid.base.IBaseView;
import wanandroid.c03.jy.com.wanandroid.data.entity.KnowHerarData;
import wanandroid.c03.jy.com.wanandroid.data.entity.TwoKonwledgehierarchypage;
import wanandroid.c03.jy.com.wanandroid.home.HomeContract;

/**
 * User:lenovo
 * Date:2018/12/10
 * Time:19:40
 * author:lzy
 */

public class KnowHierarPresenter implements KnowledgeHierarchyCotrcte.KnowHierarPresenter{

    private KnowledgeHierarchyCotrcte.KnowHierarMode mMode;
    private KnowledgeHierarchyCotrcte.KnowHierarView mView;
    private KnowledgeHierarchyCotrcte.KnowHierarView.TwoKnowHierarView mViews;

    private int mPage = 0;
    private int mId = 0;

    public KnowHierarPresenter(KnowledgeHierarchyCotrcte.KnowHierarMode mMode){
        this.mMode = mMode;
    }

    @Override
    public void getKnowHierarSuccess(final int type) {
        mMode.getKnowHierarInfo(new Observer<List<KnowHerarData>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<KnowHerarData> list) {
                mView.onKnowHierarSuccess(list,type);
            }

            @Override
            public void onError(Throwable e) {
                mView.onKnowHierarFail(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        },(RxFragment)mView);
    }

    @Override
    public void getTwoKnowHierarSucces(final int type) {
        mMode.getTwoKnowHierarInfo(new Observer<TwoKonwledgehierarchypage>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(TwoKonwledgehierarchypage twoKonwledgehierarchypage) {
                mPage = twoKonwledgehierarchypage.getCurPage();
                mViews.onTwoKnowHierarSucces(twoKonwledgehierarchypage.getDatas(), type);
            }

            @Override
            public void onError(Throwable e) {
                mViews.onTwoKnowHierarFail(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        },(RxFragment)mViews,mPage,mId);
    }

    @Override
    public void reFreshe() {
        mPage = 0;
        getTwoKnowHierarSucces(KnowledgeHierarchyCotrcte.KnowHierarView.LOAD_TYPE_REFIRST);
    }

    @Override
    public void loadmore() {
        mPage++;
        getTwoKnowHierarSucces(KnowledgeHierarchyCotrcte.KnowHierarView.LOAD_TYPE_MORE);
    }

    @Override
    public void attachView(IBaseView view) {
        if(view instanceof KnowledgeHierarchyCotrcte.KnowHierarView){
            mView = (KnowledgeHierarchyCotrcte.KnowHierarView) view;
        }else{
            mViews = (KnowledgeHierarchyCotrcte.KnowHierarView.TwoKnowHierarView) view;
        }

    }

    public void setId(int id){
        mId = id;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
