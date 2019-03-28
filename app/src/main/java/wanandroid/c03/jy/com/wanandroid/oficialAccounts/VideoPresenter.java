package wanandroid.c03.jy.com.wanandroid.oficialAccounts;

import android.util.Log;

import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import wanandroid.c03.jy.com.wanandroid.data.VideoDataReposition;
import wanandroid.c03.jy.com.wanandroid.data.entity.VideoBean;

import static wanandroid.c03.jy.com.wanandroid.AppConstant.VIDEO_NO_ITEM;

/**
 * User:lenovo
 * Date:2018/12/26
 * Time:11:26
 * author:lzy
 */
public class VideoPresenter implements VideoConuntract.MVideoPresenter {

    private VideoDataReposition mMode;
    private VideoConuntract.MVideoView mView;
    private int id = 14;

    VideoPresenter(VideoDataReposition mode){
        this.mMode = mode;
    }

    @Override
    public void remotVideoUSer(final int type) {
        mMode.onVideoData(new Observer<List<VideoBean.ResultBean.DataBeanX.ContentBean.DataBean>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<VideoBean.ResultBean.DataBeanX.ContentBean.DataBean> user) {
                if(user != null){
                    mView.onVideoData(user,type);
                }else{
                    mView.onFile(VIDEO_NO_ITEM);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d("LZy----e",e.getMessage());
                mView.onFile(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        }, (RxFragment)mView,id);
    }

    @Override
    public void refresh() {


    }

    @Override
    public void onLodmore() {

    }

    public void setData(){
        remotVideoUSer(VideoConuntract.MVideoView.LOAD_TYPE_FIRST);
    }

    @Override
    public void attachView(VideoConuntract.MVideoView view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }
}
